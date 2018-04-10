package com.alMundo.callCenter.service.impl;

import com.alMundo.callCenter.model.Call;
import com.alMundo.callCenter.model.Employee;
import com.alMundo.callCenter.model.enums.EmployeeCategory;
import com.alMundo.callCenter.service.EmployeesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Created by fscarpa on 10/04/18.
 */
@Service
public class EmployeesServiceImpl implements EmployeesService {

    private static Logger LOGGER = LoggerFactory.getLogger(EmployeesServiceImpl.class);

    private PriorityBlockingQueue<Employee> employeesPriorityQueue;

    public EmployeesServiceImpl () {
        employeesPriorityQueue = new PriorityBlockingQueue<>(5);
        employeesPriorityQueue.add(new Employee("SUPERVISOR", EmployeeCategory.SUPERVISOR));
        employeesPriorityQueue.add(new Employee("DIRECTOR", EmployeeCategory.DIRECTOR));
        employeesPriorityQueue.add(new Employee("OPERATOR 1", EmployeeCategory.OPERATOR));
        employeesPriorityQueue.add(new Employee("OPERATOR 2", EmployeeCategory.OPERATOR));
        employeesPriorityQueue.add(new Employee("OPERATOR 3", EmployeeCategory.OPERATOR));
    }

    @Override
    public void assignCallToEmployee(Call call) {
        Employee employee = null;
        try {
            employee = employeesPriorityQueue.take();
            processCall(employee,call);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void processCall(Employee employee, Call call) {
        LOGGER.info("el empleado '{}' esta procesando la llamada de '{}'",employee.getEmployeeName(),call.getCustomerName());
        int callDuration = this.getCallDuration();
        try {
            Thread.sleep(callDuration);
        } catch (InterruptedException ie) {
            LOGGER.error( "Error en la llamada", ie);
        }
        LOGGER.info("el empleado {} termino la llamada en {} segundos",employee.getEmployeeName(), TimeUnit.MILLISECONDS.toSeconds(callDuration));
        employeesPriorityQueue.add(employee);
    }

    public int getCallDuration() {
        return (int) (Math.random() * ((10000 - 5000) + 1)) + 5000;
    }
}
