package cn.meshed.cloud.rd.domain.cli;

import cn.meshed.cloud.rd.codegen.Adapter;
import cn.meshed.cloud.rd.domain.repo.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * <h1>生成适配器服务接口</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenerateAdapter {

    /**
     * 适配器接口
     */
    private Set<Adapter> adapters;

    /**
     * 生成路径
     */
    private String basePath;

    /**
     * 提交信息
     */
    private String commitMessage;

    /**
     * 提交分支信息
     */
    private Branch branch;

}
