package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {

    public static void main(String[] args) throws IOException {
        System.out.println("== Servidor ==");

        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();

        // pegando uma referência do canal de saída do socket. Ao escrever nesse canal, está se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma referência do canal de entrada do socket. Ao ler deste canal, está se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        String diretorioRaiz = "./br/edu/ifpb/gugawag/so/sockets/diretorios";

        // laço infinito do servidor
        while (true) {
            System.out.println("Cliente: " + socket.getInetAddress());

            String mensagem = dis.readUTF();

            String[] parts = mensagem.split(" ");
            String command = "";
            String archive = "";
            String newName = "";
            
            if(parts.length >= 1 && parts.length < 4) {
              command = parts[0];

              if (!command.equals("readdir")) {
                archive = parts[1];
                
                if (command.equals("rename")) {
                  newName = parts[2];
                }
              }

              switch (command) {
                case "readdir":
                  File raiz = new File(diretorioRaiz);
                  ArrayList<String> arr = new ArrayList<String>();
                  if(raiz.listFiles().length > 0) {
                    for (File f : raiz.listFiles()) {
                      arr.add(f.getPath());
                    }
                  }
                  dos.writeUTF(arr.toString());
                  break;
                
                case "rename":
                  new File(diretorioRaiz + "/" + archive).delete();
                  new File(diretorioRaiz + "/" + newName).mkdir();
                  dos.writeUTF("");
                  break;
                
                case "create":
                  new File(diretorioRaiz + "/" + archive).mkdir();
                  dos.writeUTF("");
                  break;
                
                case "remove":
                  new File(diretorioRaiz + "/" + archive).delete();
                  dos.writeUTF("");
                  break;
              
                default:
                  break;
              }

            } else {
              dos.writeUTF("comando inválido!");
            }

        }
        /*
         * Observe o while acima. Perceba que primeiro se lê a mensagem vinda do cliente (linha 29, depois se escreve
         * (linha 32) no canal de saída do socket. Isso ocorre da forma inversa do que ocorre no while do Cliente2,
         * pois, de outra forma, daria deadlock (se ambos quiserem ler da entrada ao mesmo tempo, por exemplo,
         * ninguém evoluiria, já que todos estariam aguardando.
         */
    }
}
