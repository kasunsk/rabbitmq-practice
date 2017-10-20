package com.creative.primelog.rabbitmq.practice;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {

    private final static String QUEUE_NAME = "hello";

    private final static String HOST = "10.4.1.12";
    private final static String USER = "pagero";
    private final static String PASS = "pagero";

    public static void main(String args []) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(USER);
        factory.setPassword(PASS);
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String message = "Hello World !";

        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

        System.out.println(" [*] waiting for messages ");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
