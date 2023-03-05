package cn.meshed.cloud.rd.domain.repo;

import cn.meshed.cloud.rd.domain.repo.constant.ListRepositoryTreeType;
import lombok.Data;

/**
 * <h1>查询代码库的文件树</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class ListRepositoryTree {

    /**
     * 查找策略 DIRECT：仅展示当前目录下的内容 RECURSIVE：递归查找当前路径下的所有文件 FLATTEN：扁平化展示（如果是目录，递归查找，直到子目录包含文件或多个目录为止）
     */
    private ListRepositoryTreeType type;

    /**
     * 代码库ID
     */
    private Long repositoryId;

    /**
     * 代码仓库内的文件路径，获取该路径下的文件目录，默认获取代码库文件树的全部结构
     */
    private String path;

    /**
     * 分支名称、标签名称或CommitID，默认为代码库库默认分支
     */
    private String refName;


}
