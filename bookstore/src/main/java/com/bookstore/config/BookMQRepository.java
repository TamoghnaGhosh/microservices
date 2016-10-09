package com.bookstore.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bookstore.vo.Book;

@Component
public class BookMQRepository {

	@Autowired
	@Qualifier(value = "producerRabbitTemplate")
	private RabbitTemplate producerRabbitTemplate;

	public void sendMessage(Book bi) {
		producerRabbitTemplate.convertAndSend(bi);
	}

}
