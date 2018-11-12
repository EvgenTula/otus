package Atm;

public abstract class AtmCommand {
    IAtm atm = null;

    public AtmCommand(IAtm atm) {
        this.atm = atm;
    }

    public abstract void doCommand();
}
