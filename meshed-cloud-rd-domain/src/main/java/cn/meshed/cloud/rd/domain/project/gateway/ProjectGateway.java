package cn.meshed.cloud.rd.domain.project.gateway;

import cn.meshed.cloud.core.ISave;
import cn.meshed.cloud.rd.domain.project.Project;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public interface ProjectGateway extends ISave<Project, Project> {

    /**
     * 通过key查询
     *
     * @param key key
     * @return {@link Project}
     */
    Project queryByKey(String key);

    /**
     * 判断项目Key是否已经存在
     *
     * @param key 项目key
     * @return
     */
    boolean existKey(String key);
}
