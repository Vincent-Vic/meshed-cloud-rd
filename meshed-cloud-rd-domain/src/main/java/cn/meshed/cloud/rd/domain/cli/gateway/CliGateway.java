package cn.meshed.cloud.rd.domain.cli.gateway;

import cn.meshed.cloud.rd.domain.cli.Archetype;
import cn.meshed.cloud.rd.domain.cli.Artifact;
import com.alibaba.cola.exception.SysException;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public interface CliGateway {

    /**
     * 原型构建
     *
     * @param outPath   输出路径
     * @param archetype 原型
     * @param artifact  制品信息
     * @throws SysException 构建异常信息
     */
    void archetype(String outPath, Archetype archetype, Artifact artifact) throws SysException;
}
