package cn.meshed.cloud.rd.deployment.strategy;

import cn.meshed.cloud.rd.ProviderApplication;
import cn.meshed.cloud.rd.deployment.gatewayimpl.strategy.ClientPublishHandler;
import cn.meshed.cloud.rd.domain.deployment.strategy.AsyncPublishStrategy;
import cn.meshed.cloud.rd.domain.deployment.strategy.PublishType;
import cn.meshed.cloud.rd.domain.deployment.strategy.dto.ClientPublish;
import cn.meshed.cloud.rd.domain.repo.Branch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static cn.meshed.cloud.rd.domain.repo.constant.RepoConstant.MASTER;
import static cn.meshed.cloud.rd.domain.repo.constant.RepoConstant.WORKSPACE;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@SpringBootTest(classes = ProviderApplication.class)
@RunWith(SpringRunner.class)
public class AsyncPublishStrategyTest {

    @Autowired
    private AsyncPublishStrategy asyncPublishStrategy;
    @Autowired
    private ClientPublishHandler clientPublishHandler;

    @Test
    public void asyncPublish() {

        ClientPublish clientPublish = new ClientPublish();
        clientPublish.setProjectKey("PROPERTY");
        clientPublish.setBasePackage("cn.meshed.cloud.property");
        clientPublish.setRepositoryId("3219116");
        clientPublish.setCommitMessage("生成客户端接口合模型");
        clientPublish.setBranch(new Branch(WORKSPACE, MASTER));
        asyncPublishStrategy.asyncPublish(PublishType.CLIENT, clientPublish);

    }
}