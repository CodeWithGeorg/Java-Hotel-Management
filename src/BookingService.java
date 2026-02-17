package src;

import javax.swing.*;
import java.util.ArrayList;

public class BookingService {
    ArrayList<Booking> bookings = new ArrayList<>();
    private final RoomService roomService;
    private final CustomerService customerService;

    // Constructor
    public BookingService(RoomService roomService, CustomerService customerService) {
        this.roomService = roomService;
        this.customerService = customerService;
    }

    public void createBooking() {
        String customerId = JOptionPane.showInputDialog("Customer ID:");
        String roomId = JOptionPane.showInputDialog("Room ID:");
        String checkInDate = JOptionPane.showInputDialog("Check-in Date (YYYY-MM-DD):");
        String checkOutDate = JOptionPane.showInputDialog("Check-out Date (YYYY-MM-DD):");

        // Find customer and room details
        Customer customer = null;
        Room room = null;

        for (Customer c : customerService.customers) {
            if (c.id.equals(customerId)) {
                customer = c;
                break;
            }
        }

        for (Room r : roomService.rooms) {
            if (r.id.equals(roomId)) {
                room = r;
                break;
            }
        }

        if (customer == null) {
            JOptionPane.showMessageDialog(null, "Customer not found!");
            return;
        }

        if (room == null) {
            JOptionPane.showMessageDialog(null, "Room not found!");
            return;
        }

        // Calculate total amount (placeholder: $100 per night)
        double nights = 3; // Default, in real app calculate from dates
        double totalAmount = room.price * nights;

        String id = "B" + (bookings.size() + 1);
        Booking booking = new Booking(id, customerId, roomId, customer.name,
                room.roomNumber, checkInDate, checkOutDate,
                totalAmount, "Confirmed");
        bookings.add(booking);
        JOptionPane.showMessageDialog(null, "Booking created! Total: $" + totalAmount);
    }

    public void viewBookings() {
        if (bookings.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No bookings found.");
            return;
        }

        StringBuilder sb = new StringBuilder("=== BOOKINGS ===\n");
        sb.append(String.format("%-8s %-12s %-10s %-15s %-10s %-12s %-12s %-10s\n",
                "ID", "Customer", "Room", "Check-in", "Check-out", "Amount", "Status"));
        sb.append("-".repeat(90)).append("\n");

        for (Booking b : bookings) {
            sb.append(String.format("%-8s %-12s %-10s %-15s %-10s $%-9.2f %-10s\n",
                    b.id, b.customerName, b.roomNumber, b.checkInDate,
                    b.checkOutDate, b.totalAmount, b.status));
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void cancelBooking(String id) {
        for (Booking b : bookings) {
            if (b.id.equals(id)) {
                b.status = "Cancelled";
                JOptionPane.showMessageDialog(null, "Booking cancelled!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Booking ID not found!");
    }

    public void updateBookingStatus(String id, String newStatus) {
        for (Booking b : bookings) {
            if (b.id.equals(id)) {
                b.status = newStatus;
                JOptionPane.showMessageDialog(null, "Booking status updated to: " + newStatus);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Booking ID not found!");
    }

    public double getTotalRevenue() {
        double total = 0;
        for (Booking b : bookings) {
            if (b.status.equals("Completed") || b.status.equals("Checked-in")) {
                total += b.totalAmount;
            }
        }
        return total;
    }

    public int getActiveBookingsCount() {
        int count = 0;
        for (Booking b : bookings) {
            if (b.status.equals("Confirmed") || b.status.equals("Checked-in")) {
                count++;
            }
        }
        return count;
    }
}
