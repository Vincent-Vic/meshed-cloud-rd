package cn.meshed.cloud.rd.deployment.executor.query;


import cn.meshed.cloud.cqrs.QueryExecute;
import cn.meshed.cloud.rd.deployment.data.VersionReleaseCountDTO;
import cn.meshed.cloud.rd.deployment.query.VersionReleaseCountQry;
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
public class VersionReleaseCountQryExe implements QueryExecute<VersionReleaseCountQry, SingleResponse<VersionReleaseCountDTO>> {
    /**
     * @param versionReleaseCountQry
     * @return
     */
    @Override
    public SingleResponse<VersionReleaseCountDTO> execute(VersionReleaseCountQry versionReleaseCountQry) {
        return null;
    }
}
