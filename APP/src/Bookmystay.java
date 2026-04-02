import java.util.*;

// Add-On Service class
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, Service service) {
        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added " + service.getServiceName()
                + " to Reservation " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {
        System.out.println("\n=== Services for Reservation " + reservationId + " ===");

        List<Service> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (Service s : services) {
            System.out.println(s.getServiceName() + " - Rs." + s.getCost());
        }
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;

        List<Service> services = serviceMap.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// Main Class
public class Bookmystay {
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES-101";

        // Adding services
        manager.addService(reservationId, new Service("Breakfast", 500));
        manager.addService(reservationId, new Service("Airport Pickup", 1000));
        manager.addService(reservationId, new Service("Extra Bed", 800));

        // Display services
        manager.displayServices(reservationId);

        // Calculate total cost
        double total = manager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Add-On Cost: Rs." + total);
    }
}