import java.util.*;

public class Atm implements IMemento {

    private TreeMap <RateСurrency, Integer> stockMoney;
    private int total_sum = 0;

    public Atm() {
        //this.id = id;
        stockMoney = new TreeMap<>(Collections.reverseOrder());
        Arrays.stream(RateСurrency.values()).forEach(i -> stockMoney.put(i,0));
    }

    public void deposit(RateСurrency rateСurrency, int cout) {
        total_sum += (cout * rateСurrency.getVal());
        int newCout = stockMoney.get(rateСurrency) + cout;
        stockMoney.put(rateСurrency,newCout);
    }

    public boolean withdraw(int sum) {
        System.out.println("withdraw " + sum + "\n");
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
        return true;
    }

    public void printStockMoney() {
        int sum = 0;
        for (var item : stockMoney.entrySet()) {
            System.out.println(item.getKey() + "("+item.getKey().getVal() + ") : " + item.getValue());
            sum += (item.getValue() * item.getKey().getVal());
        }
        System.out.println("All sum = " + sum + "\n");
    }

    public int getTotalSum() {
        return total_sum;
    }

    public IMemento SaveState() {
        return this;
    }

    @Override
    public void RestoreState(IMemento memento) {
        if (memento instanceof Atm) {
            this.stockMoney = ((Atm) memento).stockMoney;
            this.total_sum = ((Atm)memento).total_sum;
        }
    }
}
