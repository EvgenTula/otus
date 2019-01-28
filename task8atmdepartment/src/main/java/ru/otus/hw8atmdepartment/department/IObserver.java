package ru.otus.hw8atmdepartment.department;
import ru.otus.hw8atmdepartment.atm.Atm;

public interface IObserver<T> {
    void register(Atm atm);
    void unregister(Atm atm);
    void restoreStateAllAtm();
    void printAtmInfo();
    void printTotalSum();
    T doCommand(Atm atm);
}
