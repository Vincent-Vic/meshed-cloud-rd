package cn.meshed.cloud.rd.deployment.executor;

import cn.meshed.cloud.rd.deployment.command.WarehouseAddCmd;
import cn.meshed.cloud.rd.deployment.command.WarehouseImportCmd;
import cn.meshed.cloud.rd.deployment.data.WarehouseDTO;
import cn.meshed.cloud.rd.deployment.executor.command.WarehouseAddCmdExe;
import cn.meshed.cloud.rd.deployment.executor.command.WarehouseImportCmdExe;
import cn.meshed.cloud.rd.deployment.query.WarehousePageQry;
import cn.meshed.cloud.rd.domain.deployment.ability.WarehouseAbility;
import com.alibaba.cola.dto.PageResponse;
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
public class WarehouseAbilityImpl implements WarehouseAbility {

    private final WarehouseAddCmdExe warehouseAddCmdExe;
    private final WarehouseImportCmdExe warehouseImportCmdExe;

    /**
     * 仓库列表
     *
     * @param warehousePageQry 仓库分页查询
     * @return
     */
    @Override
    public PageResponse<WarehouseDTO> list(WarehousePageQry warehousePageQry) {
        return null;
    }

    /**
     * 新增仓库
     *
     * @param warehouseAddCmd 仓库新增参数
     * @return {@link Response}
     */
    @Override
    public Response add(WarehouseAddCmd warehouseAddCmd) {
        return warehouseAddCmdExe.execute(warehouseAddCmd);
    }

    /**
     * 注册信息/导入仓库
     *
     * @param warehouseImportCmd 仓库登记/导入功能
     * @return {@link Response}
     */
    @Override
    public Response warehouseImport(WarehouseImportCmd warehouseImportCmd) {
        return warehouseImportCmdExe.execute(warehouseImportCmd);
    }
}
