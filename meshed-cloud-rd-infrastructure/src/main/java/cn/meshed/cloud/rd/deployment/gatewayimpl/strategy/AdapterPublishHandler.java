package cn.meshed.cloud.rd.deployment.gatewayimpl.strategy;

import cn.meshed.cloud.rd.codegen.Adapter;
import cn.meshed.cloud.rd.codegen.AdapterMethod;
import cn.meshed.cloud.rd.codegen.ObjectModel;
import cn.meshed.cloud.rd.codegen.ObjectParameter;
import cn.meshed.cloud.rd.codegen.ObjectResponse;
import cn.meshed.cloud.rd.codegen.constant.RequestType;
import cn.meshed.cloud.rd.domain.cli.GenerateAdapter;
import cn.meshed.cloud.rd.domain.cli.gateway.CliGateway;
import cn.meshed.cloud.rd.domain.cli.utils.GenerateUtils;
import cn.meshed.cloud.rd.domain.deployment.strategy.AbstractServicePublish;
import cn.meshed.cloud.rd.domain.deployment.strategy.PublishHandler;
import cn.meshed.cloud.rd.domain.deployment.strategy.PublishType;
import cn.meshed.cloud.rd.domain.deployment.strategy.dto.ServicePublish;
import cn.meshed.cloud.rd.domain.project.Service;
import cn.meshed.cloud.rd.domain.project.gateway.ModelGateway;
import cn.meshed.cloud.rd.project.enums.RequestTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class AdapterPublishHandler extends AbstractServicePublish implements PublishHandler<ServicePublish> {


    public AdapterPublishHandler(ModelGateway modelGateway, CliGateway cliGateway) {
        super(modelGateway, cliGateway);
    }

    /**
     * 注册类型
     *
     * @return PublishType
     */
    @Override
    public PublishType getPublishType() {
        return PublishType.ADAPTER;
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
        Set<Adapter> adapters = classNameMap.values().stream()
                .map(controls -> toAdapter(servicePublish.getBasePackage(), newModels, controls))
                .collect(Collectors.toSet());

        //新增模型
        generateModelWithPush(servicePublish.getRepositoryId(), newModels,
                servicePublish.getBasePath(), servicePublish.getCommitMessage(), servicePublish.getBranch());

        //新增接口
        getCliGateway().asyncGenerateAdapterWithPush(servicePublish.getRepositoryId(),
                buildGenerateAdapter(servicePublish, adapters));
    }

    @NotNull
    private GenerateAdapter buildGenerateAdapter(ServicePublish servicePublish, Set<Adapter> adapters) {
        GenerateAdapter generateAdapter = new GenerateAdapter();
        generateAdapter.setAdapters(adapters);
        generateAdapter.setBasePath(SRC_PATH);
        generateAdapter.setCommitMessage(servicePublish.getCommitMessage());
        generateAdapter.setBranch(servicePublish.getBranch());
        return generateAdapter;
    }

    private Adapter toAdapter(String basePackage, Set<ObjectModel> newModels, List<Service> services) {
        //每个服务中都记录了较为冗余的数据，所以取第一个即可
        Service service = services.get(0);
        Adapter adapter = new Adapter();
        String className = service.getClassName();
        adapter.setClassName(className);
        adapter.setUri(GenerateUtils.classNameToUri(className));
        adapter.setAuthor(getAuthor());
        adapter.setDescription(className);
        adapter.setPackageName(GenerateUtils.buildServicePackageName(basePackage, service.getDomainKey(), className));
        adapter.setExplain(className);
        adapter.setVersion(service.getVersion());


        // 方法构建
        Set<AdapterMethod> methods = services.stream().map(serviceItem -> toAdapterMethod(basePackage, newModels, serviceItem))
                .collect(Collectors.toSet());
        adapter.setMethods(methods);

        //导包
        //为登记到系统的类单独导包
        if (CollectionUtils.isNotEmpty(newModels)) {
            Set<String> newPackageNames = newModels.stream()
                    .filter(Objects::nonNull).map(ObjectModel::getPackageName).collect(Collectors.toSet());
            adapter.addImport(newPackageNames);
        }

        //方法参数导包
        for (AdapterMethod method : methods) {
            if (CollectionUtils.isEmpty(method.getParameters())) {
                continue;
            }
            Set<String> classNames = method.getParameters().stream().map(ObjectParameter::getType).collect(Collectors.toSet());
            //方法参数导包
            adapter.addImport(classNamesImport(classNames));
            //返回参数导包
            ObjectResponse objectResponse = method.getObjectResponse();
            adapter.addImport(classNameImport(objectResponse.getDataType()));
        }
        return adapter;
    }

    protected AdapterMethod toAdapterMethod(String basePackage, Set<ObjectModel> newModels, Service service) {
        AdapterMethod method = new AdapterMethod();
        Set<ObjectModel> models = assembleMethod(basePackage, method, service);
        newModels.addAll(models);
        method.setRequestType(convertRequestType(service.getRequestType()));
        method.setUri(service.getUri());
        return method;
    }

    private RequestType convertRequestType(RequestTypeEnum requestTypeEnum) {
        return RequestType.valueOf(requestTypeEnum.name());
    }

}
