import java.util.*;

public class Atm implements IMemento {

    private int id;
    private TreeMap <RateСurrency, Integer> stockMoney;
    private int total_sum = 0;

    public Atm(int id) {
        this.id = id;
        this.stockMoney = new TreeMap<>(Collections.reverseOrder());
        Arrays.stream(RateСurrency.values()).forEach(i -> stockMoney.put(i,0));
    }

    private Atm(Atm obj) {
        this.stockMoney = new TreeMap<>(obj.stockMoney);
        this.total_sum = obj.total_sum;
    }

    public void deposit(RateСurrency rateСurrency, int cout) {
        total_sum += (cout * rateСurrency.getVal());
        int newCout = stockMoney.get(rateСurrency) + cout;
        stockMoney.put(rateСurrency,newCout);
    }

    public boolean withdraw(int sum) {
        System.out.println("Atm " + this.id + " withdraw " + sum);
        if (sum < 0) {
            System.out.println("Illegal argument\nResult: false\n");
            return  false;
        }
        if (sum > total_sum) {
            System.out.println("So much money\nResult: false\n");
            return  false;
        }
        int original_sum = sum;

        LinkedHashMap<RateСurrency, Integer> withdrawMoney = new LinkedHashMap<>();
        withdrawMoney.putAll(stockMoney);
            for (var item : withdrawMoney.entrySet()) {
                    if (sum / item.getKey().getVal() > 0) {
                        int i = sum / item.getKey().getVal();
                        if (i > item.getValue()) {
                            sum -= item.getValue();
                            withdrawMoney.put(item.getKey(), 0);
                        } else {
                            sum -= i * item.getKey().getVal();
                            withdrawMoney.put(item.getKey(), item.getValue() - i);
                        }
                    }
                }
        if (sum != 0) {
            System.out.println("Not enough currency\n");
            return  false;
        }
        stockMoney.putAll(withdrawMoney);
        total_sum -= original_sum;
        this.printStockMoney();
        return true;
    }

    public void printStockMoney() {
        int sum = 0;
        for (var item : stockMoney.entrySet()) {
            System.out.println(item.getKey() + "("+item.getKey().getVal() + ") : " + item.getValue());
            sum += (item.getValue() * item.getKey().getVal());
        }
        System.out.println("Atm " + this.id + " total sum = " + sum + "\n");
    }

    public int getTotalSum() {
        return total_sum;
    }

    @Override
    protected Atm clone() {
        return new Atm(this);
    }

    public IMemento SaveState() {
        return this.clone();
    }

    @Override
    public void RestoreState(IMemento memento) {
        if (memento instanceof Atm) {
            this.stockMoney = ((Atm) memento).stockMoney;
            this.total_sum = ((Atm)memento).total_sum;
        }
    }
}
