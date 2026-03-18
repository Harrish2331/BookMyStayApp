/**
 * BookMyStayApp
 *
 * Use Case 7: Add-On Service Selection
 *
 * Allows guests to select optional services for confirmed reservations
 * while keeping core booking and inventory logic unchanged.
 *
 * @author Dhanussz
 * @version 7.0
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
        System.out.println("Room Type: " + roomType + " | Beds: " + beds + " | Price: ₹" + price);
    }

    public String getRoomType() { return roomType; }
    public double getPrice() { return price; }
}

// Room types
class SingleRoom extends Room { public SingleRoom() { super("Single Room", 1, 2000); } }
class DoubleRoom extends Room { public DoubleRoom() { super("Double Room", 2, 3500); } }
class SuiteRoom extends Room { public SuiteRoom() { super("Suite Room", 3, 6000); } }

// Inventory Class
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) { return inventory.getOrDefault(roomType, 0); }
    public boolean decrementAvailability(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) { inventory.put(roomType, available - 1); return true; }
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
    private String roomId;

    public Reservation(String guestName, String requestedRoomType, String roomId) {
        this.guestName = guestName;
        this.requestedRoomType = requestedRoomType;
        this.roomId = roomId;
    }

    public String getGuestName() { return guestName; }
    public String getRequestedRoomType() { return requestedRoomType; }
    public String getRoomId() { return roomId; }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + requestedRoomType + " | Room ID: " + roomId);
    }
}

// Booking Request Queue
class BookingRequestQueue {
    private Queue<String[]> queue; // [guestName, roomType]

    public BookingRequestQueue() { queue = new LinkedList<>(); }

    public void addRequest(String guestName, String roomType) {
        queue.add(new String[]{guestName, roomType});
        System.out.println("Booking request added: " + guestName + " -> " + roomType);
    }

    public String[] pollNext() { return queue.poll(); }
    public boolean isEmpty() { return queue.isEmpty(); }
}

// Booking Service
class BookingService {
    private RoomInventory inventory;
    private HashMap<String, Set<String>> allocatedRooms;
    private int roomIdCounter;
    private List<Reservation> confirmedReservations;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
        this.roomIdCounter = 100;
        this.confirmedReservations = new ArrayList<>();
    }

    public Reservation processBooking(String guestName, String roomType) {
        if (inventory.decrementAvailability(roomType)) {
            String roomId = "R" + (roomIdCounter++);
            allocatedRooms.putIfAbsent(roomType, new HashSet<>());
            allocatedRooms.get(roomType).add(roomId);
            Reservation res = new Reservation(guestName, roomType, roomId);
            confirmedReservations.add(res);
            System.out.println("Reservation confirmed for " + guestName + " | Room Type: " + roomType + " | Room ID: " + roomId);
            return res;
        } else {
            System.out.println("Sorry " + guestName + ", no " + roomType + " available.");
            return null;
        }
    }

    public void displayAllocatedRooms() {
        System.out.println("\n===== Allocated Rooms =====");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public List<Reservation> getConfirmedReservations() { return confirmedReservations; }
}

// Add-On Service class
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name; this.cost = cost;
    }

    public String getName() { return name; }
    public double getCost() { return cost; }
}

// Add-On Service Manager
class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices; // roomId -> services

    public AddOnServiceManager() { reservationServices = new HashMap<>(); }

    public void addService(Reservation res, Service service) {
        reservationServices.putIfAbsent(res.getRoomId(), new ArrayList<>());
        reservationServices.get(res.getRoomId()).add(service);
        System.out.println("Added service '" + service.getName() + "' for reservation " + res.getRoomId());
    }

    public void displayServices(Reservation res) {
        System.out.println("\nServices for " + res.getGuestName() + " (" + res.getRoomId() + "):");
        List<Service> services = reservationServices.getOrDefault(res.getRoomId(), new ArrayList<>());
        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }
        double total = 0;
        for (Service s : services) {
            System.out.println("- " + s.getName() + " | Cost: ₹" + s.getCost());
            total += s.getCost();
        }
        System.out.println("Total Add-On Cost: ₹" + total);
    }
}

// MAIN CLASS
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("===== Book My Stay App (Version 7.0) =====");

        // Initialize inventory and booking service
        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        // Booking queue
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest("Alice", "Single Room");
        queue.addRequest("Bob", "Double Room");
        queue.addRequest("Charlie", "Suite Room");

        // Process bookings
        List<Reservation> confirmed = new ArrayList<>();
        while (!queue.isEmpty()) {
            String[] req = queue.pollNext();
            Reservation res = bookingService.processBooking(req[0], req[1]);
            if (res != null) confirmed.add(res);
        }

        // Add-On Services
        AddOnServiceManager serviceManager = new AddOnServiceManager();
        Service breakfast = new Service("Breakfast", 500);
        Service spa = new Service("Spa Package", 1500);

        // Assign services
        serviceManager.addService(confirmed.get(0), breakfast);
        serviceManager.addService(confirmed.get(0), spa);
        serviceManager.addService(confirmed.get(2), spa);

        // Display allocations and inventory
        bookingService.displayAllocatedRooms();
        inventory.displayInventory();

        // Display services per reservation
        for (Reservation res : confirmed) {
            serviceManager.displayServices(res);
        }
    }
}