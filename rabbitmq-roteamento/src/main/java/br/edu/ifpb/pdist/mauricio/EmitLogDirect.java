package br.edu.ifpb.pdist.mauricio;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {

    private static final String NOME_EXCHANGE = "direct_logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (
                Connection connection = factory.newConnection();
                Channel canal = connection.createChannel()
        ){
            canal.exchangeDeclare(NOME_EXCHANGE, BuiltinExchangeType.DIRECT);

//            String[] argumentos = { "error", "Run. Run. Or it will explode." };
            String[] argumentos = { "info", "Mauricio Pereira da Costa Junior" };

            String severidade = getSeverity(argumentos);
            String mensagem = getMessage(argumentos);

            canal.basicPublish(NOME_EXCHANGE, severidade, null, mensagem.getBytes("UTF-8"));
            System.out.println(" [x] Enviado '" + severidade + "':'" + mensagem + "'");
        }
    }

    private static String getSeverity(String[] strings) {
        if (strings.length < 1)
            return "info";
        return strings[0];
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 2)
            return "Hello Wolrd!";
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) return "";
        if (length <= startIndex) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
