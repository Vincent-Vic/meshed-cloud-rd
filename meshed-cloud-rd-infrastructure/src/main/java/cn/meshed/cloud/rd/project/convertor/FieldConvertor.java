package cn.meshed.cloud.rd.project.convertor;

import cn.meshed.cloud.rd.domain.project.Field;
import cn.meshed.cloud.rd.project.gatewayimpl.database.dataobject.FieldDO;
import cn.meshed.cloud.utils.CopyUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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
    public static List<FieldDO> toEntityList(Set<Field> fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return Collections.emptyList();
        }
        List<FieldDO> fieldDOList = CopyUtils.copyListProperties(fields, FieldDO::new);
        fieldDOList.forEach(fieldDO -> {
            if (fieldDO.getNonNull() == null) {
                fieldDO.setNonNull(false);
            }
        });
        return fieldDOList;
    }

}
