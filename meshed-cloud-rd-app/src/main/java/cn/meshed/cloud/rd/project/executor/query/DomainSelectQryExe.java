package cn.meshed.cloud.rd.project.executor.query;

import cn.meshed.cloud.cqrs.QueryExecute;
import cn.meshed.cloud.rd.domain.project.gateway.DomainGateway;
import cn.meshed.cloud.utils.ResultUtils;
import com.alibaba.cola.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
public class DomainSelectQryExe implements QueryExecute<String, SingleResponse<List<String>>> {

    private final DomainGateway domainGateway;

    /**
     * @param projectKey
     * @return
     */
    @Override
    public SingleResponse<List<String>> execute(String projectKey) {
        if (StringUtils.isEmpty(projectKey)) {
            return ResultUtils.fail("项目Key不允许为空");
        }
        List<String> select = domainGateway.select(projectKey);
        return ResultUtils.of(select);
    }
}
