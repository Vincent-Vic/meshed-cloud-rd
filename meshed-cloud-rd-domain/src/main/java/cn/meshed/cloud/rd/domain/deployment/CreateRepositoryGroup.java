package cn.meshed.cloud.rd.domain.deployment;

import lombok.Data;

/**
 * <h1>创建存储库分组</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class CreateRepositoryGroup {

    /**
     * 分组描述
     */
    String description;
    /**
     * 分组名称 （只能包含字母和数字、 '_'、 '.'和'-'，且只能以字母、数字或'_'开头）
     */
    private String groupName;
    /**
     * 分组可见性
     */
    private boolean visible;

}
