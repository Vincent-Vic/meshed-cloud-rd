package cn.meshed.cloud.rd.deployment.config;

import cn.meshed.cloud.rd.domain.deployment.ScaffoldTemplate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "rd.warehouse")
public class ScaffoldTemplateProperties {

    private List<ScaffoldTemplate> scaffoldTemplates;

}
