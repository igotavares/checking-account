package br.com.ibeans.checkingaccount.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Value("${amqp.exchange:transaction}")
    private String exchangeName;

    @Value("${amqp.queue:transaction.query}")
    private String queueName;

    @Value("${amqp.dlx.queue:transaction.query.dlx}")
    private String deadLetterQueueName;

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder
                .fanoutExchange(exchangeName)
                .build();
    }

    @Bean
    public Queue queue() {
        return QueueBuilder
                .durable(queueName)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", deadLetterQueueName)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder
                .durable(deadLetterQueueName)
                .build();
    }

    @Bean
    public Binding binding(Exchange exchange, Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("*")
                .noargs();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper mapper) {
        final var jsonRabbitTemplate = new RabbitTemplate(connectionFactory);
        jsonRabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(mapper));
        return jsonRabbitTemplate;
    }

}
