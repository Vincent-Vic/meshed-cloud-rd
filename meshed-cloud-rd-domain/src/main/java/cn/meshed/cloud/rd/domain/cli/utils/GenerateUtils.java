package cn.meshed.cloud.rd.domain.cli.utils;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

import static cn.meshed.cloud.rd.domain.project.constant.ProjectConstant.MODEL_PACKAGE_NAME_FORMAT;
import static cn.meshed.cloud.rd.domain.project.constant.ProjectConstant.SERVICE_PACKAGE_NAME_FORMAT;

/**
 * <h1>生成工具</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public class GenerateUtils {

    /**
     * 类名转URI
     *
     * @param className 类名
     * @return URI
     */
    public static String classNameToUri(String className) {
        if (StringUtils.isBlank(className)) {
            return "/";
        }
        StringBuilder builder = new StringBuilder();
        for (char c : className.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                builder.append("/");

            }
            builder.append(Character.toLowerCase(c));
        }
        return builder.toString();
    }

    /**
     * 构建类名
     *
     * @param name   方法名称
     * @param suffix 后缀
     * @return
     */
    public static String buildClassName(String name, String suffix) {
        return StrUtil.upperFirst(name) + suffix;
    }

    /**
     * 包名生成
     * 基础包名.领域名称.类名
     * 基础包名：项目的基础包名
     * 领域名称：项目内部的领域划分
     * 类名：模型实体类名
     *
     * @param basePackage 基础包名
     * @param domainKey   领域名称
     * @param className   类名
     * @return
     */
    public static String buildServicePackageName(String basePackage, String domainKey, String className) {
        return String.format(SERVICE_PACKAGE_NAME_FORMAT, basePackage.toLowerCase(), domainKey.toLowerCase(), className);
    }

    /**
     * 包名生成
     * 基础包名.领域名称.类名
     * 基础包名：项目的基础包名
     * 领域名称：项目内部的领域划分
     * 类名：模型实体类名
     *
     * @param basePackage 基础包名
     * @param domainKey   领域名称
     * @param className   类名
     * @param subPackage  子包名
     * @return
     */
    public static String buildModelPackageName(String basePackage, String domainKey, String subPackage, String className) {
        return String.format(MODEL_PACKAGE_NAME_FORMAT, basePackage, domainKey, subPackage, className).toLowerCase();
    }
}
