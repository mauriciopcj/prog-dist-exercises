package br.edu.ifpb.pdist.mauricio;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ConsumidorDois {

    public static void main(String[] args) throws Exception {
        System.out.println("Consumidor");

//        String NOME_FILA = "filaOla";
        String NOME_FILA = "task_queue";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection conexao = connectionFactory.newConnection();

        Channel canal = conexao.createChannel();

        boolean duravel = true;

        canal.queueDeclare(NOME_FILA, duravel, false, false, null);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x] Recebido " + mensagem);

            try {
                doWork(mensagem);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("[x] Feito");
                canal.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };

        boolean autoAck = false;

        canal.basicConsume(NOME_FILA, autoAck, callback, consumerTag -> {});
        System.out.println("Continuarei executando outras atividades enquanto não chega mensagem...");
    }

    private static void doWork(String task) throws InterruptedException {
        for(char ch: task.toCharArray()) {
            if(ch == '.') Thread.sleep(1000);
        }
    }
}
