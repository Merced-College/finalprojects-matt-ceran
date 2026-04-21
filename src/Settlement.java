import java.util.ArrayList;

// settlement holds the survivors and has methods for managing them
public class Settlement {

    private String name;
    private ArrayList<Survivor> survivors;

    public Settlement(String name) {
        this.name = name;
        this.survivors = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Survivor> getSurvivors() {
        return survivors;
    }

    public void addSurvivor(Survivor s) {
        survivors.add(s);
    }

    // remove a survivor by name, returns true if found
    public boolean removeSurvivor(String name) {
        for (int i = 0; i < survivors.size(); i++) {
            if (survivors.get(i).getName().equalsIgnoreCase(name)) {
                survivors.remove(i);
                return true;
            }
        }
        return false;
    }

    public double getAverageHealth() {
        if (survivors.size() == 0) {
            return 0.0;
        }

        int total = 0;
        for (Survivor s : survivors) {
            total += s.getHealth();
        }

        return (double) total / survivors.size();
    }

    // quick test to make sure stuff works
    public static void main(String[] args) {
        Settlement s = new Settlement("Sanctuary");
        s.addSurvivor(new Survivor("Preston"));
        s.addSurvivor(new Survivor("Sturges"));

        System.out.println("Settlement: " + s.getName());
        for (Survivor sv : s.getSurvivors()) {
            System.out.println(sv);
        }
        System.out.println("Average health: " + s.getAverageHealth());
    }
}
