package cn.meshed.cloud.rd.project.executor;

import cn.meshed.cloud.rd.ProviderApplication;
import cn.meshed.cloud.rd.project.command.ServiceGroupCmd;
import cn.meshed.cloud.rd.project.enums.ServiceTypeEnum;
import cn.meshed.cloud.rd.project.executor.command.ServiceGroupCmdExe;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@SpringBootTest(classes = ProviderApplication.class)
@RunWith(SpringRunner.class)
public class ServiceGroupCmdExeTest {

    @Autowired
    private ServiceGroupCmdExe serviceGroupCmdExe;

    @Test
    public void execute() {
        serviceGroupCmdExe.execute(buildMockServiceCmd());
    }

    @NotNull
    private ServiceGroupCmd buildMockServiceCmd() {
        ServiceGroupCmd serviceGroupCmd = new ServiceGroupCmd();
        serviceGroupCmd.setDomain("property");
        serviceGroupCmd.setDescription("project xxx");
        serviceGroupCmd.setName("资产服务控制器");
        serviceGroupCmd.setProjectKey("Property");
        serviceGroupCmd.setKey("Property");
//        serviceGroupCmd.setUri("/property");
        serviceGroupCmd.setType(ServiceTypeEnum.RPC);
        return serviceGroupCmd;
    }
}