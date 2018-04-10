package com.alMundo.callCenter.model;

/**
 * Created by fscarpa on 10/04/18.
 */
public class Call {

    private String customerName;

    public Call (){

    }

    public Call (String string) {
        this.customerName=string;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
