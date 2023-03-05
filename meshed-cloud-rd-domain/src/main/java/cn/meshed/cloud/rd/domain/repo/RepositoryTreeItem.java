package cn.meshed.cloud.rd.domain.repo;

import lombok.Data;

/**
 * <h1>仓库文件树</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class RepositoryTreeItem {

    /**
     * 文件版本，唯一标识
     */
    private String id;
    /**
     * 类型、权限信息
     */
    private String mode;

    /**
     * 文件类型 tree: 目录 blob：文件 commit：commit支持submodule
     */
    private String type;

    /**
     * 文件路径
     */
    private String name;

    /**
     * 类型、权限信息
     */
    private Boolean isLFS;

    /**
     * 文件路径
     */
    private String path;


}
