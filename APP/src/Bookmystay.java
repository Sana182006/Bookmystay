import java.util.*;

// Reservation (same as UC5)
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
}

// Inventory Service
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, inventory.get(roomType) - 1);
        }
    }

    public void displayInventory() {
        System.out.println("\n=== Updated Inventory ===");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// Booking Service (Core Logic)
class BookingService {

    private Queue<Reservation> queue;
    private RoomInventory inventory;

    private Set<String> allocatedRoomIds;
    private HashMap<String, Set<String>> roomAllocations;

    public BookingService(Queue<Reservation> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocations = new HashMap<>();
    }

    // Generate Unique Room ID
    private String generateRoomId(String roomType, int count) {
        return roomType.substring(0, 2).toUpperCase() + "-" + count;
    }

    // Process Booking Requests
    public void processBookings() {
        System.out.println("=== Processing Bookings ===\n");

        int counter = 1;

        while (!queue.isEmpty()) {
            Reservation r = queue.poll();
            String type = r.getRoomType();

            System.out.println("Processing: " + r.getGuestName());

            if (inventory.getAvailability(type) > 0) {

                String roomId = generateRoomId(type, counter++);

                // Ensure uniqueness
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    roomAllocations
                            .computeIfAbsent(type, k -> new HashSet<>())
                            .add(roomId);

                    inventory.decrementRoom(type);

                    System.out.println("Booking Confirmed for " + r.getGuestName()
                            + " | Room ID: " + roomId);
                }

            } else {
                System.out.println("Booking Failed for " + r.getGuestName()
                        + " (No rooms available)");
            }

            System.out.println();
        }
    }

    public void displayAllocations() {
        System.out.println("\n=== Room Allocations ===");

        for (Map.Entry<String, Set<String>> entry : roomAllocations.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Main Class
public class Bookmystay {
    public static void main(String[] args) {

        Queue<Reservation> queue = new LinkedList<>();

        queue.offer(new Reservation("Alice", "Single Room"));
        queue.offer(new Reservation("Bob", "Single Room"));
        queue.offer(new Reservation("Charlie", "Single Room"));
        queue.offer(new Reservation("David", "Suite Room"));

        RoomInventory inventory = new RoomInventory();

        BookingService service = new BookingService(queue, inventory);

        service.processBookings();

        service.displayAllocations();

        inventory.displayInventory();
    }
}