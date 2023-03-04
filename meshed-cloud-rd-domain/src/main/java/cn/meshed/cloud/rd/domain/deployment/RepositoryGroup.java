package cn.meshed.cloud.rd.domain.deployment;

import lombok.Data;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class RepositoryGroup {

    /**
     * 分组Id
     */
    private Long groupId;

    /**
     * 分组名称
     */
    private String groupName;

}
