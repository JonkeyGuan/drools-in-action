package com.droolsinaction.expressfeeprarate;

import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleTest {

    static final Logger log = LoggerFactory.getLogger(RuleTest.class);

    @Test
    public void test() throws InterruptedException, ExecutionException {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kContainer.getKieBase();
        KieSession session = kieBase.newKieSession();

        insertExpressFee(session);
        insertWorkHours(session);
        insertRevenue(session);

        session.fireAllRules();
    }

    private void insertExpressFee(KieSession session) {
        session.insert(new ExpressFee("202112", 100000));
    }

    private void insertWorkHours(KieSession session) {
        session.insert(new WorkHours("202112", "行政", 10000));
        session.insert(new WorkHours("202112", "部门1", 20000));
        session.insert(new WorkHours("202112", "部门2", 30000));
        session.insert(new WorkHours("202112", "部门3", 40000));
        session.insert(new WorkHours("202112", "部门4", 50000));
        session.insert(new WorkHours("202112", "部门5", 60000));
    }

    private void insertRevenue(KieSession session) {
        session.insert(new Revenue("202112", "部门1", "淘宝", 100000));
        session.insert(new Revenue("202112", "部门1", "京东", 200000));
        session.insert(new Revenue("202112", "部门2", "淘宝", 300000));
        session.insert(new Revenue("202112", "部门2", "京东", 400000));
        session.insert(new Revenue("202112", "部门3", "淘宝", 500000));
        session.insert(new Revenue("202112", "部门3", "京东", 600000));
        session.insert(new Revenue("202112", "部门4", "淘宝", 700000));
        session.insert(new Revenue("202112", "部门4", "京东", 800000));
        session.insert(new Revenue("202112", "部门5", "淘宝", 900000));
        session.insert(new Revenue("202112", "部门5", "京东", 1000000));
    }

}