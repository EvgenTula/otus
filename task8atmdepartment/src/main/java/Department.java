import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Department {

    private HashMap<Atm, IMemento> atmList = null;
    //private List<Atm> atmList = null;

    public Department() {
        atmList = new HashMap<>();
    }

    public void addAtm(Atm atm) {
        atmList.put(atm, atm.SaveState());
    }

    //public

    public void printTotalSum() {
        int sum = 0;
        /*
        for (Atm atm : atmList) {
            sum += atm.getTotalSum();
        }*/
        System.out.println("Departament atm : total sum (" + atmList.size() + ") : " + sum );
    }
}
