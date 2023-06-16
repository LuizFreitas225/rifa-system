import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

            Boolean hasEmptyPosition = false;
            for (String item : numbers) {
                if(item == null){
                  hasEmptyPosition = true ;
                  break;
                }
            }
            if(hasEmptyPosition){
                return "Número  " +number+  " escolhido.";
            }else{
                Random sortition = new Random();
                Integer winner = sortition.nextInt(numbers.length);
                return "Todos os números foram preenchidos, o ganhador foi o nosso cliente com o  número "
                 + (winner+ 1) + ": " + numbers[winner];
            }
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
