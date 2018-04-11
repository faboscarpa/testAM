package com.alMundo.callCenter.model;

/**
 * Created by fscarpa on 10/04/18.
 */
public class Call {

    private static final int MAX_DURATION = 10000;
    private static final int MIN_DURATION = 500;

    private String customerName;
    private Integer duration;


    public Call (){
        //A modo de test se le asigna la duracion de la llamada desde la creacion.
        duration = getSimulateDuration();
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Este metodo tiene solo el proposito de test para simular cuanto va a durar la llamada.
     * @return duracion de la llamada en Milisegundos.
     */
    public int getSimulateDuration() {
        return (int) (Math.random() * ((MAX_DURATION - MIN_DURATION) + 1)) + MIN_DURATION;
    }
}
