public class Main {
    public static void main(String[] args) {
        Atm atm = new Atm();

        atm.deposit(RateСurrency.ONE, 100);
        atm.deposit(RateСurrency.FIVE, 100);
        atm.deposit(RateСurrency.TEN, 100);
        atm.deposit(RateСurrency.HOUNDRED, 100);
        atm.deposit(RateСurrency.FIVEHOUNDRED, 50);
        atm.deposit(RateСurrency.THOUSAND, 10);
        atm.deposit(RateСurrency.FIVETHOUSAND, 5);
        atm.deposit(RateСurrency.FIVETHOUSAND, 5);

        atm.printStockMoney();
        atm.withdraw(10000);
        atm.printStockMoney();
        atm.withdraw(100);
        atm.printStockMoney();
        atm.withdraw(10);
        atm.printStockMoney();
        atm.withdraw(1);
        atm.printStockMoney();
        atm.withdraw(10);
        atm.printStockMoney();
        atm.withdraw(3);
        atm.printStockMoney();
    }
}
