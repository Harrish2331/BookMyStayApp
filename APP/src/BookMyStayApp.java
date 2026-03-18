/**
 * BookMyStayApp
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates how booking requests are stored in a queue
 * to preserve arrival order without updating inventory.
 *
 * @author Dhanussz
 * @version 5.0
 */

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.Queue;

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
        return new HashMap<>(inventory);
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

    public String getGuestName() {
        return guestName;
    }

    public String getRequestedRoomType() {
        return requestedRoomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + requestedRoomType);
    }
}

// Booking Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add a reservation request
    public void addRequest(Reservation res) {
        queue.add(res);
        System.out.println("Booking request added: " + res.getGuestName() + " -> " + res.getRequestedRoomType());
    }

    // Peek next request without removing
    public Reservation peekNext() {
        return queue.peek();
    }

    // Process next request (removes from queue)
    public Reservation processNext() {
        return queue.poll();
    }

    // Display all pending requests
    public void displayQueue() {
        System.out.println("\n===== Booking Requests Queue =====");
        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        for (Reservation r : queue) {
            r.displayReservation();
        }
    }

    // Check if queue is empty
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// MAIN CLASS
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App (Version 5.0) =====");

        // Initialize rooms
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate adding booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingQueue.addRequest(new Reservation("David", "Single Room"));

        // Display queue
        bookingQueue.displayQueue();

        // Peek next request
        Reservation next = bookingQueue.peekNext();
        if (next != null) {
            System.out.println("\nNext request to process (peek):");
            next.displayReservation();
        }

        // Process requests in order (without changing inventory)
        System.out.println("\nProcessing requests in arrival order:");
        while (!bookingQueue.isEmpty()) {
            Reservation res = bookingQueue.processNext();
            System.out.println("Processing: ");
            res.displayReservation();
        }

        // Show that queue is empty
        bookingQueue.displayQueue();

        // Inventory remains unchanged
        System.out.println("\nInventory remains unchanged:");
        for (Map.Entry<String, Integer> entry : inventory.getAllInventory().entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}