package cn.meshed.cloud.rd.project.executor.query;

import cn.meshed.cloud.cqrs.QueryExecute;
import cn.meshed.cloud.rd.project.data.ServiceDTO;
import cn.meshed.cloud.rd.project.query.ServicePageQry;
import com.alibaba.cola.dto.PageResponse;
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
public class ServicePageQryExe implements QueryExecute<ServicePageQry, PageResponse<ServiceDTO>> {
    /**
     * @param servicePageQry
     * @return
     */
    @Override
    public PageResponse<ServiceDTO> execute(ServicePageQry servicePageQry) {
        return null;
    }
}
