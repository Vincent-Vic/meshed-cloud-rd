package cn.meshed.cloud.rd.project.convertor;


import cn.meshed.cloud.rd.domain.project.Model;
import cn.meshed.cloud.rd.project.gatewayimpl.database.dataobject.ModelDO;
import cn.meshed.cloud.utils.CopyUtils;

import java.io.Serializable;

/**
 * <h1>模型转换</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public class ModelConvertor implements Serializable {

    public static ModelDO toEntity(Model model, ModelDO modelDO) {
        if (modelDO == null) {
            return CopyUtils.copy(model, ModelDO.class);
        }
        modelDO.setName(model.getName());
        modelDO.setDescription(modelDO.getDescription());
        return modelDO;
    }

}
