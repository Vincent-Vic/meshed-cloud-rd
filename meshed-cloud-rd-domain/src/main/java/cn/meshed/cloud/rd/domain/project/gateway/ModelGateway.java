package cn.meshed.cloud.rd.domain.project.gateway;

import cn.meshed.cloud.core.IPageList;
import cn.meshed.cloud.core.IQuery;
import cn.meshed.cloud.core.ISave;
import cn.meshed.cloud.rd.domain.project.Model;
import cn.meshed.cloud.rd.project.query.ModelPageQry;
import com.alibaba.cola.dto.PageResponse;

/**
 * <h1>模型网关</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public interface ModelGateway extends ISave<Model, String>, IQuery<String, Model>, IPageList<ModelPageQry, PageResponse<Model>> {

    /**
     * 判断className是否在模型中是否已经存在
     *
     * @param projectKey 项目key 当项目key不存在时会判断这个系统的类名唯一值
     * @param className  类名
     * @return
     */
    boolean existClassName(String projectKey, String className);
}
