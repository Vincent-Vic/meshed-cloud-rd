package cn.meshed.cloud.rd.deployment.executor.query;

import cn.meshed.cloud.cqrs.QueryExecute;
import cn.meshed.cloud.rd.deployment.data.VersionDTO;
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
public class VersionPageQryExe implements QueryExecute<VersionPageQryExe, PageResponse<VersionDTO>> {
    /**
     * @param versionPageQryExe
     * @return
     */
    @Override
    public PageResponse<VersionDTO> execute(VersionPageQryExe versionPageQryExe) {
        return null;
    }
}
