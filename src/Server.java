import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class Server {
    public static void main(String[] args) {
        try {
            Raffle raffle = new RaffleImp();

            Registry registry = LocateRegistry.createRegistry(1011);

            registry.rebind("Raffle", raffle);

            System.out.println("Server pronto.");
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}
