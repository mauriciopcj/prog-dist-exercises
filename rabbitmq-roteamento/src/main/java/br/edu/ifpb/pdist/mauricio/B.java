package br.edu.ifpb.pdist.mauricio;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class B {

    private static final Random generator = new Random();
    private static Integer R = generator.nextInt(9);
    private static Integer count_msg = 0;
    private static final String NOME_EXCHANGE = "lts";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel canal = connection.createChannel();

        canal.exchangeDeclare(NOME_EXCHANGE, BuiltinExchangeType.DIRECT);
        String queueName = canal.queueDeclare().getQueue();

        String routing_key = "rk_B";

        canal.queueBind(queueName, NOME_EXCHANGE, routing_key);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [x] Recebido '" + R + " - " + mensagem + "'");

            if (mensagem.split(":").length < 2) {
                try {
                    send_timed(mensagem, "rk_B");
                } catch (Exception e) {

                }
            } else if (!(mensagem.split(":")[1].equals("FIM"))) {
                atualizar_relogio(Integer.parseInt(mensagem.split(":")[1]));

                String teste = "rk_B";

//                if (count_msg > 30) {
//                    teste = "FIM";
//                }

                try {
                    send_timed(mensagem.split(":")[1], teste);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };

        canal.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    public static void atualizar_relogio(Integer relogio_msg) {
        R = Math.max(R, relogio_msg) + 1;
    }

    public static void incrementar_relogio() {
        R ++;
    }

    public static void incrementar_count_msg() {
        count_msg ++;
    }

    public static void send_timed(String dest, String msg) throws Exception {

        incrementar_count_msg();
        incrementar_relogio();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (
                Connection connection = factory.newConnection();
                Channel canal = connection.createChannel()
        ){
            canal.exchangeDeclare(NOME_EXCHANGE, BuiltinExchangeType.DIRECT);

            String mensagem = R + ":" + msg;

            canal.basicPublish(NOME_EXCHANGE, dest, null, mensagem.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Enviado '" + R + " - " + mensagem + "'");
        }
    }
}
