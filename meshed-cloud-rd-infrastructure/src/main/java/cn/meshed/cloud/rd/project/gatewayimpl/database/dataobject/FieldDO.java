package cn.meshed.cloud.rd.project.gatewayimpl.database.dataobject;

import cn.meshed.cloud.rd.domain.project.constant.RelevanceTypeEnum;
import cn.meshed.cloud.rd.project.enums.BaseGenericsEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("m_field")
public class FieldDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段类型（对象类型）
     */
    private String fieldType;

    /**
     * 字段泛型
     */
    private BaseGenericsEnum generic;

    /**
     * 字段描述
     */
    @TableField(value = "`explain`")
    private String explain;

    /**
     * 字段不为空（布尔）
     */
    private Boolean nonNull;

    /**
     * 字段模拟数据
     */
    private String mock;

    /**
     * 字段规则 （json）
     */
    private String rule;

    /**
     * 字段关联ID（模型/服务的ID）
     */
    private String relevanceId;

    /**
     * 字段关联类型 模型/参数/body/返回/方法参数/方法返回
     */
    private RelevanceTypeEnum relevanceType;


}
