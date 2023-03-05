package cn.meshed.cloud.rd.cli.gateway;

import cn.hutool.core.io.FileUtil;
import cn.meshed.cloud.rd.domain.repo.RepositoryFile;

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
public class FileTest {

    public static void main(String[] args) {
        String projectPath = "D:\\Temp\\meshed-cloud-test";
        List<File> list = FileUtil.loopFiles(projectPath);
        List<RepositoryFile> repositoryFiles = new ArrayList<>();
        for (File file : list) {
            String content = FileUtil.readString(file, StandardCharsets.UTF_8);
            String path = file.getPath().substring(projectPath.length() + 1).replaceAll("\\\\", "/");
            repositoryFiles.add(new RepositoryFile(path, content));
        }
    }
}
