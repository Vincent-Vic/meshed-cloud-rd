package cn.meshed.cloud.rd.deployment.executor.query;

import cn.meshed.cloud.cqrs.QueryExecute;
import cn.meshed.cloud.rd.deployment.config.ScaffoldTemplateProperties;
import cn.meshed.cloud.rd.domain.deployment.ScaffoldTemplate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
public class ScaffoldTemplateQryExe implements QueryExecute<String, List<ScaffoldTemplate>> {

    private final ScaffoldTemplateProperties scaffoldTemplateProperties;

    /**
     * @param templateKey
     * @return
     */
    @Override
    public List<ScaffoldTemplate> execute(String templateKey) {
        if (StringUtils.isEmpty(templateKey)) {
            return null;
        }
        if (CollectionUtils.isNotEmpty(scaffoldTemplateProperties.getScaffoldTemplates())) {
            Map<String, List<ScaffoldTemplate>> listMap = scaffoldTemplateProperties.getScaffoldTemplates().stream()
                    .collect(Collectors.groupingBy(scaffoldTemplate ->
                            StringUtils.isNotBlank(scaffoldTemplate.getKey()) ?
                                    scaffoldTemplate.getKey().toUpperCase() : "UNKNOWN"
                    ));
            return listMap.get(templateKey.toUpperCase());
        }
        return null;
    }
}
