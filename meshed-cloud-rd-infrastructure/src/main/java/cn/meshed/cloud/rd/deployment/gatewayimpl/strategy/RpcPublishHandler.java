package cn.meshed.cloud.rd.deployment.gatewayimpl.strategy;

import cn.meshed.cloud.rd.domain.cli.gateway.CliGateway;
import cn.meshed.cloud.rd.domain.deployment.strategy.AbstractServicePublish;
import cn.meshed.cloud.rd.domain.deployment.strategy.PublishHandler;
import cn.meshed.cloud.rd.domain.deployment.strategy.PublishType;
import cn.meshed.cloud.rd.domain.deployment.strategy.dto.ServicePublish;
import cn.meshed.cloud.rd.domain.project.gateway.ModelGateway;
import cn.meshed.cloud.utils.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Component
public class RpcPublishHandler extends AbstractServicePublish implements PublishHandler<ServicePublish> {

    public RpcPublishHandler(ModelGateway modelGateway, CliGateway cliGateway) {
        super(modelGateway, cliGateway);
    }

    /**
     * 注册类型
     *
     * @return PublishType
     */
    @Override
    public PublishType getPublishType() {
        return PublishType.RPC;
    }

    /**
     * 发布模型
     *
     * @param servicePublish 模型发布
     */
    @Override
    public void publish(ServicePublish servicePublish) {
        String projectKey = servicePublish.getProjectKey();
        AssertUtils.isTrue(StringUtils.isNotBlank(projectKey), "项目key不允许为空");

    }


}
