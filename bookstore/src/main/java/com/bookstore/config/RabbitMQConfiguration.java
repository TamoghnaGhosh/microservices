package com.bookstore.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.bookstore.vo.BookInput;

@Configuration
public class RabbitMQConfiguration {
	
	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.port}")
	private int port;

	@Value("${spring.rabbitmq.username}")
	private String userName;

	@Value("${spring.rabbitmq.password}")
	private String password;

	@Value("${spring.rabbitmq.producer.queue.name}")
	private String producerQueueName;

	@Value("${spring.rabbitmq.virtual-host}")
	private String vHost;

	@Value("${spring.rabbitmq.ExchangeDetails}")
	private String exchange;
	
	@Value("${spring.rabbitmq.producer.routingkey}")
	private String producerRoutingKey;

	@Bean
	public MessageConverter jsonMessageConverter() {
		DefaultClassMapper classMapper = new DefaultClassMapper();
		classMapper.setDefaultType(BookInput.class);
		Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
			messageConverter.setClassMapper(classMapper);
		 
		 return messageConverter;
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
				host);
		connectionFactory.setUsername(userName);
		connectionFactory.setPassword(password);
		connectionFactory.setPort(port);
		connectionFactory.setVirtualHost(vHost);
		return connectionFactory;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	Queue queue() {
		return new Queue(producerQueueName, true);
	}

	@Bean
	List<Binding> binding() {
		DirectExchange producerExchange = new DirectExchange(exchange);

		return Arrays.asList(
				BindingBuilder.bind(queue()).to(producerExchange)
						.with(producerRoutingKey));
	}

	@Primary
	@Bean(name = "producerRabbitTemplate")
	RabbitTemplate getRabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setExchange(exchange);
		template.setRoutingKey(producerRoutingKey);
		template.setQueue(producerQueueName);
		template.setMessageConverter(jsonMessageConverter());
		return template;
	}
	
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		factory.setMessageConverter(jsonMessageConverter());
		return factory;
	}
}
