package com.bookstore.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.bookstore.service.BookService;
import com.bookstore.vo.Book;
import com.bookstore.vo.BookInput;

@Component
public class BookMQHandler {

	static final Logger LOG = LoggerFactory
			.getLogger(BookMQHandler.class);

	@Autowired
	private BookService bookService;


	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${spring.rabbitmq.consumer.queue.name}", durable = "true"), exchange = @Exchange(value = "${spring.rabbitmq.ExchangeDetails}", durable = "true"), key = "${spring.rabbitmq.consumer.routingkey}"))
	public void handleMessage(@Payload BookInput bi) {
		try {
			LOG.info("Rabbit MQ Project executing handleMessage");
			Book book = bookService.save(bi);
			bookService.sendData(book);
		} catch (Throwable e) {
			LOG.error(e.getMessage());
		}
	}

}
