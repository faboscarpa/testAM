package com.alMundo.callCenter.cosumer;

import com.alMundo.callCenter.model.Call;
import com.alMundo.callCenter.service.EmployeesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * Created by fscarpa on 10/04/18.
 */
@Component
public class Dispatcher {

    private static Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);

    @Autowired
    private EmployeesService employeesService;

    @JmsListener(destination = "${queue.incomingCallQueue}")
    public void dispatchCall(Message callMessage) {
        try {
            Call call = new ObjectMapper().readValue(((TextMessage) callMessage).getText(), Call.class);
            LOGGER.info("Llamada entrante de '{}'", call.getCustomerName());
            employeesService.assignCallToEmployee(call);
        } catch (IOException | JMSException  e){
            LOGGER.error("No se puede convertir la llamada.", e);
        }

    }
}
