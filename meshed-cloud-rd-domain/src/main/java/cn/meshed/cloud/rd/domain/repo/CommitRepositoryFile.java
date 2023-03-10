package cn.meshed.cloud.rd.domain.repo;

import lombok.Data;

import java.util.List;

/**
 * <h1>提交代码文件信息</h1>
 * 单个文件信息
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class CommitRepositoryFile {

    /**
     * 仓库ID
     */
    private String repositoryId;

    /**
     * 提交分支
     */
    private String branchName;

    /**
     * 提交信息
     */
    private String commitMessage;

    /**
     * 提交文件
     */
    private List<RepositoryFile> files;

}
