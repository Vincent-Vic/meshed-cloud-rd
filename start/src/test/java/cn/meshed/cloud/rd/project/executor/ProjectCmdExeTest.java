package cn.meshed.cloud.rd.project.executor;

import cn.meshed.cloud.rd.ProviderApplication;
import cn.meshed.cloud.rd.project.command.ProjectCmd;
import cn.meshed.cloud.rd.project.enums.ProjectAccessModeEnum;
import cn.meshed.cloud.rd.project.enums.ProjectTypeEnum;
import cn.meshed.cloud.rd.project.executor.command.ProjectCmdExe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@SpringBootTest(classes = ProviderApplication.class)
@RunWith(SpringRunner.class)
public class ProjectCmdExeTest {

    @Autowired
    private ProjectCmdExe projectCmdExe;

    @Test
    public void execute() {
        ProjectCmd projectCmd = new ProjectCmd();
        projectCmd.setDescription("test xxx");
        projectCmd.setName("资产中心");
        projectCmd.setKey("xxx");
        projectCmd.setType(ProjectTypeEnum.SERVICE);
        projectCmd.setAccessMode(ProjectAccessModeEnum.PUBLIC);
        projectCmd.setCodeTemplates(Arrays.asList("COLA"));
        projectCmdExe.execute(projectCmd);


    }
}