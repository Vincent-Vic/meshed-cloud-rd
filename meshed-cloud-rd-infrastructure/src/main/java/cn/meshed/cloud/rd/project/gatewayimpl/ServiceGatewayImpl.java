package cn.meshed.cloud.rd.project.gatewayimpl;

import cn.meshed.cloud.rd.domain.project.Field;
import cn.meshed.cloud.rd.domain.project.Service;
import cn.meshed.cloud.rd.domain.project.constant.GroupTypeEnum;
import cn.meshed.cloud.rd.domain.project.gateway.FieldGateway;
import cn.meshed.cloud.rd.domain.project.gateway.ServiceGateway;
import cn.meshed.cloud.rd.domain.project.param.FieldByListParam;
import cn.meshed.cloud.rd.project.convertor.ServiceConvertor;
import cn.meshed.cloud.rd.project.gatewayimpl.database.dataobject.ServiceDO;
import cn.meshed.cloud.rd.project.gatewayimpl.database.mapper.ServiceMapper;
import cn.meshed.cloud.rd.project.query.ServicePageQry;
import cn.meshed.cloud.utils.AssertUtils;
import cn.meshed.cloud.utils.CopyUtils;
import com.alibaba.cola.dto.PageResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <h1>服务网关实现</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
public class ServiceGatewayImpl implements ServiceGateway {

    private final ServiceMapper serviceMapper;
    private final FieldGateway fieldGateway;
    private final List<GroupTypeEnum> groupTypes = new ArrayList<GroupTypeEnum>(3) {{
        add(GroupTypeEnum.REQUEST_PARAM);
        add(GroupTypeEnum.REQUEST_BODY);
        add(GroupTypeEnum.RESPONSE);
    }};


    /**
     * <h1>分页搜索</h1>
     *
     * @param param@return {@link PageResponse<Service>}
     */
    @Override
    public PageResponse<Service> searchPageList(ServicePageQry param) {
        return null;
    }

    /**
     * 查询
     *
     * @param uuid uuid
     * @return {@link Service}
     */
    @Override
    public Service query(String uuid) {
        Service service = CopyUtils.copy(queryByUuid(uuid), Service.class);
        if (service == null) {
            return null;
        }
        FieldByListParam param = FieldByListParam.builder()
                .groupId(uuid).groupTypes(groupTypes).build();
        List<Field> fields = fieldGateway.searchList(param);
        Map<GroupTypeEnum, List<Field>> fieldMap = fields.stream().collect(Collectors.groupingBy(Field::getGroupType));
        service.setRequestParams(fieldMap.get(GroupTypeEnum.REQUEST_PARAM));
        service.setRequestBodys(fieldMap.get(GroupTypeEnum.REQUEST_BODY));
        service.setResponses(fieldMap.get(GroupTypeEnum.RESPONSE));

        return service;
    }

    /**
     * <h1>保存对象</h1>
     *
     * @param service
     * @return {@link String}
     */
    @Override
    public String save(Service service) {
        if (service == null) {
            return null;
        }
        AssertUtils.isTrue(!existMethodName(
                service.getProjectKey(), service.getClassName(), service.getMethod()
        ), "生成类名重复");
        ServiceDO serviceDO = ServiceConvertor.toEntity(service, queryByUuid(service.getUuid()));
        //保存服务
        if (StringUtils.isEmpty(serviceDO.getUuid())) {
            //判断服务新增是否成功
            AssertUtils.isTrue(serviceMapper.insert(serviceDO) > 0, "服务新增失败");

        } else {
            //更新服务
            //判断服务新增是否成功
            AssertUtils.isTrue(serviceMapper.updateById(serviceDO) > 0, "服务更新失败");
        }

        //保存字段
        String uuid = serviceDO.getUuid();
        saveFields(GroupTypeEnum.REQUEST_PARAM, uuid, service.getRequestParams());
        saveFields(GroupTypeEnum.REQUEST_BODY, uuid, service.getRequestBodys());
        saveFields(GroupTypeEnum.REQUEST_PARAM, uuid, service.getResponses());

        //返回uuid
        return uuid;
    }

    @NotNull
    private void saveFields(GroupTypeEnum groupType, String uuid, List<Field> fields) {
        fields = fields.stream().peek(field -> {
            field.setGroupId(uuid);
            field.setGroupType(groupType);
        }).collect(Collectors.toList());

        AssertUtils.isTrue(fieldGateway.saveBatch(groupType, fields), "字段保存失败");
    }


    /**
     * @param uuid
     * @return
     */
    private ServiceDO queryByUuid(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            return null;
        }
        return serviceMapper.selectById(uuid);
    }

    /**
     * 判断服务处理器类中是否存在查询的方法
     *
     * @param projectKey 项目key
     * @param className  控制器
     * @param method     方法名称
     * @return
     */
    @Override
    public boolean existMethodName(String projectKey, String className, String method) {
        AssertUtils.isTrue(StringUtils.isNotBlank(projectKey), "项目key不能为空");
        AssertUtils.isTrue(StringUtils.isNotBlank(className), "类名不能为空");
        AssertUtils.isTrue(StringUtils.isNotBlank(method), "方法名称不能为空");
        LambdaQueryWrapper<ServiceDO> lqw = new LambdaQueryWrapper<>();
        //select 只返回type仅因为type字节较少，
        lqw.eq(ServiceDO::getProjectKey, projectKey)
                .eq(ServiceDO::getClassName, className)
                .eq(ServiceDO::getMethod, method);
        //项目的控制器类的方法具有唯一性
        return serviceMapper.selectCount(lqw) > 0;
    }
}
