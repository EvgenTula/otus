public class Card {
    private int id;
    private int pin;
    private double balance;
    public Card(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean access() {
        if (pin == 1234) {
            return true;
        }
        return false;
    }


}
