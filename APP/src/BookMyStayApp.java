import java.util.*;

class Reservation {
    String name;

    public Reservation(String name) {
        this.name = name;
    }
}

class RoomInventory {
    int rooms = 1;

    public void releaseRoom() {
        rooms++;
    }
}

class CancellationService {
    Stack<String> stack = new Stack<>();

    public void cancel(Reservation r, RoomInventory inventory) {
        inventory.releaseRoom();
        stack.push(r.name);
        System.out.println("Cancelled booking for " + r.name);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        Reservation r = new Reservation("Shyam");

        CancellationService cs = new CancellationService();
        cs.cancel(r, inventory);
    }
}