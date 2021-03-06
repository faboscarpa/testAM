package com.alMundo.callCenter;

import static org.junit.Assert.*;


import com.alMundo.callCenter.model.Call;
import com.alMundo.callCenter.model.Employee;
import com.alMundo.callCenter.model.enums.EmployeeCategory;
import com.alMundo.callCenter.service.EmployeesService;
import com.jayway.awaitility.Awaitility;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CallCenterApplicationTests {

    private final static Logger LOGGER = LoggerFactory.getLogger(CallCenterApplicationTests.class);

    @Value("${queue.incomingCallQueue}")
    private String INCOMING_CALL_Q;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    private EmployeesService employeesService;

    @ClassRule
    public static EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();


    /**
     * Test de concurrencia, se valida que al entrar 10 mensajes en la queue son
     * antedidos en paralelo por el consumer sin superar los 12 segundos (tiempo max que puede demorar una call + 2 seg).
     * @throws InterruptedException
     */
    @Test
    public void tenConcurrentCallsTest() throws InterruptedException {
        List<String> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messages.add("{\"customerName\":\"Test" + i + "\"}");
        }

        addEmployee();

        doConcurrentsCalls(messages);

        Awaitility.await().atMost(12000,TimeUnit.MILLISECONDS).until(() -> employeesService.getProcessCallCounters().getCallCount() == 10);

        assertEquals(10, employeesService.getProcessCallCounters().getCallCount());
        assertEquals(10, employeesService.getProcessCallCounters().getCallProcessOkCout());
    }

    @Test
    public void twentyConcurrentCallsTest() throws InterruptedException {
        List<String> messages = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            messages.add("{\"customerName\":\"Test" + i + "\"}");
        }

        addEmployee();

        doConcurrentsCalls(messages);

        Awaitility.await().atMost(200000,TimeUnit.MILLISECONDS).until(() -> employeesService.getProcessCallCounters().getCallCount() == 20);

        assertEquals(20, employeesService.getProcessCallCounters().getCallCount());
        assertEquals(20, employeesService.getProcessCallCounters().getCallProcessOkCout());
    }

    /**
     * Con este test se valida que se corten las llamadas luego de estar demasiado tiempo en espera.
     * Para esto simplemente no cargo ningun empleado.
     * @throws InterruptedException
     */
    @Test
    public void tenConcurrentCallsWithoutEmployeesTest() throws InterruptedException {
        List<String> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messages.add("{\"customerName\":\"Test" + i + "\"}");
        }
        doConcurrentsCalls(messages);

        Awaitility.await().atMost(20000,TimeUnit.MILLISECONDS).until(() -> employeesService.getProcessCallCounters().getCallCount() == 10);

        assertEquals(10, employeesService.getProcessCallCounters().getCallCount());
        assertEquals(10, employeesService.getProcessCallCounters().getCallNoProcessCount());

    }

    private void doConcurrentsCalls(List<String> messages) {
        messages.parallelStream().forEach(message -> insertMessageToCallQueue(message));
    }

    private void insertMessageToCallQueue(String message) {
        LOGGER.info("insertando mensaje + " + message);
        jmsTemplate.convertAndSend(INCOMING_CALL_Q, message);
    }


    private void addEmployee (){
        employeesService.addEmployeeToQueue(new Employee("SUPERVISOR", EmployeeCategory.SUPERVISOR));
        employeesService.addEmployeeToQueue(new Employee("SUPERVISOR 2", EmployeeCategory.SUPERVISOR));
        employeesService.addEmployeeToQueue(new Employee("DIRECTOR", EmployeeCategory.DIRECTOR));
        employeesService.addEmployeeToQueue(new Employee("DIRECTOR 2", EmployeeCategory.DIRECTOR));
        employeesService.addEmployeeToQueue(new Employee("OPERATOR 1", EmployeeCategory.OPERATOR));
        employeesService.addEmployeeToQueue(new Employee("OPERATOR 2", EmployeeCategory.OPERATOR));
        employeesService.addEmployeeToQueue(new Employee("OPERATOR 3", EmployeeCategory.OPERATOR));
        employeesService.addEmployeeToQueue(new Employee("OPERATOR 5", EmployeeCategory.OPERATOR));
        employeesService.addEmployeeToQueue(new Employee("OPERATOR 5", EmployeeCategory.OPERATOR));
        employeesService.addEmployeeToQueue(new Employee("OPERATOR 6", EmployeeCategory.OPERATOR));
    }
}
