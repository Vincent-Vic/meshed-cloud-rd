package cn.meshed.cloud.rd.deployment.web;

import cn.meshed.cloud.rd.deployment.WarehouseAdapter;
import cn.meshed.cloud.rd.deployment.command.WarehouseAddCmd;
import cn.meshed.cloud.rd.deployment.command.WarehouseImportCmd;
import cn.meshed.cloud.rd.deployment.data.WarehouseDTO;
import cn.meshed.cloud.rd.deployment.query.WarehousePageQry;
import cn.meshed.cloud.rd.domain.deployment.ability.WarehouseAbility;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <h1>仓库Web适配器</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
public class WarehouseWebAdapter implements WarehouseAdapter {

    private final WarehouseAbility warehouseAbility;

    /**
     * 仓库列表
     *
     * @param projectKey       项目key
     * @param warehousePageQry 仓库分页查询
     * @return
     */
    @Override
    public PageResponse<WarehouseDTO> list(String projectKey, @Valid WarehousePageQry warehousePageQry) {
        warehousePageQry.setProjectKey(projectKey);
        return warehouseAbility.list(warehousePageQry);
    }

    /**
     * 新增仓库
     *
     * @param warehouseAddCmd 仓库新增参数
     * @return {@link Response}
     */
    @Override
    public Response add(@Valid WarehouseAddCmd warehouseAddCmd) {
        return null;
    }

    /**
     * 注册信息/导入仓库
     *
     * @param warehouseImportCmd 仓库登记/导入功能
     * @return {@link Response}
     */
    @Override
    public Response warehouseImport(@Valid WarehouseImportCmd warehouseImportCmd) {
        return null;
    }
}
