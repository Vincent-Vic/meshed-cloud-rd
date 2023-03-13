package cn.meshed.cloud.rd.project.executor;

import cn.meshed.cloud.rd.ProviderApplication;
import cn.meshed.cloud.rd.project.command.ServiceCmd;
import cn.meshed.cloud.rd.project.data.RequestFieldDTO;
import cn.meshed.cloud.rd.project.data.ResponsesFieldDTO;
import cn.meshed.cloud.rd.project.enums.RequestModeEnum;
import cn.meshed.cloud.rd.project.enums.RequestTypeEnum;
import cn.meshed.cloud.rd.project.enums.ServiceAccessModeEnum;
import cn.meshed.cloud.rd.project.executor.command.ServiceCmdExe;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@SpringBootTest(classes = ProviderApplication.class)
@RunWith(SpringRunner.class)
public class ServiceCmdExeTest {

    @Autowired
    private ServiceCmdExe serviceCmdExe;

    @Test
    public void execute() {
        serviceCmdExe.execute(buildMockServiceCmd());
    }

    @NotNull
    private ServiceCmd buildMockServiceCmd() {
        ServiceCmd serviceCmd = new ServiceCmd();
        //api
        serviceCmd.setGroupId("11d9d8d8cc37ec7dfaa2116ec59aca29");
        //rpc
//        serviceCmd.setGroupId("832a8955571acf359433a2fe09c2f45d");
        serviceCmd.setDescription("project xxx");
        serviceCmd.setName("资产服务列表");
        serviceCmd.setMethod("Edit");
        // todo 确保控制器内唯一
        serviceCmd.setUri("/edit");
        serviceCmd.setIdentifier("xxx");
        serviceCmd.setAccessMode(ServiceAccessModeEnum.ANONYMOUS);
        serviceCmd.setRequestMode(RequestModeEnum.JSON);
        serviceCmd.setRequestType(RequestTypeEnum.GET);
        serviceCmd.setResponseMerge(true);
        //请求参数
        RequestFieldDTO requestFieldDTO = new RequestFieldDTO();
        requestFieldDTO.setExplain("资产名称");
        requestFieldDTO.setFieldName("requestName");
        requestFieldDTO.setFieldType("String");
        requestFieldDTO.setNonNull(true);
        RequestFieldDTO requestFieldDTO2 = new RequestFieldDTO();
        requestFieldDTO2.setExplain("资产名称2");
        requestFieldDTO2.setFieldName("requestName2");
        requestFieldDTO2.setFieldType("String");
        requestFieldDTO2.setNonNull(true);
        serviceCmd.setRequests(Arrays.asList(requestFieldDTO, requestFieldDTO2));
        //响应参数
        ResponsesFieldDTO responsesFieldDTO = new ResponsesFieldDTO();
        responsesFieldDTO.setExplain("资产响应信息");
        responsesFieldDTO.setFieldName("responseName");
        responsesFieldDTO.setFieldType("String");
        serviceCmd.setResponses(Collections.singletonList(responsesFieldDTO));
        serviceCmd.setName("保存");
        return serviceCmd;
    }
}