package cn.meshed.cloud.rd.deployment.gatewayimpl.strategy;

import cn.meshed.cloud.rd.domain.deployment.strategy.AsyncPublishStrategy;
import cn.meshed.cloud.rd.domain.deployment.strategy.PublishHandler;
import cn.meshed.cloud.rd.domain.deployment.strategy.PublishType;
import cn.meshed.cloud.rd.domain.deployment.strategy.dto.ClientPublish;
import cn.meshed.cloud.rd.domain.deployment.strategy.dto.ModelPublish;
import cn.meshed.cloud.rd.domain.deployment.strategy.dto.ServicePublish;
import cn.meshed.cloud.rd.domain.repo.gateway.RepositoryGateway;
import cn.meshed.cloud.utils.AssertUtils;
import cn.meshed.cloud.utils.CopyUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static cn.meshed.cloud.rd.domain.deployment.constant.DeploymentConstant.SRC_PATH;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
public class ClientPublishHandler implements PublishHandler<ClientPublish> {

    private final AsyncPublishStrategy asyncPublishStrategy;
    private final RepositoryGateway repositoryGateway;

    /**
     * 注册类型
     *
     * @return PublishType
     */
    @Override
    public PublishType getPublishType() {
        return PublishType.CLIENT;
    }

    /**
     * 发布客户端
     *
     * @param clientPublish 客户端发布数据包
     */
    @Override
    public void publish(ClientPublish clientPublish) {
        String projectKey = clientPublish.getProjectKey();
        AssertUtils.isTrue(StringUtils.isNotBlank(projectKey), "项目key不允许为空");
        repositoryGateway.rebuildBranch(clientPublish.getRepositoryId(), clientPublish.getBranch());
        //发布模型
        ModelPublish modelPublish = CopyUtils.copy(clientPublish, ModelPublish.class);
        modelPublish.setBasePath(SRC_PATH);
        asyncPublishStrategy.asyncPublish(PublishType.MODEL, modelPublish);
        //发布服务
        ServicePublish servicePublish = CopyUtils.copy(clientPublish, ServicePublish.class);
        servicePublish.setBasePath(SRC_PATH);
        asyncPublishStrategy.asyncPublish(PublishType.SERVICE, servicePublish);
        //发布枚举。。。


    }
}
