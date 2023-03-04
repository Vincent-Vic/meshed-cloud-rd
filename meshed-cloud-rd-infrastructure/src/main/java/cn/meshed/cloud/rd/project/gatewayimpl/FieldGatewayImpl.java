package cn.meshed.cloud.rd.project.gatewayimpl;

import cn.meshed.cloud.rd.domain.project.Field;
import cn.meshed.cloud.rd.domain.project.constant.GroupTypeEnum;
import cn.meshed.cloud.rd.domain.project.gateway.FieldGateway;
import cn.meshed.cloud.rd.domain.project.param.FieldByListParam;
import cn.meshed.cloud.rd.project.convertor.FieldConvertor;
import cn.meshed.cloud.rd.project.gatewayimpl.database.dataobject.FieldDO;
import cn.meshed.cloud.rd.project.gatewayimpl.database.mapper.FieldMapper;
import cn.meshed.cloud.utils.CopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
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


    /**
     * <h1>搜索</h1>
     *
     * @param param
     * @return {@link List<Field>}
     */
    @Override
    public List<Field> searchList(FieldByListParam param) {
        if (param == null || CollectionUtils.isEmpty(param.getGroupTypes())
                || StringUtils.isEmpty(param.getGroupId())) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<FieldDO> lqw = new LambdaQueryWrapper<>();
        lqw.in(FieldDO::getGroupType, param.getGroupTypes()).eq(FieldDO::getGroupId, param.getGroupId());
        return CopyUtils.copyListProperties(fieldMapper.selectList(lqw), Field::new);
    }

    /**
     * 批量新增 （会删除字段中分组的字段）
     *
     * @param groupType 分组类型
     * @param fields    字段列表
     * @return
     */
    @Transactional
    public Boolean saveBatch(GroupTypeEnum groupType, List<Field> fields) {
        //校验列表不能为空
        if (CollectionUtils.isEmpty(fields)) {
            return false;
        }
        //先删除原有的字段
        List<String> groupIds = fields.stream().map(Field::getGroupId).collect(Collectors.toList());
        if (delByGroupId(groupIds, groupType)) {
            return false;
        }
        //新增字段
        List<FieldDO> fieldList = FieldConvertor.toEntityList(fields, groupType);

        //批量报错到数据库中
        return fieldMapper.insertBatch(fieldList) > 0;
    }


    /**
     * 通过分组id列表删除
     *
     * @param groupIds 分组ID 列表
     * @return
     */
    private boolean delByGroupId(List<String> groupIds, GroupTypeEnum groupType) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return false;
        }
        LambdaQueryWrapper<FieldDO> lqw = new LambdaQueryWrapper<>();
        lqw.eq(FieldDO::getGroupType, groupType).in(FieldDO::getGroupId, groupIds);
        return fieldMapper.delete(lqw) > 0;
    }


}
