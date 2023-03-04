package cn.meshed.cloud.rd.domain.project.param;

import cn.meshed.cloud.rd.domain.project.constant.GroupTypeEnum;
import com.alibaba.cola.dto.Query;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * <h1>内部列表查询参数</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
@Builder
public class FieldByListParam extends Query {

    /**
     * 类型列表
     */
    private List<GroupTypeEnum> groupTypes;

    /**
     * 分组ID
     */
    private String groupId;
}
