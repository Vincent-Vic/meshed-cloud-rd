package cn.meshed.cloud.rd.project.gatewayimpl.database.dataobject;

import cn.meshed.cloud.entity.BaseEntity;
import cn.meshed.cloud.rd.project.enums.ReleaseStatusEnum;
import cn.meshed.cloud.rd.project.enums.RequestModeEnum;
import cn.meshed.cloud.rd.project.enums.RequestTypeEnum;
import cn.meshed.cloud.rd.project.enums.ServiceAccessModeEnum;
import cn.meshed.cloud.rd.project.enums.ServiceModelStatusEnum;
import cn.meshed.cloud.rd.project.enums.ServiceTypeEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author by Vincent Vic
 * @since 2023-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("m_service")
public class ServiceDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String uuid;

    /**
     * 服务名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 服务方法名称
     */
    private String method;

    /**
     * 服务uri
     */
    private String uri;

    /**
     * 服务类名
     */
    private String className;

    /**
     * 服务所属控制器类名简写
     */
    private String control;

    /**
     * 服务版本号
     */
    private String version;

    /**
     * 服务负责人ID
     */
    private Long ownerId;

    /**
     * 服务所属领域key
     */
    private String domainKey;

    /**
     * 模型所属项目key
     */
    private String projectKey;

    /**
     * 服务授权码，用于注册身份安全
     */
    private String identifier;

    /**
     * 服务请求参数模式
     */
    private RequestModeEnum requestMode;

    /**
     * 服务级别 当前仅支持跟随项目性质（组件）
     */
    private ServiceAccessModeEnum accessMode;

    /**
     * 服务描述
     */
    private String description;

    /**
     * 服务类型
     */
    private ServiceTypeEnum type;

    /**
     * 服务状态
     */
    @TableField("`status`")
    private ServiceModelStatusEnum status;

    /**
     * 版本状态
     */
    private ReleaseStatusEnum releaseStatus;

    /**
     * 服务请求类型
     */
    private RequestTypeEnum requestType;


}
