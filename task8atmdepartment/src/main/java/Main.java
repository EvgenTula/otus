/*Написать приложение ATM Department:
    • Приложение может содержать несколько ATM
    • Departmant может собирать сумму остатков со всех ATM
    • Department может инициировать событие – восстановить состояние всех ATM до начального.
        (начальные состояния у разных ATM могут быть разными)*/

public class Main {
    public static void main(String[] args) {

        Department department = new Department();

        Atm atmMini = new Atm();
        atmMini.deposit(RateСurrency.ONE, 100);
        atmMini.deposit(RateСurrency.FIVE, 100);
        atmMini.deposit(RateСurrency.TEN, 100);
        atmMini.deposit(RateСurrency.HOUNDRED, 5);

        Atm atmMiddle = new Atm();
        atmMiddle.deposit(RateСurrency.ONE, 100);
        atmMiddle.deposit(RateСurrency.FIVE, 100);
        atmMiddle.deposit(RateСurrency.TEN, 100);
        atmMiddle.deposit(RateСurrency.HOUNDRED, 10);
        atmMiddle.deposit(RateСurrency.FIVEHOUNDRED, 5);
        atmMiddle.deposit(RateСurrency.THOUSAND, 1);

        Atm atmMax = new Atm();
        atmMax.deposit(RateСurrency.ONE, 100);
        atmMax.deposit(RateСurrency.FIVE, 100);
        atmMax.deposit(RateСurrency.TEN, 100);
        atmMax.deposit(RateСurrency.HOUNDRED, 100);
        atmMax.deposit(RateСurrency.FIVEHOUNDRED, 100);
        atmMax.deposit(RateСurrency.THOUSAND, 100);
        atmMax.deposit(RateСurrency.FIVETHOUSAND, 100);

        department.addAtm(atmMini);
        department.addAtm(atmMiddle);
        department.addAtm(atmMax);

        atmMini.withdraw(10000);
        atmMini.printStockMoney();
        atmMini.withdraw(100);
        atmMini.printStockMoney();
        atmMini.withdraw(10);
        atmMini.printStockMoney();
        atmMini.withdraw(1);
        atmMini.printStockMoney();
        atmMini.withdraw(10);
        atmMini.printStockMoney();
        atmMini.withdraw(3);
        atmMini.printStockMoney();

    }
}
