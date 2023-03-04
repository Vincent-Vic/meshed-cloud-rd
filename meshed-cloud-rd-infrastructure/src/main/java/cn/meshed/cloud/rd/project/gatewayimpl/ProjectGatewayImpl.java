package cn.meshed.cloud.rd.project.gatewayimpl;

import cn.meshed.cloud.rd.domain.project.Project;
import cn.meshed.cloud.rd.domain.project.gateway.ProjectGateway;
import cn.meshed.cloud.rd.project.gatewayimpl.database.dataobject.ProjectDO;
import cn.meshed.cloud.rd.project.gatewayimpl.database.mapper.ProjectMapper;
import cn.meshed.cloud.utils.AssertUtils;
import cn.meshed.cloud.utils.CopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * <h1>项目网关</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
public class ProjectGatewayImpl implements ProjectGateway {

    private final ProjectMapper projectMapper;

    /**
     * 通过key查询
     *
     * @param key key
     * @return {@link Project}
     */
    @Override
    public Project queryByKey(String key) {
        AssertUtils.isTrue(StringUtils.isNotBlank(key), "key 不能为空");
        LambdaQueryWrapper<ProjectDO> lqw = new LambdaQueryWrapper<ProjectDO>();
        lqw.eq(ProjectDO::getKey, key.toUpperCase());
        ProjectDO projectDO = projectMapper.selectOne(lqw);
        return CopyUtils.copy(projectDO, Project.class);
    }

    /**
     * 判断项目Key是否已经存在
     *
     * @param key 项目key
     * @return
     */
    @Override
    public boolean existKey(String key) {
        AssertUtils.isTrue(StringUtils.isNotBlank(key), "key 不能为空");
        LambdaQueryWrapper<ProjectDO> lqw = new LambdaQueryWrapper<ProjectDO>();
        lqw.eq(ProjectDO::getKey, key.toUpperCase());
        return projectMapper.selectCount(lqw) > 0;
    }

    /**
     * <h1>保存对象</h1>
     *
     * @param project
     * @return {@link Project}
     */
    @Override
    public Project save(Project project) {
        AssertUtils.isTrue(!existKey(project.getKey()), "项目KEY已经存在");
        ProjectDO projectDO = CopyUtils.copy(project, ProjectDO.class);
        AssertUtils.isTrue(projectMapper.insert(projectDO) > 0, "新增项目失败");
        return CopyUtils.copy(projectDO, Project.class);
    }


}
