import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RaffleImp extends UnicastRemoteObject implements Raffle {
    String[] numbers ;

    protected RaffleImp() throws RemoteException {
        numbers = new String[3];
    }

    @Override
    public String chooseNumber(String clientName ,Integer number) throws RemoteException {
        if(number  <= 0  || numbers.length < number){
            //invalid number
            return "Número inválido, o numero deve ser mair que 0 e menor que " + (numbers.length + 1) + "."  ;
        }
        else if(numbers[number - 1] == null ){
            numbers[number -1]  = clientName;
            return "Número  " +number+  " escolhido.";
        }
        return "O número " +number+ " já foi escolhido anteriormente por alguem, tente outro numero.";
    }

    @Override
    public List<Integer> getAvailableNumbers() throws RemoteException {
        List<Integer> availableNumbers = new ArrayList<>();

        for ( int index = 0; index < numbers.length; index++){
            if ( numbers[index] == null || numbers[index].isEmpty()){
                availableNumbers.add(index + 1);
            }
        }
        return availableNumbers;
    }
}
