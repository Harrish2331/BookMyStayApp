/**
 * BookMyStayApp
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Processes queued booking requests, assigns unique room IDs,
 * prevents double booking, and updates inventory.
 *
 * @author Dhanussz
 * @version 6.0
 */

import java.util.*;

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
    public SingleRoom() { super("Single Room", 1, 2000); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 2, 3500); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 3, 6000); }
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

    public boolean decrementAvailability(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\n===== Current Inventory =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}

// Reservation class
class Reservation {
    private String guestName;
    private String requestedRoomType;

    public Reservation(String guestName, String requestedRoomType) {
        this.guestName = guestName;
        this.requestedRoomType = requestedRoomType;
    }

    public String getGuestName() { return guestName; }
    public String getRequestedRoomType() { return requestedRoomType; }
}

// Booking Request Queue
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() { queue = new LinkedList<>(); }

    public void addRequest(Reservation res) {
        queue.add(res);
        System.out.println("Booking request added: " + res.getGuestName() + " -> " + res.getRequestedRoomType());
    }

    public Reservation pollNext() { return queue.poll(); }
    public boolean isEmpty() { return queue.isEmpty(); }
}

// Booking Service: confirms bookings and allocates rooms
class BookingService {
    private RoomInventory inventory;
    private HashMap<String, Set<String>> allocatedRooms; // roomType -> set of room IDs
    private int roomIdCounter;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
        this.roomIdCounter = 100; // starting room ID
    }

    public void processBooking(Reservation res) {
        String type = res.getRequestedRoomType();
        if (inventory.decrementAvailability(type)) {
            String roomId = generateRoomId();
            allocatedRooms.putIfAbsent(type, new HashSet<>());
            allocatedRooms.get(type).add(roomId);
            System.out.println("Reservation confirmed for " + res.getGuestName() +
                    " | Room Type: " + type + " | Room ID: " + roomId);
        } else {
            System.out.println("Sorry " + res.getGuestName() + ", no " + type + " available.");
        }
    }

    private String generateRoomId() {
        return "R" + (roomIdCounter++);
    }

    public void displayAllocatedRooms() {
        System.out.println("\n===== Allocated Rooms =====");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// MAIN CLASS
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("===== Book My Stay App (Version 6.0) =====");

        // Initialize rooms
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize booking queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Sample booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));
        queue.addRequest(new Reservation("David", "Single Room"));
        queue.addRequest(new Reservation("Eva", "Suite Room")); // may fail if inventory < 2

        // Booking service
        BookingService bookingService = new BookingService(inventory);

        // Process bookings in FIFO order
        while (!queue.isEmpty()) {
            Reservation res = queue.pollNext();
            bookingService.processBooking(res);
        }

        // Show allocated rooms
        bookingService.displayAllocatedRooms();

        // Show remaining inventory
        inventory.displayInventory();
    }
}