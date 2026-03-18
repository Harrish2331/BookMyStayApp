import java.io.*;

class RoomInventory {
    int rooms = 5;
}

class FilePersistenceService {

    public void save(RoomInventory inv, String file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("rooms=" + inv.rooms);
        } catch (Exception e) {
            System.out.println("Error saving");
        }
    }

    public void load(RoomInventory inv, String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            inv.rooms = Integer.parseInt(line.split("=")[1]);
        } catch (Exception e) {
            System.out.println("Error loading");
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inv = new RoomInventory();
        FilePersistenceService fps = new FilePersistenceService();

        fps.save(inv, "data.txt");
        inv.rooms = 0;

        fps.load(inv, "data.txt");
        System.out.println("Rooms after recovery: " + inv.rooms);
    }
}