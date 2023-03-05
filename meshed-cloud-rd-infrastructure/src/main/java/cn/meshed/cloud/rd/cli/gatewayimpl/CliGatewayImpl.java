package cn.meshed.cloud.rd.cli.gatewayimpl;

import cn.meshed.cloud.rd.domain.cli.Archetype;
import cn.meshed.cloud.rd.domain.cli.Artifact;
import cn.meshed.cloud.rd.domain.cli.gateway.CliGateway;
import com.alibaba.cola.exception.SysException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.cli.MavenCli;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.meshed.cloud.rd.cli.gatewayimpl.MavenConstant.ARCHETYPE_ARTIFACT_ID;
import static cn.meshed.cloud.rd.cli.gatewayimpl.MavenConstant.ARCHETYPE_GENERATE_ARG;
import static cn.meshed.cloud.rd.cli.gatewayimpl.MavenConstant.ARCHETYPE_GROUP_ID;
import static cn.meshed.cloud.rd.cli.gatewayimpl.MavenConstant.ARCHETYPE_VERSION;
import static cn.meshed.cloud.rd.cli.gatewayimpl.MavenConstant.ARG_FORMAT;
import static cn.meshed.cloud.rd.cli.gatewayimpl.MavenConstant.ARTIFACT_ID;
import static cn.meshed.cloud.rd.cli.gatewayimpl.MavenConstant.BUILD_ARG;
import static cn.meshed.cloud.rd.cli.gatewayimpl.MavenConstant.GROUP_ID;
import static cn.meshed.cloud.rd.cli.gatewayimpl.MavenConstant.MULTI_MODULE_PROJECT_DIRECTORY;
import static cn.meshed.cloud.rd.cli.gatewayimpl.MavenConstant.PACKAGE;
import static cn.meshed.cloud.rd.cli.gatewayimpl.MavenConstant.SETTING_PARAM_FORMAT;
import static cn.meshed.cloud.rd.cli.gatewayimpl.MavenConstant.VERSION;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
public class CliGatewayImpl implements CliGateway {

    /**
     * 原型构建
     *
     * @param outPath   输出路径
     * @param archetype 原型
     * @param artifact  制品信息
     * @throws SysException 构建异常信息
     */
    @Override
    public void archetype(String outPath, Archetype archetype, Artifact artifact) throws SysException {
        MavenCli cli = new MavenCli();
        String mvnHome = MavenCli.USER_MAVEN_CONFIGURATION_HOME.getAbsolutePath();
        System.getProperties().setProperty(MULTI_MODULE_PROJECT_DIRECTORY, mvnHome);
        List<String> args = new ArrayList<>();
        args.add(ARCHETYPE_GENERATE_ARG);
        if (StringUtils.isNotBlank(archetype.getSettingPath())) {
            args.add(String.format(SETTING_PARAM_FORMAT, archetype.getSettingPath()));
        }
        addArg(args, ARCHETYPE_GROUP_ID, archetype.getArchetypeGroupId());
        addArg(args, ARCHETYPE_ARTIFACT_ID, archetype.getArchetypeArtifactId());
        addArg(args, ARCHETYPE_VERSION, archetype.getArchetypeVersion());
        addArg(args, GROUP_ID, artifact.getGroupId());
        addArg(args, ARTIFACT_ID, artifact.getArtifactId());
        addArg(args, VERSION, artifact.getVersion());
        addArg(args, PACKAGE, artifact.getPackageName());
        args.add(BUILD_ARG);
        Map<String, String> extendedMap = artifact.getExtendedMap();
        if (extendedMap != null && extendedMap.size() > 0) {
            for (Map.Entry<String, String> entry : extendedMap.entrySet()) {
                addArg(args, entry.getKey(), entry.getValue());
            }
        }
        int status = 0;
        try {
            status = cli.doMain(args.toArray(new String[]{}), outPath, System.out, System.out);
        } catch (Exception e) {
            throw e;
        }
        if (status != 0) {
            throw new SysException("maven 出错");
        }
    }

    /**
     * 构建参数
     *
     * @param parameter
     * @param value
     * @return
     */
    @NotNull
    private void addArg(List<String> args, String parameter, String value) {
        if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(parameter)) {
            args.add(String.format(ARG_FORMAT, parameter, value));
        }
    }
}
