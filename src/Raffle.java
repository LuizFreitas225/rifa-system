import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Raffle extends Remote {
    Boolean chooseNumber(String clientName , Integer number) throws RemoteException;
    List<Integer> getAvailableNumbers() throws RemoteException;
}
