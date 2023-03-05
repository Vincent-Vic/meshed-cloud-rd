package cn.meshed.cloud.rd.cli.executor.command;

import cn.meshed.cloud.cqrs.CommandExecute;
import cn.meshed.cloud.rd.cli.executor.query.ArchetypeTemplateQry;
import cn.meshed.cloud.rd.domain.cli.Archetype;
import cn.meshed.cloud.rd.domain.cli.Skeleton;
import cn.meshed.cloud.rd.domain.cli.gateway.CliGateway;
import cn.meshed.cloud.utils.IdUtils;
import cn.meshed.cloud.utils.ResultUtils;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.SysException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
public class SkeletonCmdExe implements CommandExecute<Skeleton, SingleResponse<String>> {

    private final CliGateway cliGateway;
    private final ArchetypeTemplateQry archetypeTemplateQry;

    @Value("${rd.cli.workspace}")
    private String workspace;

    /**
     * <h1>执行器</h1>
     *
     * @param skeleton 执行器 {@link Skeleton}
     * @return {@link Response}
     */
    @Override
    public SingleResponse<String> execute(Skeleton skeleton) {
        //校验
        skeleton.verification();

        //获取原型模板
        Archetype archetype = archetypeTemplateQry.execute(skeleton.getEngineTemplate());
        if (archetype == null) {
            return ResultUtils.fail("模板不存在");
        }

        //生成根路径
        String workspacePath = getWorkspacePath();

        try {
            cliGateway.archetype(workspacePath, archetype, skeleton.getArtifact());
        } catch (SysException sysException) {
            return ResultUtils.fail(sysException.getMessage());
        }

        return ResultUtils.of(workspacePath);
    }

    @NotNull
    private String getWorkspacePath() {
        if (!workspace.endsWith("/")) {
            workspace = workspace + "/";
        }
        return workspace + IdUtils.simpleUUID() + "/";
    }
}
