package cn.meshed.cloud.rd.domain.project;

import cn.hutool.core.util.StrUtil;
import cn.meshed.cloud.rd.project.enums.ReleaseStatusEnum;
import cn.meshed.cloud.rd.project.enums.RequestTypeEnum;
import cn.meshed.cloud.rd.project.enums.ServiceAccessModeEnum;
import cn.meshed.cloud.rd.project.enums.ServiceBehaviorEnum;
import cn.meshed.cloud.rd.project.enums.ServiceModelStatusEnum;
import cn.meshed.cloud.rd.project.enums.ServiceTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

import static cn.meshed.cloud.rd.domain.project.constant.ProjectConstant.INIT_VERSION;
import static cn.meshed.cloud.rd.domain.project.constant.ProjectConstant.SERVICE_PACKAGE_NAME_FORMAT;

/**
 * <h1>服务</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
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
     * 模型包名
     */
    private String packageName;

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
     * 服务行为能力
     */
    private ServiceBehaviorEnum behavior;

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
    private List<Field> requestParams;
    /**
     * 请求数据传输列表
     */
    private List<Field> requestBodys;
    /**
     * 相应字段列表
     */
    private List<Field> responses;

    public void initService(Project project) {
        this.className = StrUtil.upperFirst(this.control) + this.type.getKey();
        this.releaseStatus = ReleaseStatusEnum.EDIT;
        this.packageName = buildPackageName(project.getBasePackage());
        this.status = ServiceModelStatusEnum.DEV;
        this.version = INIT_VERSION;
        if (this.type == ServiceTypeEnum.RPC) {
            this.uri = this.className + "#" + this.method;
        }
    }

    /**
     * 包名生成
     * 基础包名.领域名称.子包名.类名
     * 基础包名：项目的基础包名
     * 领域名称：项目内部的领域划分
     * 子包名： 具体包名
     * 类名：模型实体类名
     *
     * @param basePackage
     * @return
     */
    private String buildPackageName(String basePackage) {
        return String.format(SERVICE_PACKAGE_NAME_FORMAT, basePackage, domainKey, this.className).toLowerCase();
    }

}
