package cn.meshed.cloud.rd.domain.project;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <h1>领域</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Domain implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 领域唯一值
     */
    private String key;

    /**
     * 领域名称
     */
    private String name;

    /**
     * 所属项目key
     */
    private String projectKey;


}
