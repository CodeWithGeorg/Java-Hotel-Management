package src;

public class Booking {
    // Unique booking identifier
    public String id;
    // ID of the customer who made the booking
    public String customerId;
    // ID of the room being booked
    public String roomId;
    // Name of the customer
    public String customerName;
    // Room number for the booking
    public String roomNumber;
    // Check-in date
    public String checkInDate;
    // Check-out date
    public String checkOutDate;
    // Total amount for the booking
    public double totalAmount;
    // Status of the booking: "Confirmed", "Checked-in", "Completed", "Cancelled"
    public String status;

    // Constructor to initialize booking details
    public Booking(String id, String customerId, String roomId, String customerName,
            String roomNumber, String checkInDate, String checkOutDate,
            double totalAmount, String status) {
        this.id = id;
        this.customerId = customerId;
        this.roomId = roomId;
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }
}
