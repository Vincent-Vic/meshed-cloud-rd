package cn.meshed.cloud.rd.deployment.executor.command;

import cn.meshed.cloud.cqrs.CommandExecute;
import cn.meshed.cloud.rd.deployment.command.WarehouseImportCmd;
import com.alibaba.cola.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
public class WarehouseImportCmdExe implements CommandExecute<WarehouseImportCmd, Response> {
    /**
     * <h1>执行器</h1>
     *
     * @param cmd 执行器 {@link WarehouseImportCmd}
     * @return {@link Response}
     */
    @Override
    public Response execute(WarehouseImportCmd cmd) {
        return null;
    }
}
