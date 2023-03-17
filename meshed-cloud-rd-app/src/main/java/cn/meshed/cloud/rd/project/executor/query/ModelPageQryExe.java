package cn.meshed.cloud.rd.project.executor.query;

import cn.meshed.cloud.cqrs.QueryExecute;
import cn.meshed.cloud.rd.domain.project.Model;
import cn.meshed.cloud.rd.domain.project.gateway.ModelGateway;
import cn.meshed.cloud.rd.project.data.ModelDTO;
import cn.meshed.cloud.rd.project.query.ModelPageQry;
import cn.meshed.cloud.utils.ResultUtils;
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
public class ModelPageQryExe implements QueryExecute<ModelPageQry, PageResponse<ModelDTO>> {

    private final ModelGateway modelGateway;

    /**
     * @param modelPageQry
     * @return
     */
    @Override
    public PageResponse<ModelDTO> execute(ModelPageQry modelPageQry) {
        PageResponse<Model> pageResponse = modelGateway.searchPageList(modelPageQry);
        return ResultUtils.copyPage(pageResponse, ModelDTO::new);
    }
}
