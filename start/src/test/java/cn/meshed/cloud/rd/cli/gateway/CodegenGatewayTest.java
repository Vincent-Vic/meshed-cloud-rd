package cn.meshed.cloud.rd.cli.gateway;

import cn.meshed.cloud.rd.ProviderApplication;
import cn.meshed.cloud.rd.codegen.GenerateClassExecute;
import cn.meshed.cloud.rd.codegen.ObjectField;
import cn.meshed.cloud.rd.codegen.ObjectModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@SpringBootTest(classes = ProviderApplication.class)
@RunWith(SpringRunner.class)
public class CodegenGatewayTest {

    @Autowired
    private GenerateClassExecute generateClassExecute;

    @Test
    public void test() {
        ObjectModel model = new ObjectModel();
        model.setAuthor("Vincent Vic");
        model.setDescription("Test");
        model.setExplain("测试");
        model.setClassName("Test");
        model.setPackageName("cn.meshed.cloud.rd");
        model.addImport("org.junit.Test");
        model.setVersion("1.0.0");
        model.setSuperClass("Object");
        ObjectField field = new ObjectField();
        field.setExplain("姓名");
        field.setType("ClientObject");
        field.setNonNull(true);
        field.setName("name");
        field.setAnnotationJson("{\"Pattern\":{\"regexp\":\"abc\",\"message\":\"规则不匹配\"},\"Size\":{\"min\":22,\"max\":70,\"message\":\"不在范围\"},\"Max\":{\"value\":50,\"message\":\"最大50\"},\"Min\":{\"value\":0,\"message\":\"最小0\"},\"Email\":null}");
        model.setFields(Collections.singleton(field));
        String code = generateClassExecute.buildModel(model);
        System.out.println(code);
    }
}
