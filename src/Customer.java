package src;

public class Customer {
    // Unique customer identifier
    public String id;
    // Customer full name
    public String name;
    // Customer phone number
    public String phone;
    // Room number assigned to customer
    public String roomNumber;

    // Constructor to initialize customer details
    public Customer(String id, String name, String phone, String roomNumber) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.roomNumber = roomNumber;
    }

}
