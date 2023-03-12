package cn.meshed.cloud.rd.project.gatewayimpl.database.dao.impl;

import cn.meshed.cloud.rd.project.gatewayimpl.database.dao.ServiceGroupDao;
import cn.meshed.cloud.rd.project.gatewayimpl.database.dataobject.ServiceGroupDO;
import cn.meshed.cloud.rd.project.gatewayimpl.database.mapper.ServiceGroupMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author by Vincent Vic
 * @since 2023-03-11
 */
@Service
public class ServiceGroupDaoImpl extends ServiceImpl<ServiceGroupMapper, ServiceGroupDO> implements ServiceGroupDao {

}
