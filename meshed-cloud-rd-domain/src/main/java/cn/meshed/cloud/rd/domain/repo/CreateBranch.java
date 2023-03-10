package cn.meshed.cloud.rd.domain.repo;

import lombok.Data;

/**
 * <h1>创建分支</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class CreateBranch {

    /**
     * 仓库ID
     */
    private String repositoryId;

    /**
     * 分支名称
     */
    private String branchName;

    /**
     * 来源分支
     */
    private String ref;

    public CreateBranch(String repositoryId, String branchName, String ref) {
        this.repositoryId = repositoryId;
        this.branchName = branchName;
        this.ref = ref;
    }
}
