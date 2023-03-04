package cn.meshed.cloud.rd.deployment.web;

import cn.meshed.cloud.rd.deployment.VersionAdapter;
import cn.meshed.cloud.rd.deployment.data.VersionDTO;
import cn.meshed.cloud.rd.deployment.query.VersionPageQry;
import cn.meshed.cloud.rd.domain.deployment.ability.VersionAbility;
import com.alibaba.cola.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
public class VersionWebAdapter implements VersionAdapter {

    private final VersionAbility versionAbility;

    /**
     * 版本列表
     *
     * @param projectKey     项目key
     * @param versionPageQry 版本分页查询
     * @return
     */
    @Override
    public PageResponse<VersionDTO> list(String projectKey, @Valid VersionPageQry versionPageQry) {
        versionPageQry.setProjectKey(projectKey);
        return versionAbility.list(versionPageQry);
    }
}
