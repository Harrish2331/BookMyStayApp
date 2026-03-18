class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    int rooms = 1;

    public void bookRoom() throws InvalidBookingException {
        if (rooms <= 0) {
            throw new InvalidBookingException("No rooms available");
        }
        rooms--;
    }
}

class ReservationValidator {
    public void validate(String name) throws InvalidBookingException {
        if (name == null || name.isEmpty()) {
            throw new InvalidBookingException("Invalid name");
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();

        try {
            validator.validate("Shyam");
            inventory.bookRoom();
            System.out.println("Booking Successful");
        } catch (InvalidBookingException e) {
            System.out.println(e.getMessage());
        }
    }
}