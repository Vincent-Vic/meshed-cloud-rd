package cn.meshed.cloud.rd.domain.deployment.gateway;

import cn.meshed.cloud.rd.domain.deployment.CreateRepository;
import cn.meshed.cloud.rd.domain.deployment.CreateRepositoryGroup;
import cn.meshed.cloud.rd.domain.deployment.Repository;
import cn.meshed.cloud.rd.domain.deployment.RepositoryGroup;

/**
 * <h1>代码仓库</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public interface RepositoryGateway {

    /**
     * 创建仓库
     *
     * @param createRepository 代码仓库参数
     * @return 仓库信息
     */
    Repository createRepository(CreateRepository createRepository);


    /**
     * 查询代码库，支持按代码库ID和代码库路径(Path)查询
     *
     * @param identity 代码库ID或路径
     * @return 仓库信息
     */
    Repository getRepository(String identity);

    /**
     * 创建分组/项目（映射项目）
     *
     * @param createRepositoryGroup 分组名称
     * @return 分组信息
     */
    RepositoryGroup createRepositoryGroup(CreateRepositoryGroup createRepositoryGroup);
}
