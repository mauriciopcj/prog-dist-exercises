package br.edu.ifpb.pdist.mauricio;

import com.rabbitmq.client.*;

public class MIddleware {

    public static Integer R = 0;
    private static final String NOME_EXCHANGE = "lts";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel canal = connection.createChannel();

        canal.exchangeDeclare(NOME_EXCHANGE, BuiltinExchangeType.DIRECT);
        String queueName = canal.queueDeclare().getQueue();

        String routing_key = "middleware";

        canal.queueBind(queueName, NOME_EXCHANGE, routing_key);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Recebido '" + delivery.getEnvelope().getRoutingKey() + "':'" + mensagem + "'");
        };

        canal.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    public static void send_timed(String dest, String msg) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (
                Connection connection = factory.newConnection();
                Channel canal = connection.createChannel()
        ){
            canal.exchangeDeclare(NOME_EXCHANGE, BuiltinExchangeType.DIRECT);

            String mensagem = R + ":" + msg;

            canal.basicPublish(NOME_EXCHANGE, dest, null, mensagem.getBytes("UTF-8"));
            System.out.println(" [x] Enviado '" + dest +  mensagem + "'");
        }
    }

    public void receive_timed(String canal, String metodo, String props, String msg) {

    }
}
