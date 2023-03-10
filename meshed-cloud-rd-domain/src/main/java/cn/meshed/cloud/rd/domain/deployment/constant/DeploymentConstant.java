package cn.meshed.cloud.rd.domain.deployment.constant;

import java.util.Arrays;
import java.util.List;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public interface DeploymentConstant {

    /**
     * Response
     */
    String RESPONSE = "Response";
    /**
     * SingleResponse
     */
    String SINGLE_RESPONSE = "SingleResponse";
    /**
     * PageResponse
     */
    String PAGE_RESPONSE = "PageResponse";
    /**
     * PageQuery
     */
    String PAGE_QUERY = "PageQuery";
    /**
     * DTO
     */
    String DTO = "DTO";
    /**
     * PageParam
     */
    String PAGE_PARAM_SUFFIX = "PageParam";
    /**
     * VO
     */
    String VO_SUFFIX = "VO";
    /**
     * param
     */
    String PARAM_LOWER = "param";
    /**
     * data
     */
    String DATA_LOWER = "data";
    /**
     * 基本java数据类型
     */
    List<String> BASE_JAVA_TYPES = Arrays.asList("String", "Integer", "Long", "Float", "LocalDateTime", "LocalDate");
}
