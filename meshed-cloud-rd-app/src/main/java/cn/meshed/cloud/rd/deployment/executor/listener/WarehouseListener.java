package cn.meshed.cloud.rd.deployment.executor.listener;

import cn.meshed.cloud.rd.deployment.event.WarehouseInitializeEvent;
import cn.meshed.cloud.rd.deployment.executor.command.WarehouseInitByProjectEventExe;
import cn.meshed.cloud.rd.deployment.executor.command.WarehouseInitializeMemberEventExe;
import cn.meshed.cloud.rd.deployment.executor.command.WarehouseInitializeSkeletonEventExe;
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
public class WarehouseListener {

    private final WarehouseInitByProjectEventExe warehouseInitByProjectEventExe;
    private final WarehouseInitializeSkeletonEventExe warehouseInitializeSkeletonEventExe;
    private final WarehouseInitializeMemberEventExe warehouseInitializeMemberEventExe;

    @Bean
    public Consumer<ProjectInitializeEvent> initProjectWarehouse() {
        return warehouseInitByProjectEventExe::execute;
    }

    @Bean
    public Consumer<WarehouseInitializeEvent> initWarehouseSkeleton() {
        return warehouseInitializeSkeletonEventExe::execute;
    }

    @Bean
    public Consumer<WarehouseInitializeEvent> initWarehouseMember() {
        return warehouseInitializeMemberEventExe::execute;
    }


}
