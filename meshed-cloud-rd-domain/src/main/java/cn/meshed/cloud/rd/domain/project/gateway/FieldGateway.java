package cn.meshed.cloud.rd.domain.project.gateway;

import cn.meshed.cloud.rd.domain.project.Field;
import cn.meshed.cloud.rd.domain.project.constant.GroupTypeEnum;

import java.util.Set;

/**
 * <h1>字段网关</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public interface FieldGateway {

    /**
     * 批量新增 （会删除字段中分组的字段）
     *
     * @param groupType 分组类型
     * @param fields    字段列表
     * @return
     */
    Boolean saveBatch(GroupTypeEnum groupType, Set<Field> fields);

    /**
     * 查询模型字段
     *
     * @param uuid 分组ID
     * @return 字段列表
     */
    Set<Field> listByModel(String uuid);

    /**
     * 批量查询模型字段
     *
     * @param uuids 分组ID列表
     * @return 字段列表
     */
    Set<Field> listByModels(Set<String> uuids);

    /**
     * 查询服务字段
     *
     * @param uuid 分组ID
     * @return 字段列表
     */
    Set<Field> listByService(String uuid);

    /**
     * 批量查询模型字段
     *
     * @param uuids 分组ID列表
     * @return 字段列表
     */
    Set<Field> listByServices(Set<String> uuids);

}
