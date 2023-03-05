package cn.meshed.cloud.rd.repo.executor;

import cn.meshed.cloud.rd.ProviderApplication;
import cn.meshed.cloud.rd.deployment.event.WarehouseInitializeEvent;
import cn.meshed.cloud.rd.deployment.executor.command.WarehouseInitializeSkeletonEventExe;
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
public class WarehouseInitializeSkeletonEventExeTest {

    @Autowired
    private WarehouseInitializeSkeletonEventExe warehouseInitializeSkeletonEventExe;

    @Test
    public void execute() {
        WarehouseInitializeEvent event = new WarehouseInitializeEvent();
        event.setEngineTemplate("meshed-cloud-archetype");
        event.setBasePackage("cn.meshed.cloud.property");
        event.setRepositoryId(3217159L);
        event.setRepositoryName("meshed-cloud-property");
        event.setProjectKey("property");
        warehouseInitializeSkeletonEventExe.execute(event);
    }
}