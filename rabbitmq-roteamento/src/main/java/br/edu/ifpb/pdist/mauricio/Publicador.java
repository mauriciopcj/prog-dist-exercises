package br.edu.ifpb.pdist.mauricio;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publicador {

    private static final String NOME_EXCHANGE = "lts";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (
                Connection connection = factory.newConnection();
                Channel canal = connection.createChannel()
        ){
            canal.exchangeDeclare(NOME_EXCHANGE, BuiltinExchangeType.DIRECT);

            String routing_key = "rk_B";
            String mensagem = "rk_A";

            canal.basicPublish(NOME_EXCHANGE, routing_key, null, mensagem.getBytes("UTF-8"));
            System.out.println(" [x] Enviado '" + routing_key + "':'" + mensagem + "'");
        }
    }
}
