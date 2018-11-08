import java.util.HashMap;

public class Department {

    private HashMap<Atm, IMemento> atmList;

    public Department() {
        atmList = new HashMap<>();
    }

    public void addAtm(Atm atm) {
        atmList.put(atm, atm.SaveState());
    }

    public void RestoreState() {
        System.out.println("Restore state all atm\n");
        for (var item : atmList.entrySet()) {
            item.getKey().RestoreState(item.getValue());
        }
    }

    public void printTotalSum() {
        int sum = 0;
        for (var item : atmList.entrySet()) {
            sum += item.getKey().getTotalSum();
        }
        System.out.println("Departament atm : total sum (" + atmList.size() + ") : " + sum + "\n");
    }
}
