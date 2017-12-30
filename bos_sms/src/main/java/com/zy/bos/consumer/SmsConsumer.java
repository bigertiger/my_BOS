package com.zy.bos.consumer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Service;

/** 
* @author  philchang 
* @date 2017年12月13日 下午7:13:04 
* @version 1.0.0
*   
*/
@Service("smsConsumer")
public class SmsConsumer  implements MessageListener{

	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage) message;
		try {
			System.out.println("接收短信的手机号是:" + mapMessage.getString("telephone") 
			+ "验证码是:" + mapMessage.getString("msg"));
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
