/**
 * BookMyStayApp
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates read-only room search using centralized inventory.
 * Shows available room types with details without modifying system state.
 *
 * @author Dhanussz
 * @version 4.0
 */

import java.util.HashMap;
import java.util.Map;

// Abstract Room class
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per night: ₹" + price);
    }

    public String getRoomType() {
        return roomType;
    }
}

// Room types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

// Inventory Class
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public Map<String, Integer> getAllInventory() {
        return new HashMap<>(inventory); // return copy for safety
    }
}

// Search Service (read-only)
class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(Room[] rooms) {
        System.out.println("\n===== Available Rooms =====");
        boolean anyAvailable = false;
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
                anyAvailable = true;
            }
        }
        if (!anyAvailable) {
            System.out.println("No rooms are currently available.");
        }
    }
}

// MAIN CLASS
public class BookMyStayApp {

    public static void main(String[] args) {

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        Room[] allRooms = { single, doubleRoom, suite };

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        System.out.println("===== Book My Stay App (Version 4.0) =====");

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Perform read-only search
        searchService.searchAvailableRooms(allRooms);

        // Demonstrate inventory is not modified
        System.out.println("Inventory remains unchanged after search:");
        for (Map.Entry<String, Integer> entry : inventory.getAllInventory().entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}