package br.edu.ifpb.pdist.mauricio;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Produtor {

    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

//        String NOME_FILA = "filaOla";
        String NOME_FILA = "task_queue";

        try (
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()
        ) {
            boolean duravel = true;
            channel.queueDeclare(NOME_FILA, duravel, false, false, null);

//            String mensagem = String.join("", args);
            String pm1 = "Primeira mensagem .";
            String pm2 = "Segunda mensagem ..";
            String pm3 = "Terceira mensagem ...";
            String pm4 = "Mauricio Pereira da Costa Junior ....";
            String pm5 = "Quarta mensagem .....";
            String pm6 = "Quinta mensagem ....";
            String pm7 = "Sexta mensagem .....";

            channel.basicPublish("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, pm1.getBytes());
            System.out.println("[x] Enviado " + pm1);
            channel.basicPublish("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, pm2.getBytes());
            System.out.println("[x] Enviado " + pm2);
            channel.basicPublish("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, pm3.getBytes());
            System.out.println("[x] Enviado " + pm3);
            channel.basicPublish("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, pm4.getBytes());
            System.out.println("[x] Enviado " + pm4);
            channel.basicPublish("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, pm5.getBytes());
            System.out.println("[x] Enviado " + pm5);
            channel.basicPublish("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, pm6.getBytes());
            System.out.println("[x] Enviado " + pm6);
            channel.basicPublish("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, pm7.getBytes());
            System.out.println("[x] Enviado " + pm7);
        }


    }
}
