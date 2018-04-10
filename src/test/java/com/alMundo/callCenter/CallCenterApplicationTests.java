package com.alMundo.callCenter;

import com.alMundo.callCenter.model.Call;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CallCenterApplicationTests {

	@Autowired
	JmsTemplate jmsTemplate;

	@Test
	public void contextLoads() {
		for (int i = 0; i < 50;   i++){
			jmsTemplate.convertAndSend("incomingCall.q", "{\"customerName\":\"Test"+ i+"\"}");
		}


	}

}
