package cn.meshed.cloud.rd.domain.repo;

import lombok.Data;

/**
 * <h1>创建分支</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class Branch {

    /**
     * 分支名称
     */
    private String branchName;

    /**
     * 来源分支
     */
    private String ref;

    public Branch(String branchName, String ref) {
        this.branchName = branchName;
        this.ref = ref;
    }
}
