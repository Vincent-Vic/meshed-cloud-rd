package cn.meshed.cloud.rd.project.gatewayimpl.database.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author by Vincent Vic
 * @since 2023-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("m_domain")
public class DomainDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 领域唯一值
     */
    @TableField("`key`")
    private String key;

    /**
     * 领域名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 所属项目key
     */
    private String projectKey;


}
