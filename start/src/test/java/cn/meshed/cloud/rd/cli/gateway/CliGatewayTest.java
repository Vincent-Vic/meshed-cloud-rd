package cn.meshed.cloud.rd.cli.gateway;

import cn.meshed.cloud.rd.ProviderApplication;
import cn.meshed.cloud.rd.domain.cli.Archetype;
import cn.meshed.cloud.rd.domain.cli.Artifact;
import cn.meshed.cloud.rd.domain.cli.gateway.CliGateway;
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
public class CliGatewayTest {

    @Autowired
    private CliGateway cliGateway;

    @Test
    public void archetype() {

        Archetype archetype = new Archetype();
        archetype.setArchetypeGroupId("cn.meshed.archetype");
        archetype.setArchetypeArtifactId("meshed-cloud-archetype");
        archetype.setArchetypeVersion("1.0.0-SNAPSHOT");
        Artifact artifact = new Artifact();
        artifact.setArtifactId("meshed-cloud-test");
        artifact.setGroupId("cn.meshed.cloud.test");
        artifact.setVersion("1.0.0");
        artifact.enableSnapshot();
        artifact.setPackageName("cn.meshed.cloud.test");
        artifact.addExtended("domain", "auto");
        artifact.addExtended("projectKey", "test");

        cliGateway.archetype("D://temp//", archetype, artifact);
    }
}