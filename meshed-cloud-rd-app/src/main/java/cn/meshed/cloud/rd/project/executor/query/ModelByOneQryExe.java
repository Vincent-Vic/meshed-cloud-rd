package cn.meshed.cloud.rd.project.executor.query;

import cn.meshed.cloud.cqrs.QueryExecute;
import cn.meshed.cloud.rd.project.data.ModelDetailDTO;
import cn.meshed.cloud.rd.project.query.ModelByOneQry;
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
public class ModelByOneQryExe implements QueryExecute<ModelByOneQry, SingleResponse<ModelDetailDTO>> {
    /**
     * @param modelByOneQry
     * @return
     */
    @Override
    public SingleResponse<ModelDetailDTO> execute(ModelByOneQry modelByOneQry) {
        return null;
    }
}
