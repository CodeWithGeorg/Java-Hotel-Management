package src;

import javax.swing.*;
import java.util.ArrayList;

public class CustomerService {
    ArrayList<Customer> customers = new ArrayList<>();

    // Method to check in a new customer
    public void checkIn() {
        String name = JOptionPane.showInputDialog("Customer name:");
        String phone = JOptionPane.showInputDialog("Phone:");
        String room = JOptionPane.showInputDialog("Room number:");

        String id = "C" + (customers.size() + 1);
        customers.add(new Customer(id, name, phone, room));
        JOptionPane.showMessageDialog(null, "Customer checked-in!");
    }

    // Method to view all customers
    public void viewCustomers() {
        StringBuilder sb = new StringBuilder("=== CUSTOMERS ===\n");
        for (Customer c : customers) {
            sb.append(c.id).append(" | ").append(c.name)
                    .append(" | ").append(c.phone)
                    .append(" | ").append(c.roomNumber).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // Method to remove a customer by ID
    public void removeCustomer(String id) {
        customers.removeIf(c -> c.id.equals(id));
    }

    public void updateCustomer(String id) {
        for (Customer c : customers) {
            if (c.id.equals(id)) {
                String newName = JOptionPane.showInputDialog("New Name:", c.name);
                String newPhone = JOptionPane.showInputDialog("New Phone:", c.phone);
                String newRoom = JOptionPane.showInputDialog("New Room Number:", c.roomNumber);

                c.name = newName;
                c.phone = newPhone;
                c.roomNumber = newRoom;
                JOptionPane.showMessageDialog(null, "Customer updated!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Customer ID not found!");
    }

}
