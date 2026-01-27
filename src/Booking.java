package src;

public class Booking {
    public String id;
    public String customerId;
    public String roomId;
    public String customerName;
    public String roomNumber;
    public String checkInDate;
    public String checkOutDate;
    public double totalAmount;
    public String status; // "Confirmed", "Checked-in", "Completed", "Cancelled"

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
