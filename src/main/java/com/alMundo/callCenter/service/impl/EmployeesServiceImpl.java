package com.alMundo.callCenter.service.impl;

import com.alMundo.callCenter.model.Call;
import com.alMundo.callCenter.model.Employee;
import com.alMundo.callCenter.model.ProcessCallCounters;
import com.alMundo.callCenter.service.EmployeesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by fscarpa on 10/04/18.
 */
@Service
public class EmployeesServiceImpl implements EmployeesService {

    @Value("${max-call-wait-time}")
    private long MAX_CALL_WAIT_TIME;
    private static Logger LOGGER = LoggerFactory.getLogger(EmployeesServiceImpl.class);

    private PriorityBlockingQueue<Employee> employeesPriorityQueue;
    private ProcessCallCounters processCallCounters;

    public EmployeesServiceImpl() {
        employeesPriorityQueue = new PriorityBlockingQueue<>();
        processCallCounters = new ProcessCallCounters();

    }

    @Override
    public void assignCallToEmployee(Call call) {
        Employee employee = null;
        try {
            employee = employeesPriorityQueue.poll(MAX_CALL_WAIT_TIME, TimeUnit.MILLISECONDS);
            if (employee == null) {
                LOGGER.info("En estos momentos no podemos atender su llamada, por favor intente mas tarde.");
                processCallCounters.incrementCallNoProcessCount();
            } else {
                processCall(employee, call);
                processCallCounters.incrementCallProcessOkCout();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Error inesperado al asignar llamadas.", e);
        }

    }

    @Override
    public void addEmployeeToQueue(Employee employee) {
        employeesPriorityQueue.add(employee);
    }

    /**
     * Simula la operacion de una llamada durmiendo el thread segnu el tiempo seteado en la llamada.
     *
     * @param employee
     * @param call
     */
    private void processCall(Employee employee, Call call) {
        LOGGER.info("el empleado '{}' esta procesando la llamada de '{}'", employee.getEmployeeName(), call.getCustomerName());
        int callDuration = call.getDuration();
        try {
            Thread.sleep(callDuration);
        } catch (InterruptedException ie) {
            LOGGER.error("Error en la llamada", ie);
        }
        LOGGER.info("el empleado {} termino la llamada en {} segundos", employee.getEmployeeName(), TimeUnit.MILLISECONDS.toSeconds(callDuration));
        addEmployeeToQueue(employee);
    }

    public ProcessCallCounters getProcessCallCounters() {
        return processCallCounters;
    }
}
