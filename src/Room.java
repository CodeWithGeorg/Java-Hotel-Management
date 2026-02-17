package src;

public class Room {
    // Unique room identifier
    public String id;
    // Room number
    public String roomNumber;
    // Type of room (single, double, suite, etc.)
    public String type;
    // Price per night
    public double price;

    // Constructor to initialize room details
    public Room(String id, String roomNumber, String type, double price) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
    }

}
