package cn.meshed.cloud.rd.domain.deployment;

import lombok.Data;

/**
 * <h1>脚手架 - 项目骨架模板</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class ScaffoldTemplate {

    /**
     * 业务上的唯一，应该业务存在多个子模板，可根据此分组
     */
    private String key;

    /**
     * 目标类型
     */
    private String type;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 模板名称格式
     */
    private String format;

    /**
     * 模板语言
     */
    private String language;

    /**
     * 模板构建引擎 （本质模板）
     */
    private String engine;
}
