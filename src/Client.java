import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String clientName = "";

        System.out.println("Informe seu nome: ");
        clientName =  scan.next();
        try {
            System.out.println("Conectando...");
            //RMI
            Registry registry = LocateRegistry.getRegistry(1011);

            Raffle raffle = (Raffle) registry.lookup("Raffle");
            //Socket
            Socket socket = new Socket("localhost", 8080); // IP e porta do servidor


            // Thread para receber mensagens do servidor
            Thread receberThread = new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String menssage;
                    while ((menssage = in.readLine()) != null) {
                        System.out.println(menssage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receberThread.start();

            Boolean stop = false;
            while (!stop) {

                System.out.println("1 - Listar números diponiveís");
                System.out.println("2 - Escolher um número");
                System.out.println("3 - Sair");

                System.out.println("Escolha uma das ações:");
                int action = scan.nextInt();

                switch (action) {
                    case 1:
                    if(raffle.getAvailableNumbers().isEmpty()){
                       System.out.println("Todos as posições foram vendidas e a rifa encerrada.");
                    }else{
                        System.out.println(raffle.getAvailableNumbers().toString());
                    }
                   
                        break;
                    case 2:
                        System.out.println("Informe o número que deseja escolher:");
                        Integer number = scan.nextInt();
                        String returnMessage = raffle.chooseNumber(clientName, number);

                        if(returnMessage.contains("Todos os números foram preenchidos")){
                            stop = true;
                            //Enviando resultado ao servidor
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                            out.println(returnMessage);
                            
                        }else{
                            System.out.println(returnMessage);
                        }
                        
                        break; 

                        case 3:
                            System.out.println("Saindo...");
                            stop = true;
                        break;
                }
            }
           receberThread.join(); 
           socket.close();
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
        
    }
}

