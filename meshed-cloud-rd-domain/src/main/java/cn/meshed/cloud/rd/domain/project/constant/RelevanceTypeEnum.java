package cn.meshed.cloud.rd.domain.project.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * <h1>字段关联类型</h1>
 * 模型/参数/body/响应/方法参数/方法返回
 *
 * @author Vincent Vic
 * @version 1.0
 */
@AllArgsConstructor
@Getter
@ToString
public enum RelevanceTypeEnum {

    /**
     * 模型
     */
    MODEL(1, "model"),
    /**
     * 请求
     */
    REQUEST(2, "request"),
    /**
     * 响应
     */
    RESPONSE(3, "response"),
    ;

    private final int value;
    private final String key;

}
