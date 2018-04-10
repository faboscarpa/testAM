package com.alMundo.callCenter.model;

import com.alMundo.callCenter.model.enums.EmployeeCategory;

/**
 * Created by fscarpa on 10/04/18.
 */
public class Employee implements Comparable<Employee> {

    private String employeeName;
    private EmployeeCategory category;

    public Employee() {

    }


    public Employee(String name, EmployeeCategory category) {
        this.employeeName = name;
        this.category = category;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public EmployeeCategory getCategory() {
        return category;
    }

    public void setCategory(EmployeeCategory category) {
        this.category = category;
    }

    @Override
    public int compareTo(Employee employee) {
        return Integer.compare(category.getValue(), employee.getCategory().getValue());
    }

}
