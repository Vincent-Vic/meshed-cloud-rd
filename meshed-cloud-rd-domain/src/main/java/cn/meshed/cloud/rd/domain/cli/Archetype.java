package cn.meshed.cloud.rd.domain.cli;

import lombok.Data;

import java.io.Serializable;

/**
 * <h1>原型</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class Archetype implements Serializable {


    /**
     * setting 文件路径 （原型如果在私服需要配置读取到运行环境本地）
     */
    private String settingPath;

    /**
     * 分组ID
     */
    private String archetypeGroupId;

    /**
     * 制品ID
     */
    private String archetypeArtifactId;

    /**
     * 版本号
     */
    private String archetypeVersion;
}
