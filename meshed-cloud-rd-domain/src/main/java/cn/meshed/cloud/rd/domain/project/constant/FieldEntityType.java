package cn.meshed.cloud.rd.domain.project.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h1>实体类型</h1>
 * 实体类型 参数/body/路径/返回
 *
 * @author Vincent Vic
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public enum FieldEntityType {
    /**
     * 模型
     */
    MODEL(1, "model"),
    /**
     * 参数
     */
    PARAM(2, "param"),
    /**
     * body
     */
    BODY(3, "body"),
    /**
     * response
     */
    RESPONSE(4, "response"),
    ;

    private final int value;
    private final String key;
}
