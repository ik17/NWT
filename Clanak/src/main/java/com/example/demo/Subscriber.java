package com.example.demo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Subscriber {
	@RabbitListener(queues="spring-boot-3")
	public void receivedMessage(String msg) {
		System.out.println("Received message " + msg);
	}
}
