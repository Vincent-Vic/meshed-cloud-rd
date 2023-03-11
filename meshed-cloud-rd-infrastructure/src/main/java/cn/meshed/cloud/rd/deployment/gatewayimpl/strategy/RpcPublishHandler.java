package cn.meshed.cloud.rd.deployment.gatewayimpl.strategy;

import cn.meshed.cloud.rd.codegen.ObjectMethod;
import cn.meshed.cloud.rd.codegen.ObjectModel;
import cn.meshed.cloud.rd.codegen.ObjectParameter;
import cn.meshed.cloud.rd.codegen.ObjectResponse;
import cn.meshed.cloud.rd.codegen.Rpc;
import cn.meshed.cloud.rd.domain.cli.GenerateRpc;
import cn.meshed.cloud.rd.domain.cli.gateway.CliGateway;
import cn.meshed.cloud.rd.domain.cli.utils.GenerateUtils;
import cn.meshed.cloud.rd.domain.deployment.strategy.AbstractServicePublish;
import cn.meshed.cloud.rd.domain.deployment.strategy.PublishHandler;
import cn.meshed.cloud.rd.domain.deployment.strategy.PublishType;
import cn.meshed.cloud.rd.domain.deployment.strategy.dto.ServicePublish;
import cn.meshed.cloud.rd.domain.project.Service;
import cn.meshed.cloud.rd.domain.project.gateway.ModelGateway;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.meshed.cloud.rd.domain.deployment.constant.DeploymentConstant.SRC_PATH;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Component
public class RpcPublishHandler extends AbstractServicePublish implements PublishHandler<ServicePublish> {

    public RpcPublishHandler(ModelGateway modelGateway, CliGateway cliGateway) {
        super(modelGateway, cliGateway);
    }

    /**
     * 注册类型
     *
     * @return PublishType
     */
    @Override
    public PublishType getPublishType() {
        return PublishType.RPC;
    }

    /**
     * 发布模型
     *
     * @param servicePublish 模型发布
     */
    @Override
    public void publish(ServicePublish servicePublish) {
        //用于提取操作数据中新增的模型对象
        Set<ObjectModel> newModels = new HashSet<>();
        //服务分成控制器组
        Map<String, List<Service>> classNameMap = servicePublish.getServices()
                .stream().collect(Collectors.groupingBy(Service::getClassName));
        Set<Rpc> rpcList = classNameMap.values().stream()
                .map(controls -> toRpc(servicePublish.getBasePackage(), newModels, controls))
                .collect(Collectors.toSet());

        //新增模型
        generateModelWithPush(servicePublish.getRepositoryId(), newModels,
                servicePublish.getBasePath(), servicePublish.getCommitMessage(), servicePublish.getBranch());

        //新增接口
        getCliGateway().asyncGenerateRpcWithPush(servicePublish.getRepositoryId(),
                buildGenerateRpc(servicePublish, rpcList));
    }

    @NotNull
    private GenerateRpc buildGenerateRpc(ServicePublish servicePublish, Set<Rpc> rpcList) {
        GenerateRpc generateRpc = new GenerateRpc();
        generateRpc.setRpcList(rpcList);
        generateRpc.setBasePath(SRC_PATH);
        generateRpc.setCommitMessage(servicePublish.getCommitMessage());
        generateRpc.setBranch(servicePublish.getBranch());
        return generateRpc;
    }

    private Rpc toRpc(String basePackage, Set<ObjectModel> newModels, List<Service> services) {
        //每个服务中都记录了较为冗余的数据，所以取第一个即可
        Service service = services.get(0);
        Rpc rpc = new Rpc();
        String className = service.getClassName();
        rpc.setClassName(className);
        rpc.setAuthor(getAuthor());
        rpc.setDescription(className);
        rpc.setPackageName(GenerateUtils.buildServicePackageName(basePackage, service.getDomainKey(), className));
        rpc.setExplain(className);
        rpc.setVersion(service.getVersion());

        // 方法构建
        Set<ObjectMethod> methods = services.stream().map(serviceItem -> toObjectMethod(basePackage, newModels, serviceItem))
                .collect(Collectors.toSet());
        rpc.setMethods(methods);

        //导包
        for (ObjectMethod method : methods) {
            if (CollectionUtils.isEmpty(method.getParameters())) {
                continue;
            }
            Set<String> classNames = method.getParameters().stream().map(ObjectParameter::getType).collect(Collectors.toSet());
            //方法参数导包
            rpc.addImport(classNamesImport(classNames));
            //返回参数导包
            ObjectResponse objectResponse = method.getObjectResponse();
            rpc.addImport(classNameImport(objectResponse.getDataType()));
        }
        return rpc;
    }

    protected ObjectMethod toObjectMethod(String basePackage, Set<ObjectModel> newModels, Service service) {
        ObjectMethod method = new ObjectMethod();
        Set<ObjectModel> models = assembleMethod(basePackage, method, service);
        newModels.addAll(models);
        return method;
    }

}
