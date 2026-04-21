// survivor class for the wasteland project
public class Survivor {

    private String name;
    private int health;
    private int level;
    private String role;

    public Survivor(String name) {
        this.name = name;
        this.health = 100;
        this.level = 1;
        this.role = "Unassigned";
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getLevel() { return level; }
    public String getRole() { return role; }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String toString() {
        return name + " | HP: " + health + " | Lvl: " + level + " | Role: " + role;
    }
}
