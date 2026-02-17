package src;

public class Room {
    public String id;
    public String roomNumber;
    public String type;
    public double price;

    // Constructor to initialize room details
    public Room(String id, String roomNumber, String type, double price) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
    }

}
