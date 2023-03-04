package cn.meshed.cloud.rd.deployment.gateway;

import cn.meshed.cloud.rd.ProviderApplication;
import cn.meshed.cloud.rd.deployment.executor.query.ScaffoldTemplateQryExe;
import cn.meshed.cloud.rd.domain.deployment.CreateRepository;
import cn.meshed.cloud.rd.domain.deployment.Repository;
import cn.meshed.cloud.rd.domain.deployment.ScaffoldTemplate;
import cn.meshed.cloud.rd.domain.deployment.gateway.RepositoryGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@SpringBootTest(classes = ProviderApplication.class)
@RunWith(SpringRunner.class)
public class CodeupCreateRepositoryGatewayTest {

    @Autowired
    private RepositoryGateway repositoryGateway;
    @Autowired
    private ScaffoldTemplateQryExe scaffoldTemplateQryExe;

    @Test
    public void scaffoldTemplateQryExe() {
        List<ScaffoldTemplate> cola = scaffoldTemplateQryExe.execute("cola");
        System.out.println(cola);
    }

    @Test
    public void createRepository() {
        CreateRepository createRepository = new CreateRepository();
        createRepository.setRepositoryName("meshed-cloud-property9");
        createRepository.setDescription("xxxxx");
        createRepository.setVisible(true);
        createRepository.setNamespaceId(1092561L);
        Repository repository = repositoryGateway.createRepository(createRepository);
        System.out.println(repository);
    }
}