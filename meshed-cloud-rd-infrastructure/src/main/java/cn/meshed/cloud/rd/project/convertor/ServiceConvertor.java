package cn.meshed.cloud.rd.project.convertor;


import cn.meshed.cloud.rd.domain.project.Service;
import cn.meshed.cloud.rd.project.gatewayimpl.database.dataobject.ServiceDO;
import cn.meshed.cloud.utils.CopyUtils;

import java.io.Serializable;

/**
 * <h1>模型转换</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public class ServiceConvertor implements Serializable {

    public static ServiceDO toEntity(Service service, ServiceDO serviceDO) {
        if (serviceDO == null) {
            return CopyUtils.copy(service, ServiceDO.class);
        }
        serviceDO.setName(serviceDO.getName());
        serviceDO.setDescription(serviceDO.getDescription());
        return serviceDO;
    }

}
