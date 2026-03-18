/**
 * BookMyStayApp
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Demonstrates abstraction, inheritance, polymorphism,
 * and static availability using a single Java file.
 *
 * @author Dhanussz
 * @version 2.0
 */

// Abstract class
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
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

// MAIN CLASS (only public class)
public class BookMyStayApp {

    public static void main(String[] args) {

        // Create room objects (Polymorphism)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("===== Book My Stay App (Version 2.0) =====");

        System.out.println("\n--- Single Room ---");
        singleRoom.displayDetails();
        System.out.println("Available Rooms: " + singleAvailable);

        System.out.println("\n--- Double Room ---");
        doubleRoom.displayDetails();
        System.out.println("Available Rooms: " + doubleAvailable);

        System.out.println("\n--- Suite Room ---");
        suiteRoom.displayDetails();
        System.out.println("Available Rooms: " + suiteAvailable);
    }
}