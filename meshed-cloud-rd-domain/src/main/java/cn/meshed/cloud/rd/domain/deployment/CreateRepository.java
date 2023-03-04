package cn.meshed.cloud.rd.domain.deployment;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class CreateRepository {

    /**
     * 仓库名称
     */
    private String repositoryName;

    /**
     * 仓库可见性
     */
    private boolean visible;

    /**
     * 空间id/分组ID
     */
    private Long namespaceId;

    /**
     * 导入URL
     */
    private String importUrl;
    /**
     * 导入所在仓库的token 公开可无需
     */
    private String importToken;


    /**
     * 导入URL
     */
    private String description;

    public void setDescription(String name, String description) {
        if (StringUtils.isNotBlank(name)
                && StringUtils.isNotBlank(description)) {
            this.description = String.format("[%s]:%s", name, description);
        }
        this.description = description;
    }

}
