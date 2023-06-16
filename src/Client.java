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
            Registry registry = LocateRegistry.getRegistry(1011);

            Raffle raffle = (Raffle) registry.lookup("Raffle");

            while (true) {

                System.out.println("1 - Listar números diponiveís");
                System.out.println("2 - Escolher um número");

                System.out.println("Escolha uma das ações:");
                int action = scan.nextInt();

                switch (action) {
                    case 1:
                    System.out.println(raffle.getAvailableNumbers().toString());
                        break;
                    case 2:
                        System.out.println("Informe o número que deseja escolher:");
                        Integer number = scan.nextInt();
                        System.out.println(raffle.chooseNumber(clientName, number));
                        break;  
                }
            }
        
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
        
    }
}

