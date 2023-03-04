package cn.meshed.cloud.rd.domain.project.gateway;

import cn.meshed.cloud.core.IPageList;
import cn.meshed.cloud.core.IQuery;
import cn.meshed.cloud.core.ISave;
import cn.meshed.cloud.rd.domain.project.Service;
import cn.meshed.cloud.rd.project.query.ServicePageQry;
import com.alibaba.cola.dto.PageResponse;

/**
 * <h1>服务网关</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public interface ServiceGateway extends ISave<Service, String>, IQuery<String, Service>,
        IPageList<ServicePageQry, PageResponse<Service>> {

    /**
     * 判断服务处理器类中是否存在查询的方法
     *
     * @param projectKey 项目key
     * @param className  控制器
     * @param method     方法名称
     * @return
     */
    boolean existMethodName(String projectKey, String className, String method);
}
