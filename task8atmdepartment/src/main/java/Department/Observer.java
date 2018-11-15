package Department;
import Atm.*;
import java.util.ArrayList;
import java.util.List;

public class Observer<T> implements IObserver<T> {
    List<Atm> atmList = new ArrayList<>();
    @Override
    public void register(Atm atm) {
        System.out.println("Atm ID : " + atm.getId() + " registered!");
        atm.SaveState();
        atmList.add(atm);
    }

    @Override
    public void unregister(Atm atm) {
        System.out.println("Atm ID : " + atm.getId() + " unregistered!");
        atmList.remove(atm);
    }

    @Override
    public void restoreStateAllAtm() {
        System.out.println("Restore state all atm\n");
        for (Atm atm : atmList) {
            atm.RestoreState();
        }
    }

    public void printAtmInfo() {
        for (Atm item : atmList) {
            for (AtmCommand command: item.commandList) {
                command.doCommand();
            }
        }
    }

    public void printTotalSum() {
        int sum = 0;
        for (Atm atm : atmList) {
            sum += atm.getTotalSum();
        }
        System.out.println("Department sum : " + sum);
    }

    @Override
    public T doCommand(Atm atm) {
        return (T) atm.command;
    }

}
