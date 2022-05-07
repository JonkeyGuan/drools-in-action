package com.droolsinaction.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.ObjectFilter;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.KieServiceResponse.ResponseType;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleTest {

    static final Logger log = LoggerFactory.getLogger(RuleTest.class);

    private static final String droolsUrl = "http://localhost:8080/kie-server/services/rest/server";
    private static final String username = "rhdmAdmin";
    private static final String password = "admin@123";

    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

    private static KieServicesConfiguration kieServicesConfig;
    private static KieServicesClient kieServicesClient;

    @Before
    public void setup() {
        kieServicesConfig = KieServicesFactory.newRestConfiguration(droolsUrl, username, password);
        kieServicesConfig.setMarshallingFormat(FORMAT);

        Set<Class<?>> allClasses = new HashSet<Class<?>>();
        allClasses.add(General.class);
        allClasses.add(GeneralInQueue.class);
        kieServicesConfig.addExtraClasses(allClasses);

        kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfig);
    }

    @After
    public void teardown() {

    }

    @Test
    public void test() {

        RuleServicesClient rulesClient = kieServicesClient.getServicesClient(RuleServicesClient.class);

        KieCommands commandFactory = KieServices.Factory.get().getCommands();

        List<Command<?>> commands = new ArrayList<>();

        String[] names = new String[] { "吕布", "关羽", "张飞", "赵云" };
        String[] colors = new String[] { "紫色", "蓝色", "黑色", "白色" };
        int[] positions = new int[] { 1, 2, 3, 4 };

        for (int name = 0; name < names.length; name++) {
            for (int color = 0; color < colors.length; color++) {
                for (int position = 0; position < positions.length; position++) {
                    commands.add(commandFactory.newInsert(new General(names[name], colors[color], positions[position])));
                }
            }
        }

        Command<?> fireAllRules = commandFactory.newFireAllRules();
        ObjectFilter filter = new ClassObjectFilter(GeneralInQueue.class);
        Command<?> getObjects = commandFactory.newGetObjects(filter, "GeneralInQueue");
        Command<?> dispose = commandFactory.newDispose();
        commands.addAll(Arrays.asList(fireAllRules, getObjects, dispose));

        Command<?> batchCommand = commandFactory.newBatchExecution(commands);
        ServiceResponse<ExecutionResults> executeResponse = rulesClient.executeCommandsWithResults("General", batchCommand);

        if (executeResponse.getType() == ResponseType.SUCCESS) {
            @SuppressWarnings("unchecked")
            List<GeneralInQueue> GeneralInQueues = (List<GeneralInQueue>) executeResponse.getResult().getValue("GeneralInQueue");
            for (GeneralInQueue generalInQueue : GeneralInQueues) {
                System.out.println(generalInQueue);
            }
        }
    }

}