package cn.meshed.cloud.rd.deployment.gatewayimpl;

import cn.hutool.http.HttpStatus;
import cn.meshed.cloud.rd.domain.deployment.CreateRepository;
import cn.meshed.cloud.rd.domain.deployment.CreateRepositoryGroup;
import cn.meshed.cloud.rd.domain.deployment.Repository;
import cn.meshed.cloud.rd.domain.deployment.RepositoryGroup;
import cn.meshed.cloud.rd.domain.deployment.gateway.RepositoryGateway;
import cn.meshed.cloud.utils.AssertUtils;
import com.alibaba.cola.exception.SysException;
import com.aliyun.devops20210625.Client;
import com.aliyun.devops20210625.models.CreateRepositoryGroupRequest;
import com.aliyun.devops20210625.models.CreateRepositoryGroupResponse;
import com.aliyun.devops20210625.models.CreateRepositoryGroupResponseBody;
import com.aliyun.devops20210625.models.CreateRepositoryRequest;
import com.aliyun.devops20210625.models.CreateRepositoryResponse;
import com.aliyun.devops20210625.models.CreateRepositoryResponseBody;
import com.aliyun.devops20210625.models.GetRepositoryRequest;
import com.aliyun.devops20210625.models.GetRepositoryResponse;
import com.aliyun.devops20210625.models.GetRepositoryResponseBody;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <h1>存储库 - 云效实现 </h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
public class CodeupRepositoryGatewayImpl implements RepositoryGateway {

    private final Client client;
    private final Integer VISIBILITY = 10;

    @Value("${rd.warehouse.codeup.organization-id}")
    private String organizationId;
    @Value("${rd.warehouse.codeup.root-group}")
    private Long rootGroup;

    /**
     * 创建仓库
     *
     * @param createRepository 代码仓库参数
     * @return 仓库信息
     */
    @Override
    public Repository createRepository(CreateRepository createRepository) {
        AssertUtils.isTrue(StringUtils.isNotBlank(createRepository.getRepositoryName()), "存储库名称不能为空");

        try {

            CreateRepositoryResponse repositoryResponse = client.createRepository(buildCreateRepositoryRequest(createRepository));
            CreateRepositoryResponseBody repositoryBody = repositoryResponse.getBody();
            if (repositoryResponse.getStatusCode() == HttpStatus.HTTP_OK && repositoryBody.getSuccess()) {
                Repository repository = new Repository();
                CreateRepositoryResponseBody.CreateRepositoryResponseBodyResult result = repositoryBody.getResult();
                repository.setRepoUrl(result.getHttpUrlToRepo());
                repository.setRepositoryId(result.getId());
                repository.setRepositoryName(result.getName());
                return repository;
            }
            throw new SysException(repositoryBody.errorCode + "#" + repositoryBody.errorMessage);
        } catch (Exception e) {
            throw new SysException(e.getMessage());
        }
    }

    /**
     * 查询代码库，支持按代码库ID和代码库路径(Path)查询
     *
     * @param identity 代码库ID或路径
     * @return 仓库信息
     */
    @Override
    public Repository getRepository(String identity) {
        GetRepositoryRequest getRepositoryRequest = new GetRepositoryRequest();
        getRepositoryRequest.setOrganizationId(organizationId);
        getRepositoryRequest.setIdentity(organizationId + "/" + identity);
        try {
            GetRepositoryResponse repositoryResponse = client.getRepository(getRepositoryRequest);
            GetRepositoryResponseBody repositoryBody = repositoryResponse.getBody();
            if (repositoryResponse.getStatusCode() == HttpStatus.HTTP_OK && repositoryBody.getSuccess()) {
                Repository repository = new Repository();
                GetRepositoryResponseBody.GetRepositoryResponseBodyRepository result = repositoryBody.getRepository();
                repository.setRepoUrl(result.getHttpUrlToRepository());
                repository.setRepositoryId(result.getId());
                repository.setRepositoryName(result.getName());
                return repository;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @NotNull
    private CreateRepositoryRequest buildCreateRepositoryRequest(CreateRepository createRepository) {
        CreateRepositoryRequest request = new CreateRepositoryRequest();
        request.setOrganizationId(organizationId);
        request.setSync(true);
        request.setName(createRepository.getRepositoryName());
        request.setNamespaceId(createRepository.getNamespaceId());
        if (StringUtils.isNotBlank(createRepository.getDescription())) {
            request.setDescription(createRepository.getDescription());
        }
        if (createRepository.isVisible()) {
            //0 私有（默认） 10 企业内部公开
            request.setVisibilityLevel(VISIBILITY);
        }
        if (StringUtils.isNotBlank(createRepository.getImportUrl())) {
            request.setImportUrl(createRepository.getImportUrl());
        }
        if (StringUtils.isNotBlank(createRepository.getImportUrl())) {
            request.setImportUrl(createRepository.getImportUrl());
        }
        return request;
    }

    /**
     * 创建分组/项目（映射项目）
     *
     * @param createRepositoryGroup 分组名称
     * @return 分组信息
     */
    @Override
    public RepositoryGroup createRepositoryGroup(CreateRepositoryGroup createRepositoryGroup) {
        AssertUtils.isTrue(StringUtils.isNotBlank(createRepositoryGroup.getGroupName()), "分组名称不能为空");
        CreateRepositoryGroupRequest request = new CreateRepositoryGroupRequest();
        request.setOrganizationId(organizationId);
        request.setDescription(createRepositoryGroup.getDescription());
        request.setName(createRepositoryGroup.getGroupName());
        request.setPath(createRepositoryGroup.getGroupName());
        request.setParentId(rootGroup);
        if (createRepositoryGroup.isVisible()) {
            request.setVisibilityLevel(VISIBILITY);
        }
        try {
            CreateRepositoryGroupResponse repositoryGroupResponse = client.createRepositoryGroup(request);
            CreateRepositoryGroupResponseBody groupResponseBody = repositoryGroupResponse.getBody();
            if (repositoryGroupResponse.getStatusCode() == HttpStatus.HTTP_OK && groupResponseBody.getSuccess()) {
                RepositoryGroup repositoryGroup = new RepositoryGroup();
                CreateRepositoryGroupResponseBody.CreateRepositoryGroupResponseBodyResult result
                        = groupResponseBody.getResult();
                repositoryGroup.setGroupId(result.getId());
                repositoryGroup.setGroupName(result.getName());
                return repositoryGroup;
            }
            throw new SysException(groupResponseBody.errorCode + "#" + groupResponseBody.errorMessage);
        } catch (Exception e) {
            throw new SysException(e.getMessage());
        }
    }


}
