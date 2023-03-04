package cn.meshed.cloud.rd.deployment.executor.listener;

import cn.meshed.cloud.rd.deployment.executor.command.SystemConfigByProjectEventExe;
import cn.meshed.cloud.rd.project.event.ProjectInitializeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

/**
 * <h1>仓库统一监听器</h1>
 * SCS 函数式方案监听器统一大类管理方法
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Configuration
public class SystemConfigListener {

    private final SystemConfigByProjectEventExe systemConfigByProjectEventExe;

    @Bean
    public Consumer<ProjectInitializeEvent> initSystemConfig() {
        return systemConfigByProjectEventExe::execute;
    }
}
