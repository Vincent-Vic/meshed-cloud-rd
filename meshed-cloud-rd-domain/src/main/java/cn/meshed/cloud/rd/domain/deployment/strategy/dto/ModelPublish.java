package cn.meshed.cloud.rd.domain.deployment.strategy.dto;

import cn.meshed.cloud.rd.domain.deployment.strategy.PublishData;
import lombok.Data;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class ModelPublish implements PublishData {

    /**
     * 项目key
     */
    private String projectKey;

    /**
     * 存储库ID
     */
    private String repositoryId;

}
