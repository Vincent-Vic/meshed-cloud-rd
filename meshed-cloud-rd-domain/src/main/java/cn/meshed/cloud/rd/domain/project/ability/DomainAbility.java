package cn.meshed.cloud.rd.domain.project.ability;

import cn.meshed.cloud.rd.project.command.DomainCmd;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;

import java.util.List;

/**
 * <h1>领域能力</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public interface DomainAbility {

    /**
     * 领域统计
     *
     * @param projectKey 项目key
     * @return {@link SingleResponse < List <String>>}
     */
    SingleResponse<List<String>> select(String projectKey);

    /**
     * 领域统计
     *
     * @param domainCmd 项目key
     * @return {@link Response}
     */
    Response add(DomainCmd domainCmd);
}
