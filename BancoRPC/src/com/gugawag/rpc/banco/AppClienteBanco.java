package com.gugawag.rpc.banco;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AppClienteBanco {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        // procura o serviço no RMI Registry local. Perceba que o cliente não connhece a implementação do servidor,
        // apenas a interface
        Registry registry = LocateRegistry.getRegistry();
        BancoServiceIF banco = (BancoServiceIF) registry.lookup("BancoService");

        menu();
        Scanner entrada = new Scanner(System.in);
        int opcao = entrada.nextInt();

        while(opcao != 9) {
            switch (opcao) {
                case 1: {
                    System.out.println("Digite o número da conta:");
                    String conta = entrada.next();
                    //chamada ao método remoto, como se fosse executar localmente
                    System.out.println("\nSaldo da conta " + conta + ": " + banco.saldo(conta));
                    break;
                }
                case 2: {
                    //chamada ao método remoto, como se fosse executar localmente
                    System.out.println("\nQtd contas: " + banco.quantidadeContas());
                    break;
                }
                case 3: {
                    System.out.println("Digite o valor da conta");
                    Double saldo = entrada.nextDouble();
                    banco.novaConta(banco.quantidadeContas() + 1 + "", saldo);
                    System.out.println("\nConta " + banco.quantidadeContas() + " criada!");
                    break;
                }
                default: {
                    System.out.println("Opção inválida!");
                    break;
                }
            }
            menu();
            opcao = entrada.nextInt();
        }
    }

    public static void menu() {
        System.out.println("\n========================================");
        System.out.println("=== Mauricio Pereira da Costa Junior ===");
        System.out.println("========================================");
        System.out.println("1 - Saldo da conta");
        System.out.println("2 - Quantidade de contas");
        System.out.println("3 - Adicionar nova conta");
        System.out.println("9 - Sair");
        System.out.println("========================================");
    }

}
