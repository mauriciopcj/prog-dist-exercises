package br.edu.ifpb.pdist.mauricio;

import com.rabbitmq.client.*;

public class ReceiveLogsDirectTwo {

    private static final String NOME_EXCHANGE = "direct_logs";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel canal = connection.createChannel();

        canal.exchangeDeclare(NOME_EXCHANGE, BuiltinExchangeType.DIRECT);
        String queueName = canal.queueDeclare().getQueue();

       String[] argumentos = {"info", "warning", "error"};

        if (argumentos.length < 1) {
            System.err.println("Uso: ReceiveLogsDirect [info] [warning] [error]");
            System.exit(1);
        }

        for (String severity : argumentos) {
            canal.queueBind(queueName, NOME_EXCHANGE, severity);
        }
        System.out.println(" [*] Aguardando mensagens. Para sair pressione CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Recebido '" + delivery.getEnvelope().getRoutingKey() + "':'" + mensagem + "'");
        };
        canal.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
