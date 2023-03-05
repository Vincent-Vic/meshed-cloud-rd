package cn.meshed.cloud.rd.deployment.executor.command;

import cn.hutool.core.io.FileUtil;
import cn.meshed.cloud.cqrs.EventExecute;
import cn.meshed.cloud.rd.cli.executor.command.SkeletonCmdExe;
import cn.meshed.cloud.rd.deployment.event.WarehouseInitializeEvent;
import cn.meshed.cloud.rd.domain.cli.Artifact;
import cn.meshed.cloud.rd.domain.cli.Skeleton;
import cn.meshed.cloud.rd.domain.repo.CommitRepositoryFile;
import cn.meshed.cloud.rd.domain.repo.RepositoryFile;
import cn.meshed.cloud.rd.domain.repo.gateway.RepositoryGateway;
import cn.meshed.cloud.utils.ResultUtils;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class WarehouseInitializeSkeletonEventExe implements EventExecute<WarehouseInitializeEvent, Response> {

    private final SkeletonCmdExe skeletonCmdExe;
    private final RepositoryGateway repositoryGateway;

    /**
     * <h1>执行器</h1>
     *
     * @param warehouseInitializeEvent 执行器 {@link WarehouseInitializeEvent}
     * @return {@link Void}
     */
    @Override
    public Response execute(WarehouseInitializeEvent warehouseInitializeEvent) {
        System.out.println("skeleton test");

        //参数校验
        if (StringUtils.isBlank(warehouseInitializeEvent.getProjectKey())) {
            return ResultUtils.fail("项目key不能为空");
        }

        //构建脚手架
        String projectPath = buildSkeleton(warehouseInitializeEvent);
        if (StringUtils.isEmpty(projectPath)) {
            return ResultUtils.fail("项目骨架构建失败");
        }
        //上传脚手架内容
        return commitProject(warehouseInitializeEvent, projectPath);
    }

    /**
     * 提交项目代码至仓库
     *
     * @param warehouseInitializeEvent
     * @param projectPath
     * @return
     */
    @Nullable
    private SingleResponse commitProject(WarehouseInitializeEvent warehouseInitializeEvent, String projectPath) {
        //读取上传文件信息
        List<File> list = FileUtil.loopFiles(projectPath);
        List<RepositoryFile> repositoryFiles = new ArrayList<>();
        for (File file : list) {
            String content = FileUtil.readString(file, StandardCharsets.UTF_8);
            String path = file.getPath().substring(projectPath.length()).replaceAll("\\\\", "/");
            repositoryFiles.add(new RepositoryFile(path, content));
        }

        //可能存在构建文件不存在
        if (CollectionUtils.isNotEmpty(repositoryFiles)) {
            CommitRepositoryFile commitRepositoryFile = new CommitRepositoryFile();
            commitRepositoryFile.setRepositoryId(warehouseInitializeEvent.getRepositoryId());
            commitRepositoryFile.setCommitMessage("Initialize scaffold");
            commitRepositoryFile.setBranchName("master");
            commitRepositoryFile.setFiles(repositoryFiles);
            int commitCount = repositoryGateway.commitRepositoryFile(commitRepositoryFile);
            if (commitCount == repositoryFiles.size()) {
                return ResultUtils.ok();
            }
            return ResultUtils.fail(String.format("骨架代码提交数：%s,成功：%s", repositoryFiles.size(), commitCount));
        }
        return ResultUtils.of("模板无代码需要提交");
    }

    /**
     * 构建脚手架
     *
     * @param warehouseInitializeEvent 仓库初始化事件
     * @return 返回生成的项目路径（含项目名称）
     */
    private String buildSkeleton(WarehouseInitializeEvent warehouseInitializeEvent) {
        Artifact artifact = new Artifact(warehouseInitializeEvent.getBasePackage(),
                warehouseInitializeEvent.getRepositoryName(), true);
        artifact.addExtended("domain", "examples");
        artifact.addExtended("projectKey", warehouseInitializeEvent.getProjectKey());

        Skeleton skeleton = new Skeleton();
        skeleton.setEngineTemplate(warehouseInitializeEvent.getEngineTemplate());
        skeleton.setArtifact(artifact);
        SingleResponse<String> response = skeletonCmdExe.execute(skeleton);
        if (response.isSuccess()) {
            return response.getData() + artifact.getArtifactId() + "/";
        }
        return null;
    }
}