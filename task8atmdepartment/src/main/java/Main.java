import Atm.*;
import Department.*;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Department department = new Department(new Random().nextInt(10),"main department");

        Atm atmMini = new Atm(1);
        atmMini.deposit(RateСurrency.ONE, 100);
        atmMini.deposit(RateСurrency.FIVE, 100);
        atmMini.deposit(RateСurrency.TEN, 100);
        atmMini.deposit(RateСurrency.HOUNDRED, 5);

        Atm atmMiddle = new Atm(2);
        atmMiddle.deposit(RateСurrency.ONE, 100);
        atmMiddle.deposit(RateСurrency.FIVE, 100);
        atmMiddle.deposit(RateСurrency.TEN, 100);
        atmMiddle.deposit(RateСurrency.HOUNDRED, 10);
        atmMiddle.deposit(RateСurrency.FIVEHOUNDRED, 5);
        atmMiddle.deposit(RateСurrency.THOUSAND, 1);

        Atm atmMax = new Atm(3);
        atmMax.deposit(RateСurrency.ONE, 100);
        atmMax.deposit(RateСurrency.FIVE, 100);
        atmMax.deposit(RateСurrency.TEN, 100);
        atmMax.deposit(RateСurrency.HOUNDRED, 100);
        atmMax.deposit(RateСurrency.FIVEHOUNDRED, 100);
        atmMax.deposit(RateСurrency.THOUSAND, 100);
        atmMax.deposit(RateСurrency.FIVETHOUSAND, 100);

        department.registerAtm(atmMini);
        department.registerAtm(atmMiddle);
        department.registerAtm(atmMax);
        department.printAtmListSum();

        atmMax.withdraw(10000);
        atmMiddle.withdraw(100);
        atmMini.withdraw(10);
        atmMiddle.withdraw(1);
        atmMax.withdraw(10);
        atmMini.withdraw(3);

        department.printAtmListSum();
        department.RestoreState();
        department.printAtmListSum();
        department.unregisterAtm(atmMax);
        department.printAtmListSum();
    }
}
