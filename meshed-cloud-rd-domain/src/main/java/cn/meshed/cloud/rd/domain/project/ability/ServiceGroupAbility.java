package cn.meshed.cloud.rd.domain.project.ability;

import cn.meshed.cloud.core.IQuery;
import cn.meshed.cloud.core.ISave;
import cn.meshed.cloud.core.ISelect;
import cn.meshed.cloud.rd.project.command.ServiceGroupCmd;
import cn.meshed.cloud.rd.project.data.ServiceGroupDTO;
import cn.meshed.cloud.rd.project.data.ServiceGroupSelectDTO;
import cn.meshed.cloud.rd.project.query.ServiceAvailableClassQry;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;

import java.util.Set;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public interface ServiceGroupAbility extends ISelect<String, SingleResponse<Set<ServiceGroupSelectDTO>>>,
        ISave<ServiceGroupCmd, Response>, IQuery<String, SingleResponse<ServiceGroupDTO>> {

    /**
     * 检查方法是否可用（控制器中唯一性）
     *
     * @param serviceAvailableClassQry 检查参数
     * @return {@link Response}
     */
    Response availableClassName(ServiceAvailableClassQry serviceAvailableClassQry);
}
