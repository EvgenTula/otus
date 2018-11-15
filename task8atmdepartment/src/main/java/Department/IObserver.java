package Department;
import Atm.Atm;

public interface IObserver<T> {
    void register(Atm atm);
    void unregister(Atm atm);
    void restoreStateAllAtm();
    void printAtmInfo();
    void printTotalSum();
    T doCommand(Atm atm);
}
