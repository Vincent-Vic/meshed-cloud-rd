package cn.meshed.cloud.rd.project.gatewayimpl;

import cn.meshed.cloud.rd.domain.project.Field;
import cn.meshed.cloud.rd.domain.project.Service;
import cn.meshed.cloud.rd.domain.project.constant.GroupTypeEnum;
import cn.meshed.cloud.rd.domain.project.gateway.FieldGateway;
import cn.meshed.cloud.rd.domain.project.gateway.ServiceGateway;
import cn.meshed.cloud.rd.project.convertor.ServiceConvertor;
import cn.meshed.cloud.rd.project.enums.ReleaseStatusEnum;
import cn.meshed.cloud.rd.project.gatewayimpl.database.dataobject.ServiceDO;
import cn.meshed.cloud.rd.project.gatewayimpl.database.mapper.ServiceMapper;
import cn.meshed.cloud.rd.project.query.ServicePageQry;
import cn.meshed.cloud.utils.AssertUtils;
import cn.meshed.cloud.utils.CopyUtils;
import com.alibaba.cola.dto.PageResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
    private final List<GroupTypeEnum> groupTypes = new ArrayList<GroupTypeEnum>(2) {{
        add(GroupTypeEnum.REQUEST);
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
        Set<Field> fields = fieldGateway.listByService(uuid);
        Map<GroupTypeEnum, Set<Field>> fieldMap = fields.stream()
                .collect(Collectors.groupingBy(Field::getGroupType, Collectors.toSet()));
        service.setRequests(fieldMap.get(GroupTypeEnum.REQUEST));
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
                service.getProjectKey(), service.getControl(), service.getMethod()
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
        saveFields(GroupTypeEnum.REQUEST, uuid, service.getRequests());
        saveFields(GroupTypeEnum.REQUEST, uuid, service.getResponses());
        //返回uuid
        return uuid;
    }

    @NotNull
    private void saveFields(GroupTypeEnum groupType, String uuid, Set<Field> fields) {
        fields = fields.stream().peek(field -> {
            field.setGroupId(uuid);
            field.setGroupType(groupType);
        }).collect(Collectors.toSet());

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
        lqw.eq(ServiceDO::getProjectKey, projectKey)
                .eq(ServiceDO::getClassName, className)
                .eq(ServiceDO::getMethod, method);
        //项目的控制器类的方法具有唯一性
        return serviceMapper.selectCount(lqw) > 0;
    }

    /**
     * 查询项目的待发布服务详情列表
     *
     * @param projectKey 项目key
     * @return 详情列表
     */
    @Override
    public Set<Service> waitPublishListByProject(String projectKey) {
        AssertUtils.isTrue(StringUtils.isNotBlank(projectKey), "项目key不能为空");
        //查询服务基础信息
        Set<Service> services = getBaseServiceInfo(projectKey);
        if (CollectionUtils.isEmpty(services)) {
            return services;
        }
        Set<String> uuids = services.stream().filter(Objects::nonNull).map(Service::getUuid).collect(Collectors.toSet());

        Set<Field> fields = fieldGateway.listByServices(uuids);
        if (CollectionUtils.isNotEmpty(fields)) {
            Map<String, List<Field>> servicesFieldMap = fields.stream().collect(Collectors.groupingBy(Field::getGroupId));
            services.forEach(service -> {
                List<Field> fieldList = servicesFieldMap.get(service.getUuid());
                if (CollectionUtils.isNotEmpty(fieldList)) {
                    Map<GroupTypeEnum, Set<Field>> fieldMap = fieldList.stream()
                            .collect(Collectors.groupingBy(Field::getGroupType, Collectors.toSet()));
                    service.setRequests(fieldMap.get(GroupTypeEnum.REQUEST));
                    service.setResponses(fieldMap.get(GroupTypeEnum.RESPONSE));
                }

            });
        }

        //封装详细列表数据
        return assembleServiceDetailsList(services);
    }

    private Set<Service> getBaseServiceInfo(String projectKey) {
        LambdaQueryWrapper<ServiceDO> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ServiceDO::getProjectKey, projectKey)
                .eq(ServiceDO::getReleaseStatus, ReleaseStatusEnum.PROCESSING);
        Set<Service> services = CopyUtils.copySetProperties(serviceMapper.selectList(lqw), Service::new);
        if (CollectionUtils.isEmpty(services)) {
            return services;
        }
        //已发布的同控制器也需要被查询
        //提取出待发布的所有控制类名称（去重）
        //Set 避免正式版本和修改版本重复，重复生成导致bug
        Set<String> classNameSet = services.stream().map(Service::getClassName).collect(Collectors.toSet());
        lqw.clear();
        //仅需要快照和正式版本的服务，待发布数据已经获取
        List<ReleaseStatusEnum> statusEnums = new ArrayList<>();
        statusEnums.add(ReleaseStatusEnum.SNAPSHOT);
        statusEnums.add(ReleaseStatusEnum.RELEASE);
        lqw.eq(ServiceDO::getProjectKey, projectKey)
                .in(ServiceDO::getClassName, classNameSet)
                .in(ServiceDO::getReleaseStatus, statusEnums);
        Set<Service> oldServices = CopyUtils.copySetProperties(serviceMapper.selectList(lqw), Service::new);
        //需要重建这个控制器，所有相关的接口服务都需要获取
        services.addAll(oldServices);

        return services;
    }

    private Set<Service> assembleServiceDetailsList(Set<Service> services) {
        if (CollectionUtils.isEmpty(services)) {
            return services;
        }
        Set<String> uuids = services.stream().map(Service::getUuid).collect(Collectors.toSet());
        //获取服务相关全部字段，含返回，请求，body分组的内容
        Set<Field> fields = fieldGateway.listByServices(uuids);
        //没有任何字段也无需处理
        if (CollectionUtils.isEmpty(fields)) {
            return services;
        }
        Map<GroupTypeEnum, List<Field>> listMap = fields.stream().collect(Collectors.groupingBy(Field::getGroupType));

        //解析出相应的字段类型分组
        Map<String, Set<Field>> paramFieldMap = getFieldMap(listMap, GroupTypeEnum.REQUEST);
        Map<String, Set<Field>> responseFieldMap = getFieldMap(listMap, GroupTypeEnum.RESPONSE);

        return services.stream().peek(service -> {
            String uuid = service.getUuid();
            service.setRequests(paramFieldMap.get(uuid));
            service.setResponses(responseFieldMap.get(uuid));
        }).collect(Collectors.toSet());
    }

    /**
     * 提取服务对应的字段类型map
     *
     * @param listMap
     * @param groupType
     * @return
     */
    private Map<String, Set<Field>> getFieldMap(Map<GroupTypeEnum, List<Field>> listMap, GroupTypeEnum groupType) {
        if (listMap.isEmpty()) {
            return new HashMap<>();
        }
        List<Field> fields = listMap.get(groupType);
        if (CollectionUtils.isEmpty(fields)) {
            return new HashMap<>();
        }
        return fields.stream().collect(Collectors.groupingBy(Field::getGroupId, Collectors.toSet()));
    }

}
