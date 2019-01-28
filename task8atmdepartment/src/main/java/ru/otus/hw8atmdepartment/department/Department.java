package ru.otus.hw8atmdepartment.department;

import ru.otus.hw8atmdepartment.atm.*;

public class Department {
    IObserver observer = new Observer();
    private int id;
    private String name;

    public Department(Integer departId, String departName) {
        this.id = departId;
        this.name = departName;
    }

    private void printDepartmentInfo() {
        System.out.print("ru.otus.hw8atmdepartment.department name: " + this.name  + " (" + this.id + ") : ");
    }

    public void registerAtm(Atm atm) {
        printDepartmentInfo();
        observer.register(atm);
    }

    public void unregisterAtm(Atm atm) {
        printDepartmentInfo();
        observer.unregister(atm);
    }

    public void RestoreState() {
        printDepartmentInfo();
        observer.restoreStateAllAtm();
    }

    public void printAtmListSum() {
        printDepartmentInfo();
        observer.printAtmInfo();
        observer.printTotalSum();
    }

    public void executeCommand(Atm atm) {
        System.out.println("executeCommand: ");
        ((AtmCommand)observer.doCommand(atm)).doCommand();
    }


}
