package com.droolsinaction.hellodrools;

import static org.junit.Assert.assertEquals;

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
    public void test() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kContainer.getKieBase();
        KieSession session = kieBase.newKieSession();

        session.insert("Hello Drools!");
        session.fireAllRules();

        assertEquals("Size of object in Working Memory is 1", 1, session.getObjects().size());
    }
}