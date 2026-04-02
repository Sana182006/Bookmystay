import java.util.*;

// Reservation class (Booking Request)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + ", Requested Room: " + roomType);
    }
}

// Booking Request Queue (FIFO)
class BookingQueue {
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests (without removing)
    public void displayQueue() {
        System.out.println("\n=== Booking Request Queue ===");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.display();
        }
    }
}

// Main Class
public class Bookmystay {
    public static void main(String[] args) {

        BookingQueue bookingQueue = new BookingQueue();

        // Simulating booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        bookingQueue.displayQueue();
    }
}