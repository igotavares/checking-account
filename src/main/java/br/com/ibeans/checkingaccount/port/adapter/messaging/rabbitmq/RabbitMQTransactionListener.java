package br.com.ibeans.checkingaccount.port.adapter.messaging.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQTransactionListener {

    @RabbitListener(queues = "${amqp.queue:transaction.query}")
    public void receive(String message) {
        System.out.println("Received <" + message + ">");
    }

}
