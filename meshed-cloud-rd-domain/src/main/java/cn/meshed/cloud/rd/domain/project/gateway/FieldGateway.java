package cn.meshed.cloud.rd.domain.project.gateway;

import cn.meshed.cloud.core.IList;
import cn.meshed.cloud.rd.domain.project.Field;
import cn.meshed.cloud.rd.domain.project.constant.GroupTypeEnum;
import cn.meshed.cloud.rd.domain.project.param.FieldByListParam;

import java.util.List;

/**
 * <h1>字段网关</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public interface FieldGateway extends IList<FieldByListParam, List<Field>> {

    /**
     * 批量新增 （会删除字段中分组的字段）
     *
     * @param groupType 分组类型
     * @param fields    字段列表
     * @return
     */
    Boolean saveBatch(GroupTypeEnum groupType, List<Field> fields);
}
