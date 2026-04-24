import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// saves and loads the settlement data to a simple text file
// format is sectioned with SETTLEMENT, SURVIVORS, ITEMS headers
public class FileManager {

    // write everything out to the given file
    public static void save(Settlement settlement, Inventory inventory, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("SETTLEMENT");
            pw.println(settlement.getName());

            pw.println("SURVIVORS");
            for (Survivor s : settlement.getSurvivors()) {
                pw.println(s.getName() + "," + s.getHealth() + "," + s.getLevel() + "," + s.getRole());
            }

            pw.println("ITEMS");
            for (Item it : inventory.getItems()) {
                pw.println(it.getName() + "," + it.getType() + "," + it.getValue() + "," + it.getQuantity());
            }

            System.out.println("saved to " + filename);
        } catch (IOException e) {
            System.out.println("error saving file: " + e.getMessage());
        }
    }

    // load from a text file, returns false if the file doesnt exist or fails
    public static boolean load(Settlement settlement, Inventory inventory, String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String section = "";
            boolean nameRead = false;

            while ((line = br.readLine()) != null) {
                // section headers switch what we are reading
                if (line.equals("SETTLEMENT") || line.equals("SURVIVORS") || line.equals("ITEMS")) {
                    section = line;
                    continue;
                }

                if (section.equals("SETTLEMENT") && !nameRead) {
                    // settlement name was already set in the constructor so we just skip
                    nameRead = true;
                    continue;
                }

                if (section.equals("SURVIVORS")) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        Survivor s = new Survivor(parts[0]);
                        s.setHealth(Integer.parseInt(parts[1]));
                        s.setLevel(Integer.parseInt(parts[2]));
                        s.setRole(parts[3]);
                        settlement.addSurvivor(s);
                    }
                } else if (section.equals("ITEMS")) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        Item it = new Item(parts[0], parts[1],
                                Integer.parseInt(parts[2]),
                                Integer.parseInt(parts[3]));
                        inventory.addItem(it);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("error loading file: " + e.getMessage());
            return false;
        }
    }
}
