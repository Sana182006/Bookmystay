import java.util.*;

// Reservation
class Reservation {
    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\n=== Current Inventory ===");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// Booking History (confirmed bookings)
class BookingHistory {
    private Map<String, Reservation> bookings;

    public BookingHistory() {
        bookings = new HashMap<>();
    }

    public void addBooking(Reservation r) {
        bookings.put(r.getReservationId(), r);
    }

    public Reservation getBooking(String id) {
        return bookings.get(id);
    }

    public void removeBooking(String id) {
        bookings.remove(id);
    }
}

// Cancellation Service
class CancellationService {

    private BookingHistory history;
    private RoomInventory inventory;
    private Stack<String> rollbackStack;

    public CancellationService(BookingHistory history, RoomInventory inventory) {
        this.history = history;
        this.inventory = inventory;
        this.rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId) {

        System.out.println("\nProcessing cancellation for: " + reservationId);

        Reservation r = history.getBooking(reservationId);

        // Validation
        if (r == null) {
            System.out.println("Cancellation Failed: Reservation not found");
            return;
        }

        // Push room ID into rollback stack
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.incrementRoom(r.getRoomType());

        // Remove booking
        history.removeBooking(reservationId);

        System.out.println("Cancellation Successful for " + reservationId);
    }

    public void displayRollbackStack() {
        System.out.println("\n=== Rollback Stack ===");
        System.out.println(rollbackStack);
    }
}

// Main Class
public class Bookmystay {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        RoomInventory inventory = new RoomInventory();

        // Simulate confirmed bookings
        history.addBooking(new Reservation("RES-1", "Single Room"));
        history.addBooking(new Reservation("RES-2", "Double Room"));

        CancellationService service = new CancellationService(history, inventory);

        // Valid cancellation
        service.cancelBooking("RES-1");

        // Invalid cancellation
        service.cancelBooking("RES-3");

        service.displayRollbackStack();
        inventory.displayInventory();
    }
}