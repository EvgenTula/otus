package Department;

import Atm.Atm;

public interface IObserver {
    void register(Atm atm);
    void unregister(Atm atm);
    void restoreStateAllAtm();
    void printAtmInfo();
    void printTotalSum();
}
