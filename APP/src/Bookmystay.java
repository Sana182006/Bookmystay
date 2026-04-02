import java.io.*;
import java.util.*;

// Reservation (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public void display() {
        System.out.println("ID: " + reservationId + ", Room: " + roomType);
    }
}

// Inventory (Serializable)
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void display() {
        System.out.println("Inventory: " + inventory);
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "hotel_data.ser";

    // Save data
    public static void saveData(List<Reservation> bookings, RoomInventory inventory) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(bookings);
            out.writeObject(inventory);

            System.out.println("Data saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    // Load data
    public static Object[] loadData() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            List<Reservation> bookings = (List<Reservation>) in.readObject();
            RoomInventory inventory = (RoomInventory) in.readObject();

            System.out.println("Data loaded successfully.");
            return new Object[]{bookings, inventory};

        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return null;
        }
    }
}

// Main Class
public class Bookmystay {
    public static void main(String[] args) {

        List<Reservation> bookings;
        RoomInventory inventory;

        // Try loading existing data
        Object[] data = PersistenceService.loadData();

        if (data != null) {
            bookings = (List<Reservation>) data[0];
            inventory = (RoomInventory) data[1];
        } else {
            bookings = new ArrayList<>();
            inventory = new RoomInventory();

            // Add sample data
            bookings.add(new Reservation("RES-1", "Single Room"));
            bookings.add(new Reservation("RES-2", "Double Room"));
        }

        // Display current state
        System.out.println("\n=== Current Bookings ===");
        for (Reservation r : bookings) {
            r.display();
        }

        inventory.display();

        // Save before exit (simulate shutdown)
        PersistenceService.saveData(bookings, inventory);
    }
}
