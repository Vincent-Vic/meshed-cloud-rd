package cn.meshed.cloud.rd.cli.executor.command;

import cn.hutool.core.io.FileUtil;
import cn.meshed.cloud.cqrs.CommandExecute;
import cn.meshed.cloud.rd.cli.executor.query.ArchetypeTemplateQry;
import cn.meshed.cloud.rd.domain.cli.Archetype;
import cn.meshed.cloud.rd.domain.cli.Artifact;
import cn.meshed.cloud.rd.domain.cli.Skeleton;
import cn.meshed.cloud.rd.domain.cli.gateway.CliGateway;
import cn.meshed.cloud.rd.domain.repo.CommitRepositoryFile;
import cn.meshed.cloud.rd.domain.repo.CreateBranch;
import cn.meshed.cloud.rd.domain.repo.RepositoryFile;
import cn.meshed.cloud.rd.domain.repo.gateway.RepositoryGateway;
import cn.meshed.cloud.utils.IdUtils;
import cn.meshed.cloud.utils.ResultUtils;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.SysException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static cn.meshed.cloud.rd.domain.repo.constant.RepoConstant.MASTER;


/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
public class SkeletonCmdExe implements CommandExecute<Skeleton, SingleResponse<String>> {

    private final CliGateway cliGateway;
    private final ArchetypeTemplateQry archetypeTemplateQry;
    private final RepositoryGateway repositoryGateway;
    @Value("${rd.cli.workspace}")
    private String workspace;
    @Value("${rd.repo.task.automate-branch-format}")
    private String automateBranchFormat;


    /**
     * <h1>执行器</h1>
     *
     * @param skeleton 执行器 {@link Skeleton}
     * @return {@link Response}
     */
    @Override
    public SingleResponse<String> execute(Skeleton skeleton) {
        //校验
        skeleton.verification();

        //获取原型模板
        Archetype archetype = archetypeTemplateQry.execute(skeleton.getEngineTemplate());
        if (archetype == null) {
            return ResultUtils.fail("模板不存在");
        }

        Artifact artifact = new Artifact(skeleton.getBasePackage(), skeleton.getRepositoryName(), true);
        artifact.addExtended("domain", "examples");
        artifact.addExtended("projectKey", skeleton.getProjectKey());

        //生成根路径
        String workspacePath = getWorkspacePath();

        try {
            cliGateway.archetype(workspacePath, archetype, artifact);
        } catch (SysException sysException) {
            return ResultUtils.fail(sysException.getMessage());
        }

        //上传仓库
        return commitProject(skeleton, workspacePath);
    }

    /**
     * 提交项目代码至仓库
     *
     * @param skeleton      骨架信息
     * @param workspacePath 本地创建项目临时目录
     * @return 上传状态
     */
    @Nullable
    private SingleResponse<String> commitProject(Skeleton skeleton, String workspacePath) {
        //创建分支
        String branch = String.format(automateBranchFormat, "initialize", "scaffold");
        if (!repositoryGateway.createBranch(new CreateBranch(skeleton.getRepositoryId(), branch, MASTER))) {
            return ResultUtils.fail("分支创建失败");
        }
        String projectPath = workspacePath + "/" + skeleton.getRepositoryName();
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
            commitRepositoryFile.setRepositoryId(skeleton.getRepositoryId());
            commitRepositoryFile.setCommitMessage("Initialize scaffold");
            commitRepositoryFile.setBranchName(branch);
            commitRepositoryFile.setFiles(repositoryFiles);
            int commitCount = repositoryGateway.commitRepositoryFile(commitRepositoryFile);
            if (commitCount == repositoryFiles.size()) {
                return ResultUtils.of(branch);
            }
            return ResultUtils.fail(String.format("骨架代码提交数：%s,成功：%s", repositoryFiles.size(), commitCount));
        }
        return ResultUtils.of("模板无代码需要提交");
    }

    @NotNull
    private String getWorkspacePath() {
        if (!workspace.endsWith("/")) {
            workspace = workspace + "/";
        }
        return workspace + IdUtils.simpleUUID() + "/";
    }
}
