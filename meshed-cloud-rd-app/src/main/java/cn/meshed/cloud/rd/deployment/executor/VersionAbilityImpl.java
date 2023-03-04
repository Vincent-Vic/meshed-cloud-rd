package cn.meshed.cloud.rd.deployment.executor;

import cn.meshed.cloud.rd.deployment.data.VersionDTO;
import cn.meshed.cloud.rd.deployment.query.VersionPageQry;
import cn.meshed.cloud.rd.domain.deployment.ability.VersionAbility;
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
public class VersionAbilityImpl implements VersionAbility {

    /**
     * 版本列表
     *
     * @param versionPageQry 版本分页查询
     * @return
     */
    @Override
    public PageResponse<VersionDTO> list(VersionPageQry versionPageQry) {
        return null;
    }
}
