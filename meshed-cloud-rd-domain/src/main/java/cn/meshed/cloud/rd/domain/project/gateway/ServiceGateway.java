package cn.meshed.cloud.rd.domain.project.gateway;

import cn.meshed.cloud.core.IPageList;
import cn.meshed.cloud.core.IQuery;
import cn.meshed.cloud.core.ISave;
import cn.meshed.cloud.rd.domain.project.Service;
import cn.meshed.cloud.rd.project.query.ServicePageQry;
import com.alibaba.cola.dto.PageResponse;

import java.util.Set;

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

    /**
     * 查询项目的待发布服务详情列表
     * 注：服务发布需要重建所属这个控制器全部方法，需要将同分组的服务方法一并查询出来
     *
     * @param projectKey 项目key
     * @return 详情列表
     */
    Set<Service> waitPublishListByProject(String projectKey);
}
