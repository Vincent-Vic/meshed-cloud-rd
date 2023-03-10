package cn.meshed.cloud.rd.domain.deployment.strategy;

import cn.hutool.core.util.StrUtil;
import cn.meshed.cloud.rd.codegen.ObjectField;
import cn.meshed.cloud.rd.codegen.ObjectMethod;
import cn.meshed.cloud.rd.codegen.ObjectModel;
import cn.meshed.cloud.rd.codegen.ObjectParameter;
import cn.meshed.cloud.rd.codegen.ObjectResponse;
import cn.meshed.cloud.rd.codegen.constant.ParameterType;
import cn.meshed.cloud.rd.domain.cli.GenerateModel;
import cn.meshed.cloud.rd.domain.cli.gateway.CliGateway;
import cn.meshed.cloud.rd.domain.cli.utils.GenerateUtils;
import cn.meshed.cloud.rd.domain.project.Field;
import cn.meshed.cloud.rd.domain.project.Service;
import cn.meshed.cloud.rd.domain.project.gateway.ModelGateway;
import cn.meshed.cloud.rd.project.enums.BaseGenericsEnum;
import cn.meshed.cloud.rd.project.enums.RequestModeEnum;
import cn.meshed.cloud.rd.project.enums.ServiceTypeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.meshed.cloud.rd.domain.deployment.constant.DeploymentConstant.BASE_JAVA_TYPES;
import static cn.meshed.cloud.rd.domain.deployment.constant.DeploymentConstant.DATA_LOWER;
import static cn.meshed.cloud.rd.domain.deployment.constant.DeploymentConstant.DTO;
import static cn.meshed.cloud.rd.domain.deployment.constant.DeploymentConstant.PAGE_PARAM_SUFFIX;
import static cn.meshed.cloud.rd.domain.deployment.constant.DeploymentConstant.PAGE_QUERY;
import static cn.meshed.cloud.rd.domain.deployment.constant.DeploymentConstant.PAGE_RESPONSE;
import static cn.meshed.cloud.rd.domain.deployment.constant.DeploymentConstant.PARAM_LOWER;
import static cn.meshed.cloud.rd.domain.deployment.constant.DeploymentConstant.RESPONSE;
import static cn.meshed.cloud.rd.domain.deployment.constant.DeploymentConstant.SINGLE_RESPONSE;
import static cn.meshed.cloud.rd.domain.deployment.constant.DeploymentConstant.VO_SUFFIX;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public abstract class AbstractServicePublish {

    private final ModelGateway modelGateway;

    private final CliGateway cliGateway;


    protected boolean generateModelWithPush(String repositoryId, Set<ObjectModel> objectModels) {
        if (CollectionUtils.isEmpty(objectModels)) {
            return false;
        }
        //构建并且推送
        GenerateModel generateModel = new GenerateModel();
        generateModel.setModels(objectModels);
        cliGateway.asyncGenerateModelWithPush(repositoryId, generateModel);
        return true;
    }

    /**
     * 转换成生成所用的对象字段
     *
     * @param field 字段
     * @return ObjectField
     */
    protected ObjectField toObjectField(Field field) {
        ObjectField objectField = new ObjectField();
        objectField.setName(field.getFieldName());
        objectField.setType(field.getFieldType());
        objectField.setExplain(field.getExplain());
        objectField.setExample(field.getMock());
        if (field.getGeneric() != null && field.getGeneric() != BaseGenericsEnum.NONE) {
            objectField.setGeneric(field.getGeneric().getKey());
        }
        objectField.setAnnotationJson(field.getRule());
        objectField.setNonNull(field.getNonNull());
        return objectField;
    }

    /**
     * 字段列表导包
     *
     * @param fields 字段列表
     * @return 包列表
     */
    protected Set<String> fieldImport(Set<Field> fields) {
        Set<String> classNames = fields.stream().map(Field::getFieldType).collect(Collectors.toSet());
        return modelGateway.scanPackageNameByClassNames(classNames);
    }

    /**
     * 单个类名导包
     *
     * @param className
     * @return
     */
    protected Set<String> classNameImport(String className) {
        if (StringUtils.isBlank(className)) {
            return Collections.emptySet();
        }
        return modelGateway.scanPackageNameByClassNames(Collections.singleton(className));
    }

    /**
     * 多个个类名导包
     *
     * @param classNames
     * @return
     */
    protected Set<String> classNamesImport(Set<String> classNames) {
        if (CollectionUtils.isEmpty(classNames)) {
            return Collections.emptySet();
        }
        return modelGateway.scanPackageNameByClassNames(classNames);
    }

    protected Set<ObjectModel> assembleMethod(String basePackage, ObjectMethod method, Service service) {
        Set<ObjectModel> newModels = new HashSet<>();
        method.setName(service.getMethod());
        method.setExplain(service.getName());
        method.setDescription(service.getDescription());
        //参数处理
        //返回参数处理
        handleResponse(basePackage, method, service, newModels);

        //请求参数处理
        handleRequest(basePackage, method, service, newModels);

        return newModels;

    }

    private void handleRequest(String basePackage, ObjectMethod method, Service service, Set<ObjectModel> newModels) {
        Set<Field> requests = service.getRequests();

        if (CollectionUtils.isEmpty(requests)) {
            return;
        }

        //路径参数单独参数，但是打包时一样会将路径参数作为参数打包
        Set<Field> results = requests.stream()
                .filter(request -> request.getGeneric() == BaseGenericsEnum.PATH_VARIABLE)
                .collect(Collectors.toSet());

        //是否是分页
        if (RequestModeEnum.PAGE == service.getRequestMode()) {
            //分页需要打包成一个新的对象
            //封装返回
            ObjectModel objectModel = getObjectModel(basePackage, service, requests, PARAM_LOWER, PAGE_PARAM_SUFFIX);
            objectModel.setSuperClass(PAGE_QUERY);
            results.add(getField(service, objectModel.getClassName()));
            newModels.add(objectModel);
        } else {
            //规则1：项目仅存路径参数和单个模型对象无需封装
            //规则2：除外都打包成一个模型

            //提取非路径参数
            List<Field> fields = requests.stream()
                    .filter(request -> request.getGeneric() != BaseGenericsEnum.PATH_VARIABLE)
                    .collect(Collectors.toList());

            //除去路径参数还存在参数的情况下才有必要处理
            if (CollectionUtils.isNotEmpty(fields)) {

                //不是空至少有第一个
                Field firstField = fields.get(0);
                //字段仅有一个且不是泛型同时不是基础对象
                if (fields.size() == 1 && BaseGenericsEnum.NONE == firstField.getGeneric()
                        && !BASE_JAVA_TYPES.contains(firstField.getFieldType())) {
                    results.add(firstField);
                } else {
                    //否则全部打包成一个对象
                    ObjectModel objectModel = getObjectModel(basePackage, service, requests, PARAM_LOWER, PAGE_PARAM_SUFFIX);
                    results.add(getField(service, objectModel.getClassName()));
                    newModels.add(objectModel);
                }
            }
        }

        //处理字段
        Set<ObjectParameter> objectFields = results.stream().filter(Objects::nonNull)
                .map(result -> toObjectParameter(result, service)).collect(Collectors.toSet());

        method.setParameters(objectFields);
    }

    private ObjectParameter toObjectParameter(Field field, Service service) {
        ObjectParameter parameter = new ObjectParameter();
        parameter.setExplain(field.getExplain());
        parameter.setName(field.getFieldName());
        parameter.setType(field.getFieldType());
        //默认无
        parameter.setParameterType(ParameterType.NONE);
        if (ServiceTypeEnum.API == service.getType()) {
            if (field.getGeneric() == BaseGenericsEnum.PATH_VARIABLE) {
                parameter.setParameterType(ParameterType.PATH_VARIABLE);
            } else if (service.getRequestMode() == RequestModeEnum.JSON) {
                parameter.setParameterType(ParameterType.REQUEST_BODY);
            }
        }
        return parameter;
    }

    private Field getField(Service service, String className) {
        Field field = new Field();
        field.setExplain(service.getMethod() + "请求参数");
        field.setFieldName(StrUtil.lowerFirst(className));
        field.setFieldType(className);
        field.setGeneric(BaseGenericsEnum.NONE);
        return field;
    }

    private void handleResponse(String basePackage, ObjectMethod method, Service service, Set<ObjectModel> newModels) {
        ObjectResponse objectResponse = new ObjectResponse();
        Set<Field> responses = service.getResponses();
        //没有返回参数的时候直接采用无参数据体
        if (CollectionUtils.isEmpty(responses)) {
            objectResponse.setGeneric(RESPONSE);
        } else if (responses.size() == 1) {
            //仅仅一个的时候直接返回一个数据，不进行数据包装，直接返回
            Field field = responses.iterator().next();
            //存在业务泛型设置，分页不建议使用泛型，但是不限制业务
            if (field.getGeneric() != BaseGenericsEnum.NONE) {
                objectResponse.setSubGeneric(field.getGeneric().getKey());
            }
            //设置类型
            objectResponse.setDataType(field.getFieldType());
        } else {
            //封装返回
            ObjectModel objectModel = getObjectModel(basePackage, service, responses, DATA_LOWER, VO_SUFFIX);
            newModels.add(objectModel);
            //返回类型
            objectResponse.setDataType(objectModel.getClassName());
        }
        //如果是分页的情况下顶层泛型采用分页数据体，否则简单数据体返回
        if (service.getRequestMode() == RequestModeEnum.PAGE) {
            objectResponse.setGeneric(PAGE_RESPONSE);
        } else {
            objectResponse.setGeneric(SINGLE_RESPONSE);
        }

        method.setObjectResponse(objectResponse);
    }

    private ObjectModel getObjectModel(String basePackage, Service service, Set<Field> fields, String subPackage, String suffix) {
        //创建模型对象
        String className = GenerateUtils.buildClassName(service.getMethod(), suffix);
        ObjectModel objectModel = new ObjectModel();
        objectModel.setExplain(service.getName() + "参数");
        objectModel.setVersion(service.getVersion());
        objectModel.setSuperClass(DTO);
        objectModel.setAuthor("Vincent Vic");
        objectModel.setClassName(className);
        objectModel.setPackageName(GenerateUtils
                .buildModelPackageName(basePackage, service.getDomainKey(), subPackage, className));
        Set<ObjectField> objectFields = fields.stream().map(this::toObjectField).collect(Collectors.toSet());
        objectModel.setFields(objectFields);
        //导入包
        objectModel.addImport(fieldImport(fields));
        return objectModel;
    }
}
