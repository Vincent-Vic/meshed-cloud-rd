package cn.meshed.cloud.rd.domain.deployment.gateway;

import cn.meshed.cloud.rd.domain.deployment.strategy.dto.ClientPublish;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public interface VersionGateway {

    /**
     * 发布客户端版本
     *
     * @param clientPublish 客户端版本
     * @return
     */
    boolean publishClientVersion(ClientPublish clientPublish);
}
