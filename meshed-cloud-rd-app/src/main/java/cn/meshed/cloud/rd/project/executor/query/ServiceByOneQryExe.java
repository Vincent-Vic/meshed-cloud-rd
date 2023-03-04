package cn.meshed.cloud.rd.project.executor.query;

import cn.meshed.cloud.cqrs.QueryExecute;
import cn.meshed.cloud.rd.project.data.ServiceDetailDTO;
import cn.meshed.cloud.rd.project.query.ServiceByOneQry;
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
public class ServiceByOneQryExe implements QueryExecute<ServiceByOneQry, SingleResponse<ServiceDetailDTO>> {
    /**
     * @param serviceByOneQry
     * @return
     */
    @Override
    public SingleResponse<ServiceDetailDTO> execute(ServiceByOneQry serviceByOneQry) {
        return null;
    }
}
