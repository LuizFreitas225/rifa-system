import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RaffleImp extends UnicastRemoteObject implements Raffle {
    String[] numbers ;

    protected RaffleImp() throws RemoteException {
        numbers = new String[3];
    }

    @Override
    public Boolean chooseNumber(String clientName ,Integer number) throws RemoteException {
        if(numbers.length > number){
            //invalid number
            return false;
        }
        else if(numbers[number - 1] == null ){
            numbers[number -1]  = clientName;
            return true;
        }
        return false;
    }

    @Override
    public List<Integer> getAvailableNumbers() throws RemoteException {

        return null;
    }
}
