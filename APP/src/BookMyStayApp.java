import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return guestName + " - " + roomType;
    }
}

class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation r) {
        confirmedReservations.add(r);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("Shyam", "Deluxe"));
        history.addReservation(new Reservation("Gokul", "Standard"));

        System.out.println("Booking History:");
        for (Reservation r : history.getConfirmedReservations()) {
            System.out.println(r);
        }
    }
}