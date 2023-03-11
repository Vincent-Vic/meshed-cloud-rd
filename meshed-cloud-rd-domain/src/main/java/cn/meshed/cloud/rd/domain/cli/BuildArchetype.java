package cn.meshed.cloud.rd.domain.cli;

import cn.meshed.cloud.rd.domain.repo.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>构建原型</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BuildArchetype {

    private Archetype archetype;
    private Artifact artifact;
    private Branch branch;


}
