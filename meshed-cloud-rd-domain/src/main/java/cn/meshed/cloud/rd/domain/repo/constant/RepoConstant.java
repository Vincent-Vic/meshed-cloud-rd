package cn.meshed.cloud.rd.domain.repo.constant;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public interface RepoConstant {

    /**
     * 主分支
     */
    String MASTER = "master";
    /**
     * 正式环境分支
     */
    String RELEASE = "release";
    /**
     * 研发分支
     */
    String DEVELOP = "develop";
    /**
     * 工作分支 （系统操作的分支）
     */
    String WORKSPACE = "workspace";
}
