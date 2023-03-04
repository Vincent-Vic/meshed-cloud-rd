package cn.meshed.cloud.rd.domain.project.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@AllArgsConstructor
@Getter
@ToString
public enum GroupTypeEnum {

    /**
     * 模型
     */
    MODEL(1, "model"),
    /**
     * 请求
     */
    REQUEST_PARAM(2, "request_param"),
    /**
     * 请求体
     */
    REQUEST_BODY(3, "request_body"),
    /**
     * 返回
     */
    RESPONSE(4, "response"),
    ;

    private final int value;
    private final String key;

}
