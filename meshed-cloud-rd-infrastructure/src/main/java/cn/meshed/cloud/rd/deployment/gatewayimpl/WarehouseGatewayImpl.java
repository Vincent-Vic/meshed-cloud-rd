package cn.meshed.cloud.rd.deployment.gatewayimpl;

import cn.meshed.cloud.rd.deployment.gatewayimpl.database.dataobject.WarehouseDO;
import cn.meshed.cloud.rd.deployment.gatewayimpl.database.mapper.WarehouseMapper;
import cn.meshed.cloud.rd.deployment.query.WarehousePageQry;
import cn.meshed.cloud.rd.domain.deployment.Warehouse;
import cn.meshed.cloud.rd.domain.deployment.gateway.WarehouseGateway;
import cn.meshed.cloud.utils.AssertUtils;
import cn.meshed.cloud.utils.CopyUtils;
import com.alibaba.cola.dto.PageResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
public class WarehouseGatewayImpl implements WarehouseGateway {

    private final WarehouseMapper warehouseMapper;

    /**
     * <h1>分页搜索</h1>
     *
     * @param param@return {@link PageResponse<Warehouse>}
     */
    @Override
    public PageResponse<Warehouse> searchPageList(WarehousePageQry param) {
        return null;
    }

    /**
     * <h1>保存对象</h1>
     *
     * @param warehouse 仓库
     * @return {@link Warehouse}
     */
    @Override
    public Warehouse save(Warehouse warehouse) {
        WarehouseDO warehouseDO = CopyUtils.copy(warehouse, WarehouseDO.class);
        AssertUtils.isTrue(warehouseMapper.insert(warehouseDO) > 0, "新增失败");
        return CopyUtils.copy(warehouseDO, Warehouse.class);
    }

    /**
     * 判断仓库名称是否唯一 （全局唯一）
     *
     * @param warehouseName 仓库名称
     * @return
     */
    @Override
    public boolean existWarehouseName(String warehouseName) {
        LambdaQueryWrapper<WarehouseDO> lqw = new LambdaQueryWrapper<>();
        lqw.eq(WarehouseDO::getRepoName, warehouseName);
        return warehouseMapper.selectCount(lqw) > 0;
    }
}
