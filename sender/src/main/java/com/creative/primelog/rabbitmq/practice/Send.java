package com.creative.primelog.rabbitmq.practice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private final static String QUEUE_NAME = "hello";

    private final static String HOST = "10.4.1.12";
    private final static String USER = "pagero";
    private final  static String PASS = "pagero";

    public static void main(String args []) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(USER);
        factory.setPassword(PASS);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String message = "Hello World !";

        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(1000);
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] send ' " + message + " '");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        channel.close();
        connection.close();
    }
}
