package cn.meshed.cloud.rd.project.convertor;

import cn.meshed.cloud.rd.domain.project.Field;
import cn.meshed.cloud.rd.domain.project.constant.GroupTypeEnum;
import cn.meshed.cloud.rd.project.gatewayimpl.database.dataobject.FieldDO;
import cn.meshed.cloud.utils.CopyUtils;

import java.util.List;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public class FieldConvertor {

    /**
     * 转换字段实体列表
     *
     * @param fields 字段列表
     * @return
     */
    public static List<FieldDO> toEntityList(List<Field> fields, GroupTypeEnum groupType) {
        return CopyUtils.copyListProperties(fields, FieldDO::new);
    }

}
