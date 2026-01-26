package src;

import javax.swing.*;
import java.util.ArrayList;

public class RoomService {
    ArrayList<Room> rooms = new ArrayList<>();

    public void addRoom() {
        String no = JOptionPane.showInputDialog("Room number:");
        String type = JOptionPane.showInputDialog("Type:");
        double price = Double.parseDouble(JOptionPane.showInputDialog("Price:"));

        String id = "R" + (rooms.size() + 1);
        rooms.add(new Room(id, no, type, price));
        JOptionPane.showMessageDialog(null, "Room added!");
    }

    public void viewRooms() {
        StringBuilder sb = new StringBuilder("=== ROOMS ===\n");
        for(Room r : rooms){
            sb.append(r.id).append(" | ").append(r.roomNumber)
              .append(" | ").append(r.type)
              .append(" | ").append(r.price).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

 public void deleteRoom(String id){
    rooms.removeIf(r -> r.id.equals(id));
}

public void updateRoom(String id) {
    for (Room r : rooms) {
        if (r.id.equals(id)) {
            String newNumber = JOptionPane.showInputDialog("New Room number:", r.roomNumber);
            String newType = JOptionPane.showInputDialog("New Type:", r.type);
            double newPrice = Double.parseDouble(JOptionPane.showInputDialog("New Price:", r.price));

            r.roomNumber = newNumber;
            r.type = newType;
            r.price = newPrice;
            JOptionPane.showMessageDialog(null, "Room updated!");
            return;
        }
    }
    JOptionPane.showMessageDialog(null, "Room ID not found!");
}

}
