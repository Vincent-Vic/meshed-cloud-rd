package cn.meshed.cloud.rd.deployment.executor.query;

import cn.meshed.cloud.cqrs.QueryExecute;
import cn.meshed.cloud.rd.deployment.data.WarehouseDTO;
import cn.meshed.cloud.rd.deployment.query.WarehousePageQry;
import com.alibaba.cola.dto.SingleResponse;
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
public class WarehousePageQryExe implements QueryExecute<WarehousePageQry, SingleResponse<WarehouseDTO>> {
    /**
     * @param warehousePageQry
     * @return
     */
    @Override
    public SingleResponse<WarehouseDTO> execute(WarehousePageQry warehousePageQry) {
        return null;
    }
}
