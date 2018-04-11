package com.alMundo.callCenter.service;

import com.alMundo.callCenter.model.Call;
import com.alMundo.callCenter.model.Employee;
import com.alMundo.callCenter.model.ProcessCallCounters;

/**
 * Created by fscarpa on 10/04/18.
 */
public interface EmployeesService {

    /**
     * Asigna la llamada a un empleado, en caso de no encontrar
     * empleados disponibles durante cierto periodo de tiempo
     * la llamada es cortada.
     *
     * @param call
     */
    void assignCallToEmployee(Call call);

    /**
     * Disponibiliza a un empleado para atender llamadas.
     * @param employee
     */
    void addEmployeeToQueue(Employee employee);

    /**
     * retorna los contadores de llamadas.
     * @return
     */
    ProcessCallCounters getProcessCallCounters();

}
