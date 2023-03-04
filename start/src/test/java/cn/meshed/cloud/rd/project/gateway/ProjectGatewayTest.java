package cn.meshed.cloud.rd.project.gateway;

import cn.meshed.cloud.rd.ProviderApplication;
import cn.meshed.cloud.rd.domain.project.Project;
import cn.meshed.cloud.rd.domain.project.gateway.ProjectGateway;
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
public class ProjectGatewayTest {

    @Autowired
    private ProjectGateway projectGateway;

    @Test
    public void save() {

    }

    @Test
    public void queryByKey() {
        Project project = projectGateway.queryByKey("project");
        System.out.println(project);
    }
}