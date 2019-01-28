package ru.otus.hw8atmdepartment.atm;

public class AtmCommandPrintInfo extends AtmCommand{
    public AtmCommandPrintInfo(IAtm atm) {
        super(atm);
    }

    @Override
    public void doCommand() {
        this.atm.printAtmInfo();
    }
}
