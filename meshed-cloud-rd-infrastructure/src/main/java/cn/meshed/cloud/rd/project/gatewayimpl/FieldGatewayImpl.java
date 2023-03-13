package cn.meshed.cloud.rd.project.gatewayimpl;

import cn.meshed.cloud.rd.domain.project.Field;
import cn.meshed.cloud.rd.domain.project.constant.RelevanceTypeEnum;
import cn.meshed.cloud.rd.domain.project.gateway.FieldGateway;
import cn.meshed.cloud.rd.project.convertor.FieldConvertor;
import cn.meshed.cloud.rd.project.gatewayimpl.database.dataobject.FieldDO;
import cn.meshed.cloud.rd.project.gatewayimpl.database.mapper.FieldMapper;
import cn.meshed.cloud.utils.AssertUtils;
import cn.meshed.cloud.utils.CopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h1>字段网关实现</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
public class FieldGatewayImpl implements FieldGateway {

    private final FieldMapper fieldMapper;
    private final List<RelevanceTypeEnum> SERVICE_GROUP = Arrays.asList(RelevanceTypeEnum.REQUEST, RelevanceTypeEnum.RESPONSE);

    /**
     * 批量新增 （会删除字段中分组的字段）
     *
     * @param groupType 分组类型
     * @param fields    字段列表
     * @return
     */
    @Transactional
    @Override
    public Boolean saveBatch(RelevanceTypeEnum groupType, Set<Field> fields) {
        //校验列表不能为空
        if (CollectionUtils.isEmpty(fields)) {
            return false;
        }
        //先删除原有的字段
        Set<String> groupIds = fields.stream().map(Field::getRelevanceId).collect(Collectors.toSet());
        delByGroupId(groupIds, groupType);
        //新增字段
        List<FieldDO> fieldList = FieldConvertor.toEntityList(fields);

        //批量报错到数据库中
        return fieldMapper.insertBatch(fieldList) > 0;
    }

    /**
     * 查询模型字段
     *
     * @param uuid 分组ID
     * @return 字段列表
     */
    @Override
    public Set<Field> listByModel(String uuid) {
        AssertUtils.isTrue(StringUtils.isNotBlank(uuid), "模型ID不能为空");
        LambdaQueryWrapper<FieldDO> lqw = new LambdaQueryWrapper<>();
        lqw.eq(FieldDO::getRelevanceType, RelevanceTypeEnum.MODEL).eq(FieldDO::getRelevanceId, uuid);
        return CopyUtils.copySetProperties(fieldMapper.selectList(lqw), Field::new);
    }

    /**
     * 批量查询模型字段
     *
     * @param uuids 分组ID列表
     * @return 字段列表
     */
    @Override
    public Set<Field> listByModels(Set<String> uuids) {
        AssertUtils.isTrue(CollectionUtils.isNotEmpty(uuids), "模型ID不能为空");
        LambdaQueryWrapper<FieldDO> lqw = new LambdaQueryWrapper<>();
        lqw.eq(FieldDO::getRelevanceType, RelevanceTypeEnum.MODEL).in(FieldDO::getRelevanceId, uuids);
        List<FieldDO> fields = fieldMapper.selectList(lqw);
        return CopyUtils.copySetProperties(fields, Field::new);
    }

    /**
     * 查询服务字段
     *
     * @param uuid 分组ID
     * @return 字段列表
     */
    @Override
    public Set<Field> listByService(String uuid) {
        AssertUtils.isTrue(StringUtils.isNotBlank(uuid), "服务ID列表不能为空");
        LambdaQueryWrapper<FieldDO> lqw = new LambdaQueryWrapper<>();
        //字段目前仅含模型和服务两种大类型，为了查询方便做非运算，如新增需要修改此处业务，系分由上层业务实现
        lqw.in(FieldDO::getRelevanceType, SERVICE_GROUP).eq(FieldDO::getRelevanceId, uuid);
        return CopyUtils.copySetProperties(fieldMapper.selectList(lqw), Field::new);
    }

    /**
     * 批量查询模型字段
     *
     * @param uuids 分组ID列表
     * @return 字段列表
     */
    @Override
    public Set<Field> listByServices(Set<String> uuids) {
        AssertUtils.isTrue(CollectionUtils.isNotEmpty(uuids), "服务ID列表不能为空");
        LambdaQueryWrapper<FieldDO> lqw = new LambdaQueryWrapper<>();
        lqw.in(FieldDO::getRelevanceType, SERVICE_GROUP).in(FieldDO::getRelevanceId, uuids);
        return CopyUtils.copySetProperties(fieldMapper.selectList(lqw), Field::new);
    }

    /**
     * 通过分组id列表删除
     *
     * @param groupIds 分组ID 列表
     * @return
     */
    public boolean delByGroupId(Set<String> groupIds, RelevanceTypeEnum groupType) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return false;
        }
        LambdaQueryWrapper<FieldDO> lqw = new LambdaQueryWrapper<>();
        lqw.eq(FieldDO::getRelevanceType, groupType).in(FieldDO::getRelevanceId, groupIds);
        return fieldMapper.delete(lqw) > 0;
    }


}
