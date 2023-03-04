package cn.meshed.cloud.rd.domain.deployment.ability;

import cn.meshed.cloud.rd.deployment.command.WarehouseAddCmd;
import cn.meshed.cloud.rd.deployment.command.WarehouseImportCmd;
import cn.meshed.cloud.rd.deployment.data.WarehouseDTO;
import cn.meshed.cloud.rd.deployment.query.WarehousePageQry;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public interface WarehouseAbility {

    /**
     * 仓库列表
     *
     * @param warehousePageQry 仓库分页查询
     * @return
     */
    PageResponse<WarehouseDTO> list(WarehousePageQry warehousePageQry);

    /**
     * 新增仓库
     *
     * @param warehouseAddCmd 仓库新增参数
     * @return {@link Response}
     */
    Response add(WarehouseAddCmd warehouseAddCmd);

    /**
     * 导入仓库
     *
     * @param warehouseImportCmd 仓库登记/导入功能
     * @return {@link Response}
     */
    Response warehouseImport(WarehouseImportCmd warehouseImportCmd);
}
