package com.droolsinaction.applyequipment;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;

public class LocalTest {
    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        DMNRuntime dmnRuntime = KieRuntimeFactory.of(kcontainer.getKieBase()).get(DMNRuntime.class);
        DMNModel dmnModel = dmnRuntime.getModels().get(0);
        DMNContext context = dmnRuntime.newContext();

        Map<String,Object> applicationInfo = new HashMap<>();
        applicationInfo.put("price", 1900);
        applicationInfo.put("productType", "电脑");
        applicationInfo.put("urgency", "高");
        applicationInfo.put("category", "必备");

        context.set("设备申请", applicationInfo );

        DMNResult result = dmnRuntime.evaluateAll(dmnModel, context);

        System.out.println(result);
 
    }
}