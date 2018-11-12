package Atm;

public class AtmCommandPrintStockMoney extends AtmCommand {
    public AtmCommandPrintStockMoney(IAtm atm) {
        super(atm);
    }

    @Override
    public void doCommand() {
        this.atm.printStockMoney();
    }
}
