import java.util.TreeMap;

public abstract class AbstractAtm {
    protected TreeMap<RateСurrency, Integer> stockMoney;
    void setStockMoney(TreeMap<RateСurrency, Integer> stockMoney) {
        this.stockMoney = stockMoney;
        recalcTotalSum();
    }
    abstract void recalcTotalSum();

}
