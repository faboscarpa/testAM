package com.alMundo.callCenter.model.enums;

/**
 * Created by fscarpa on 10/04/18.
 */
public enum EmployeeCategory {

    OPERATOR(0), SUPERVISOR(1), DIRECTOR(2);

    private Integer value;

    public int getValue() {
        return value;
    }

    EmployeeCategory(int value) {
        this.value = value;
    }


}
