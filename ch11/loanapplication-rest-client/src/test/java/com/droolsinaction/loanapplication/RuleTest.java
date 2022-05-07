package com.droolsinaction.loanapplication;

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
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.KieServiceResponse.ResponseType;
import org.kie.server.api.model.ServiceResponse;
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
        allClasses.add(Applicant.class);
        allClasses.add(Loan.class);
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

        Applicant applicant = new Applicant();
        applicant.setName("Jonkey Guan");
        applicant.setAge(40);
        applicant.setCreditScore(410);
        applicant.setYearlyIncome(90000);
        commands.add(commandFactory.newInsert(applicant));

        Loan loan = new Loan();
        loan.setAmount(250000);
        loan.setDuration(10);
        commands.add(commandFactory.newInsert(loan));

        Command<?> fireAllRules = commandFactory.newFireAllRules();
        Command<?> process = commandFactory.newStartProcess("LoanApplication.LoanApplication");
        Command<?> getObjectsApplicant = commandFactory.newGetObjects(new ClassObjectFilter(Applicant.class), "Applicant");
        Command<?> getObjectsLoan= commandFactory.newGetObjects(new ClassObjectFilter(Loan.class), "Loan");
        commands.addAll(Arrays.asList(fireAllRules, process, getObjectsApplicant, getObjectsLoan));

        Command<?> batchCommand = commandFactory.newBatchExecution(commands);
        ServiceResponse<ExecutionResults> executeResponse = rulesClient.executeCommandsWithResults("LoanApplication", batchCommand);

        if (executeResponse.getType() == ResponseType.SUCCESS) {
            @SuppressWarnings("unchecked")
            List<Applicant> applicants = (List<Applicant>) executeResponse.getResult().getValue("Applicant");
            for (Applicant a : applicants) {
                log.info("" + a);
            }

            @SuppressWarnings("unchecked")
            List<Loan> loans = (List<Loan>) executeResponse.getResult().getValue("Loan");
            for (Loan l : loans) {
                log.info("" + l);
            }
        }
    }

}