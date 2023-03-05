package cn.meshed.cloud.rd.domain.repo;

import lombok.Data;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class Repository {

    /**
     * 存储库URL 带 .git
     */
    private String repoUrl;

    /**
     * 仓库ID
     */
    private Long repositoryId;

    /**
     * 仓库名称
     */
    private String repositoryName;
}
