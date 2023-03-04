package cn.meshed.cloud.rd.project.executor.command;

import cn.meshed.cloud.cqrs.CommandExecute;
import cn.meshed.cloud.rd.domain.project.Field;
import cn.meshed.cloud.rd.domain.project.Project;
import cn.meshed.cloud.rd.domain.project.Service;
import cn.meshed.cloud.rd.domain.project.gateway.DomainGateway;
import cn.meshed.cloud.rd.domain.project.gateway.ProjectGateway;
import cn.meshed.cloud.rd.domain.project.gateway.ServiceGateway;
import cn.meshed.cloud.rd.project.command.ServiceCmd;
import cn.meshed.cloud.utils.AssertUtils;
import cn.meshed.cloud.utils.CopyUtils;
import cn.meshed.cloud.utils.ResultUtils;
import com.alibaba.cola.dto.Response;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

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
    @Override
    public Response execute(ServiceCmd serviceCmd) {
        Service service = CopyUtils.copy(serviceCmd, Service.class);

        if (StringUtils.isNotBlank(service.getUuid())) {
            //查询项目，判断项目是否存在，获取项目信息
            //仅在新增时处理此逻辑，项目确保不能被删除是首要前提
            String projectKey = service.getProjectKey();
            Project project = projectGateway.queryByKey(projectKey);
            AssertUtils.isTrue(project != null, "归属项目并不存在");
            //领域未添加时添加
            if (!domainGateway.existDomainKey(service.getDomainKey())) {
                return ResultUtils.fail("领域未新增");
            }
            service.initService(project);
        }
        service.setRequestBodys(CopyUtils.copyListProperties(serviceCmd.getRequestBodys(), Field::new));
        service.setRequestParams(CopyUtils.copyListProperties(serviceCmd.getRequestParams(), Field::new));
        service.setResponses(CopyUtils.copyListProperties(serviceCmd.getResponses(), Field::new));
        String uuid = serviceGateway.save(service);
        return ResultUtils.of(uuid);
    }
}
