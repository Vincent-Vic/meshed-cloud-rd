package cn.meshed.cloud.rd.domain.deployment.ability;

import cn.meshed.cloud.rd.deployment.data.VersionDTO;
import cn.meshed.cloud.rd.deployment.query.VersionPageQry;
import com.alibaba.cola.dto.PageResponse;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public interface VersionAbility {

    /**
     * 版本列表
     *
     * @param versionPageQry 版本分页查询
     * @return
     */
    PageResponse<VersionDTO> list(VersionPageQry versionPageQry);
}
