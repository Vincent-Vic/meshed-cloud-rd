package cn.meshed.cloud.rd.domain.cli;

import cn.meshed.cloud.utils.AssertUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * <h1>脚手架信息</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class Skeleton {

    /**
     * 引擎模板 具体的模板
     */
    private String engineTemplate;
    /**
     * 构建项目的制品信息
     */
    private Artifact artifact;

    /**
     * 校验
     */
    public void verification() {
        AssertUtils.isTrue(StringUtils.isNotBlank(this.engineTemplate), "引擎模板不能为空");
        this.artifact.verification();
    }
}
