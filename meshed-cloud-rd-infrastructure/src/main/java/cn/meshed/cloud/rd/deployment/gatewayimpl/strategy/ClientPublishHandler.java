package cn.meshed.cloud.rd.deployment.gatewayimpl.strategy;

import cn.meshed.cloud.rd.domain.deployment.strategy.AsyncPublishStrategy;
import cn.meshed.cloud.rd.domain.deployment.strategy.PublishHandler;
import cn.meshed.cloud.rd.domain.deployment.strategy.PublishType;
import cn.meshed.cloud.rd.domain.deployment.strategy.dto.ClientPublish;
import cn.meshed.cloud.rd.domain.deployment.strategy.dto.ModelPublish;
import cn.meshed.cloud.rd.domain.deployment.strategy.dto.ServicePublish;
import cn.meshed.cloud.utils.AssertUtils;
import cn.meshed.cloud.utils.CopyUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

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
        String repositoryId = clientPublish.getRepositoryId();
        //发布模型
        asyncPublishStrategy.asyncPublish(PublishType.MODEL, CopyUtils.copy(clientPublish, ModelPublish.class));
        //发布服务
        asyncPublishStrategy.asyncPublish(PublishType.SERVICE, CopyUtils.copy(clientPublish, ServicePublish.class));
        //发布枚举。。。


    }
}
