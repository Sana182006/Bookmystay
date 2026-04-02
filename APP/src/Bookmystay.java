import java.util.*;

// Reservation (Confirmed Booking)
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType);
    }
}

// Booking History (List - preserves order)
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Display all bookings
    public void showAllBookings() {
        System.out.println("=== Booking History ===");

        List<Reservation> list = history.getAllReservations();

        if (list.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : list) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary() {
        System.out.println("\n=== Booking Summary ===");

        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : history.getAllReservations()) {
            String type = r.getRoomType();
            summary.put(type, summary.getOrDefault(type, 0) + 1);
        }

        for (Map.Entry<String, Integer> e : summary.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// Main Class
public class Bookmystay {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        // Simulating confirmed bookings
        history.addReservation(new Reservation("RES-1", "Alice", "Single Room"));
        history.addReservation(new Reservation("RES-2", "Bob", "Double Room"));
        history.addReservation(new Reservation("RES-3", "Charlie", "Single Room"));
        history.addReservation(new Reservation("RES-4", "David", "Suite Room"));

        BookingReportService reportService = new BookingReportService(history);

        reportService.showAllBookings();

        reportService.generateSummary();
    }
}