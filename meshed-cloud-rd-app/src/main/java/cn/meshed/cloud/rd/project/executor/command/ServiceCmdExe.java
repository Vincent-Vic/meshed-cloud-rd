package cn.meshed.cloud.rd.project.executor.command;

import cn.meshed.cloud.cqrs.CommandExecute;
import cn.meshed.cloud.rd.domain.project.Field;
import cn.meshed.cloud.rd.domain.project.Project;
import cn.meshed.cloud.rd.domain.project.Service;
import cn.meshed.cloud.rd.domain.project.gateway.DomainGateway;
import cn.meshed.cloud.rd.domain.project.gateway.ProjectGateway;
import cn.meshed.cloud.rd.domain.project.gateway.ServiceGateway;
import cn.meshed.cloud.rd.project.command.ServiceCmd;
import cn.meshed.cloud.rd.project.enums.ServiceAccessModeEnum;
import cn.meshed.cloud.utils.AssertUtils;
import cn.meshed.cloud.utils.CopyUtils;
import cn.meshed.cloud.utils.ResultUtils;
import com.alibaba.cola.dto.Response;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>服务存储处理器</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
public class ServiceCmdExe implements CommandExecute<ServiceCmd, Response> {

    private final ServiceGateway serviceGateway;
    private final ProjectGateway projectGateway;
    private final DomainGateway domainGateway;

    /**
     * @param serviceCmd
     * @return
     */
    @Transactional
    @Override
    public Response execute(ServiceCmd serviceCmd) {
        if (ServiceAccessModeEnum.AUTHORIZE == serviceCmd.getAccessMode()) {
            AssertUtils.isTrue(StringUtils.isNotBlank(serviceCmd.getIdentifier()), "授权模式下，授权码不能为空");
        }
        Service service = CopyUtils.copy(serviceCmd, Service.class);
        service.setDomainKey(serviceCmd.getProjectKey());

        if (StringUtils.isBlank(service.getUuid())) {
            //查询项目，判断项目是否存在，获取项目信息
            //仅在新增时处理此逻辑，项目确保不能被删除是首要前提
            String projectKey = service.getProjectKey();
            Project project = projectGateway.queryByKey(projectKey);
            AssertUtils.isTrue(project != null, "归属项目并不存在");
            //领域未添加时添加
            if (!domainGateway.existDomainKey(service.getDomainKey())) {
                return ResultUtils.fail("领域未新增");
            }
            service.initService();
        }
        //查询是否已经存在


        //页面字段填充
        service.setRequests(CopyUtils.copySetProperties(serviceCmd.getRequests(), Field::new));
        service.setResponses(CopyUtils.copySetProperties(serviceCmd.getResponses(), Field::new));

        String uuid = serviceGateway.save(service);
        return ResultUtils.of(uuid);
    }

//    @NotNull
//    private ModelTypeEnum toModelType(ServiceBehaviorEnum serviceBehavior) {
//        if (ServiceBehaviorEnum.COMMAND == serviceBehavior){
//            return ModelTypeEnum.COMMAND;
//        } else if (ServiceBehaviorEnum.PAGE == serviceBehavior){
//            return ModelTypeEnum.QUERY;
//        }
//        return ModelTypeEnum.QUERY;
//    }
}
