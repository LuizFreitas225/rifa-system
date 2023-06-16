import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
public class Server {
    public static void main(String[] args) {
        try {

            //RMI
            Raffle raffle = new RaffleImp();

            Registry registry = LocateRegistry.createRegistry(1011);

            registry.rebind("Raffle", raffle);


            //Socket
            ServerSocket serverSocket = new ServerSocket(8080); // Porta do servidor
            System.out.println("Server pronto.");

            
            //Recebendo clientes e criando thread pra todos
            List<Socket> clients = new ArrayList<Socket>();


            while(true){
              Socket clientSocket = serverSocket.accept();
              clients.add(clientSocket);
              System.out.println("Novo Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());


                // Cria uma nova thread para receber mensagens do cliente cliente
                Thread clienteThread = new Thread(() -> {
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        
                        String mensagem;
                        while ((mensagem = in.readLine()) != null) {
                            System.out.println("Cliente: " + mensagem);
                            
                            // Envia a mensagem recebida para todos os clientes 
                            sendMessageToAll(clients, mensagem, serverSocket);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                //Inicia Thread
                clienteThread.start();
            }

            
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }

     // Envia uma mensagem para todos os clientes
    private static void sendMessageToAll(List<Socket> clients, String mensagem, ServerSocket serverSocket) throws IOException {
      for (Socket clients2 : clients) {
       
     //Comunicando ganhador ao cliente
        PrintWriter out = new PrintWriter(clients2.getOutputStream(), true);
        out.println(mensagem);
      }

    //Encerra o Servidor 
    serverSocket.close();
    }
}
