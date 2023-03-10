package cn.meshed.cloud.rd.domain.project;

import cn.hutool.core.util.StrUtil;
import cn.meshed.cloud.context.SecurityContext;
import cn.meshed.cloud.rd.project.enums.ReleaseStatusEnum;
import cn.meshed.cloud.rd.project.enums.RequestModeEnum;
import cn.meshed.cloud.rd.project.enums.RequestTypeEnum;
import cn.meshed.cloud.rd.project.enums.ServiceAccessModeEnum;
import cn.meshed.cloud.rd.project.enums.ServiceModelStatusEnum;
import cn.meshed.cloud.rd.project.enums.ServiceTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import static cn.meshed.cloud.rd.domain.project.constant.ProjectConstant.INIT_VERSION;

/**
 * <h1>服务</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 服务名称
     */
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
     * 服务访问权限
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
    private ServiceModelStatusEnum status;

    /**
     * 版本状态
     */
    private ReleaseStatusEnum releaseStatus;

    /**
     * 服务请求类型
     */
    private RequestTypeEnum requestType;

    /**
     * 请求参数传输列表
     */
    private Set<Field> requests;
    /**
     * 响应字段列表
     */
    private Set<Field> responses;

    public void initService() {
        this.className = StrUtil.upperFirst(this.control) + this.type.getKey();
        this.releaseStatus = ReleaseStatusEnum.EDIT;
        this.status = ServiceModelStatusEnum.DEV;
        this.version = INIT_VERSION;
        this.ownerId = SecurityContext.getOperatorUserId();
        if (this.type == ServiceTypeEnum.RPC) {
            this.uri = this.className + "#" + this.method;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Service)) {
            return false;
        }
        Service service = (Service) o;
        return getMethod().equals(service.getMethod()) && getClassName().equals(service.getClassName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethod(), getClassName());
    }
}
