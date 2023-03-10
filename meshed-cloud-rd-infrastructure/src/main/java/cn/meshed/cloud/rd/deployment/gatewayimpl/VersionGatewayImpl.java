package cn.meshed.cloud.rd.deployment.gatewayimpl;

import cn.meshed.cloud.rd.domain.cli.GenerateAdapter;
import cn.meshed.cloud.rd.domain.cli.GenerateModel;
import cn.meshed.cloud.rd.domain.cli.GenerateRpc;
import cn.meshed.cloud.rd.domain.cli.gateway.CliGateway;
import cn.meshed.cloud.rd.domain.deployment.gateway.VersionGateway;
import cn.meshed.cloud.rd.domain.deployment.strategy.dto.ClientPublish;
import cn.meshed.cloud.rd.domain.project.Service;
import cn.meshed.cloud.rd.domain.project.gateway.ModelGateway;
import cn.meshed.cloud.rd.domain.project.gateway.ServiceGateway;
import cn.meshed.cloud.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * <h1>版本网关实现</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
public class VersionGatewayImpl implements VersionGateway {

    private final ServiceGateway serviceGateway;
    private final ModelGateway modelGateway;
    private final CliGateway cliGateway;

    /**
     * 发布客户端版本
     * 服务和模型中所依赖的其他模型，在此环节已默认确保了存在，调用切勿避免依赖模型未发布状态，导致发布后代码异常
     *
     * @param clientPublish 客户端版本
     * @return
     */
    @Override
    public boolean publishClientVersion(ClientPublish clientPublish) {
        String projectKey = clientPublish.getProjectKey();
        AssertUtils.isTrue(StringUtils.isNotBlank(projectKey), "项目key不允许为空");


        return true;
    }


    /**
     * 发布服务列表
     *
     * @param repositoryId 存储ID
     * @param services     项目key
     * @return
     */
    private boolean publishServiceVersion(String repositoryId, Set<Service> services) {

        //没有服务需要发布，符合正常逻辑
        if (CollectionUtils.isEmpty(services)) {
            return true;
        }
        //转换数据


        //服务合成适配器或者


        //
        GenerateModel generateModel = new GenerateModel();
        generateModel.setModels(null);
        cliGateway.asyncGenerateModelWithPush(repositoryId, generateModel);

        //构建并且推送
        GenerateAdapter generateAdapter = new GenerateAdapter();
        generateAdapter.setAdapters(null);
        cliGateway.asyncGenerateAdapterWithPush(repositoryId, generateAdapter);
        //构建并且推送
        GenerateRpc generateRpc = new GenerateRpc();
        generateRpc.setRpcList(null);
        cliGateway.asyncGenerateRpcWithPush(repositoryId, generateRpc);
        return true;
    }


}
