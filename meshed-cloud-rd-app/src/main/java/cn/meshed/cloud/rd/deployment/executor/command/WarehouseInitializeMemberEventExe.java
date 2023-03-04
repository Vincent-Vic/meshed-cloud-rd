package cn.meshed.cloud.rd.deployment.executor.command;

import cn.meshed.cloud.cqrs.EventExecute;
import cn.meshed.cloud.rd.deployment.event.WarehouseInitializeEvent;
import org.springframework.stereotype.Component;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Component
public class WarehouseInitializeMemberEventExe implements EventExecute<WarehouseInitializeEvent, Void> {
    /**
     * <h1>执行器</h1>
     *
     * @param warehouseInitializeEvent 执行器 {@link WarehouseInitializeEvent}
     * @return {@link Void}
     */
    @Override
    public Void execute(WarehouseInitializeEvent warehouseInitializeEvent) {
        System.out.println("member xxx " + warehouseInitializeEvent.toString());

        return null;
    }
}
