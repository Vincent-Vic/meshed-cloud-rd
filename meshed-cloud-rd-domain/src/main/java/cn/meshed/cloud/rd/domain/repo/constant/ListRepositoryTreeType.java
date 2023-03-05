package cn.meshed.cloud.rd.domain.repo.constant;

/**
 * <h1>查找策略</h1>
 * DIRECT：仅展示当前目录下的内容
 * RECURSIVE：递归查找当前路径下的所有文件
 * FLATTEN：扁平化展示（如果是目录，递归查找，直到子目录包含文件或多个目录为止）
 *
 * @author Vincent Vic
 * @version 1.0
 */
public enum ListRepositoryTreeType {

    /**
     * 查找策略 DIRECT：仅展示当前目录下的内容
     * RECURSIVE：递归查找当前路径下的所有文件
     * FLATTEN：扁平化展示（如果是目录，递归查找，直到子目录包含文件或多个目录为止）
     */
    DIRECT,
    RECURSIVE,
    FLATTEN,
    ;

}
