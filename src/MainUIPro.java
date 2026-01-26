package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainUIPro {
    private final RoomService roomService = new RoomService();
    private final CustomerService customerService = new CustomerService();
    private final JFrame frame;
    private final JTable roomTable;
    private final JTable customerTable;
    private final DefaultTableModel roomModel;
    private final DefaultTableModel customerModel;

    // Room input fields
    private JTextField roomNumberField;
    private JTextField roomTypeField;
    private JTextField roomPriceField;

    // Customer input fields
    private JTextField customerNameField;
    private JTextField customerPhoneField;
    private JTextField customerRoomField;

    // Buttons for dynamic text
    private JButton addRoomButton;
    private JButton addCustomerButton;

    // Update mode flags
    @SuppressWarnings("unused")
    private boolean isUpdatingRoom = false;
    @SuppressWarnings("unused")
    private String updatingRoomId = null;
    @SuppressWarnings("unused")
    private boolean isUpdatingCustomer = false;
    @SuppressWarnings("unused")
    private String updatingCustomerId = null;

    public MainUIPro() {
        // Set system look and feel for a more professional appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("Hotel Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Hotel Management System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        title.setForeground(new Color(0, 102, 204)); // Blue color for title
        frame.add(title, BorderLayout.NORTH);

        // --- Room Panel ---
        JPanel roomPanel = new JPanel(new BorderLayout(5, 5));
        roomPanel.setBorder(BorderFactory.createTitledBorder("Rooms"));
        roomPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        roomModel = new DefaultTableModel(new Object[] { "ID", "Number", "Type", "Price" }, 0);
        roomTable = new JTable(roomModel);
        roomTable.setFillsViewportHeight(true);
        roomTable.getTableHeader().setReorderingAllowed(false);
        roomPanel.add(new JScrollPane(roomTable), BorderLayout.CENTER);

        // Room form panel for adding
        JPanel roomFormPanel = createRoomFormPanel();
        roomFormPanel.setBorder(BorderFactory.createTitledBorder("Add New Room"));
        roomFormPanel.setBackground(new Color(224, 255, 255)); // Light cyan

        // Room buttons panel
        JPanel roomButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton deleteRoom = createSmallButton("Delete Room", new Color(255, 99, 71)); // Tomato red
        JButton updateRoom = createSmallButton("Update Room", new Color(255, 165, 0)); // Orange
        JButton refreshRooms = createSmallButton("Refresh", new Color(60, 179, 113)); // Green
        roomButtonsPanel.add(deleteRoom);
        roomButtonsPanel.add(updateRoom);
        roomButtonsPanel.add(refreshRooms);
        roomButtonsPanel.setBackground(new Color(240, 248, 255)); // Match room panel

        // South panel for room: form and buttons
        JPanel roomSouthPanel = new JPanel(new BorderLayout(5, 5));
        roomSouthPanel.add(roomFormPanel, BorderLayout.NORTH);
        roomSouthPanel.add(roomButtonsPanel, BorderLayout.SOUTH);
        roomPanel.add(roomSouthPanel, BorderLayout.SOUTH);

        deleteRoom.addActionListener(e -> {
            int selected = roomTable.getSelectedRow();
            if (selected >= 0) {
                String id = roomModel.getValueAt(selected, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(frame, "Delete room " + id + "?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    roomService.deleteRoom(id);
                    refreshRoomTable();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a room first.", "Selection Required",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        updateRoom.addActionListener(e -> {
            int selected = roomTable.getSelectedRow();
            if (selected >= 0) {
                String id = roomModel.getValueAt(selected, 0).toString();
                Room room = roomService.rooms.stream().filter(r -> r.id.equals(id)).findFirst().orElse(null);
                if (room != null) {
                    roomNumberField.setText(room.roomNumber);
                    roomTypeField.setText(room.type);
                    roomPriceField.setText(String.valueOf(room.price));
                    addRoomButton.setText("Update Room");
                    isUpdatingRoom = true;
                    updatingRoomId = id;
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a room first.", "Selection Required",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        refreshRooms.addActionListener(e -> refreshRoomTable());

        // --- Customer Panel ---
        JPanel customerPanel = new JPanel(new BorderLayout(5, 5));
        customerPanel.setBorder(BorderFactory.createTitledBorder("Customers"));
        customerPanel.setBackground(new Color(245, 255, 250)); // Light mint green

        customerModel = new DefaultTableModel(new Object[] { "ID", "Name", "Phone", "Room" }, 0);
        customerTable = new JTable(customerModel);
        customerTable.setFillsViewportHeight(true);
        customerTable.getTableHeader().setReorderingAllowed(false);
        customerPanel.add(new JScrollPane(customerTable), BorderLayout.CENTER);

        // Customer form panel for adding
        JPanel customerFormPanel = createCustomerFormPanel();
        customerFormPanel.setBorder(BorderFactory.createTitledBorder("Check-in New Customer"));
        customerFormPanel.setBackground(new Color(240, 255, 240)); // Light honeydew

        // Customer buttons panel
        JPanel customerButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton removeCustomer = createSmallButton("Remove Customer", new Color(255, 99, 71)); // Tomato red
        JButton updateCustomer = createSmallButton("Update Customer", new Color(255, 165, 0)); // Orange
        JButton refreshCustomers = createSmallButton("Refresh", new Color(60, 179, 113)); // Green
        customerButtonsPanel.add(removeCustomer);
        customerButtonsPanel.add(updateCustomer);
        customerButtonsPanel.add(refreshCustomers);
        customerButtonsPanel.setBackground(new Color(245, 255, 250)); // Match customer panel

        // South panel for customer: form and buttons
        JPanel customerSouthPanel = new JPanel(new BorderLayout(5, 5));
        customerSouthPanel.add(customerFormPanel, BorderLayout.NORTH);
        customerSouthPanel.add(customerButtonsPanel, BorderLayout.SOUTH);
        customerPanel.add(customerSouthPanel, BorderLayout.SOUTH);

        removeCustomer.addActionListener(e -> {
            int selected = customerTable.getSelectedRow();
            if (selected >= 0) {
                String id = customerModel.getValueAt(selected, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(frame, "Remove customer " + id + "?", "Confirm Removal",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    customerService.removeCustomer(id);
                    refreshCustomerTable();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a customer first.", "Selection Required",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        updateCustomer.addActionListener(e -> {
            int selected = customerTable.getSelectedRow();
            if (selected >= 0) {
                String id = customerModel.getValueAt(selected, 0).toString();
                Customer customer = customerService.customers.stream().filter(c -> c.id.equals(id)).findFirst()
                        .orElse(null);
                if (customer != null) {
                    customerNameField.setText(customer.name);
                    customerPhoneField.setText(customer.phone);
                    customerRoomField.setText(customer.roomNumber);
                    addCustomerButton.setText("Update Customer");
                    isUpdatingCustomer = true;
                    updatingCustomerId = id;
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a customer first.", "Selection Required",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        refreshCustomers.addActionListener(e -> refreshCustomerTable());

        // --- Split Pane ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, roomPanel, customerPanel);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.5);
        frame.add(splitPane, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        refreshRoomTable();
        refreshCustomerTable();
    }

    private JPanel createRoomFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel numberLabel = new JLabel("Room Number:");
        roomNumberField = new JTextField(10);
        JLabel typeLabel = new JLabel("Type:");
        roomTypeField = new JTextField(10);
        JLabel priceLabel = new JLabel("Price:");
        roomPriceField = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(numberLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(roomNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(typeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(roomTypeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(priceLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(roomPriceField, gbc);

        addRoomButton = createSmallButton("Add Room", new Color(30, 144, 255)); // Blue
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(addRoomButton, gbc);

        addRoomButton.addActionListener(e -> {
            try {
                String number = roomNumberField.getText().trim();
                String type = roomTypeField.getText().trim();
                double price = Double.parseDouble(roomPriceField.getText().trim());
                if (!number.isEmpty() && !type.isEmpty()) {
                    // Assuming Room class has constructor Room(id, number, type, price)
                    // and RoomService has addRoom(Room room)
                    String id = generateId(); // Placeholder for ID generation
                    Room newRoom = new Room(id, number, type, price);
                    roomService.rooms.add(newRoom); // Assuming rooms is a list
                    refreshRoomTable();
                    clearRoomFields();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid price format.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return formPanel;
    }

    private JPanel createCustomerFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Name:");
        customerNameField = new JTextField(10);
        JLabel phoneLabel = new JLabel("Phone:");
        customerPhoneField = new JTextField(10);
        JLabel roomLabel = new JLabel("Room Number:");
        customerRoomField = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(customerNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(phoneLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(customerPhoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(roomLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(customerRoomField, gbc);

        addCustomerButton = createSmallButton("Check-in", new Color(30, 144, 255)); // Blue
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(addCustomerButton, gbc);

        addCustomerButton.addActionListener(e -> {
            String name = customerNameField.getText().trim();
            String phone = customerPhoneField.getText().trim();
            String room = customerRoomField.getText().trim();
            if (!name.isEmpty() && !phone.isEmpty() && !room.isEmpty()) {
                if (isUpdatingCustomer) {
                    // Update existing customer
                    Customer customer = customerService.customers.stream().filter(c -> c.id.equals(updatingCustomerId))
                            .findFirst().orElse(null);
                    if (customer != null) {
                        customer.name = name;
                        customer.phone = phone;
                        customer.roomNumber = room;
                        refreshCustomerTable();
                        clearCustomerFields();
                        addCustomerButton.setText("Check-in");
                        isUpdatingCustomer = false;
                        updatingCustomerId = null;
                    }
                } else {
                    // Add new customer
                    String id = generateId(); // Placeholder for ID generation
                    Customer newCustomer = new Customer(id, name, phone, room);
                    customerService.customers.add(newCustomer); // Assuming customers is a list
                    refreshCustomerTable();
                    clearCustomerFields();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return formPanel;
    }

    private JButton createSmallButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 25));
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        return button;
    }

    private void clearRoomFields() {
        roomNumberField.setText("");
        roomTypeField.setText("");
        roomPriceField.setText("");
    }

    private void clearCustomerFields() {
        customerNameField.setText("");
        customerPhoneField.setText("");
        customerRoomField.setText("");
    }

    private String generateId() {
        // Placeholder: In real app, use UUID or database auto-increment
        return String.valueOf(System.currentTimeMillis());
    }

    private void refreshRoomTable() {
        roomModel.setRowCount(0);
        for (Room r : roomService.rooms) {
            roomModel.addRow(new Object[] { r.id, r.roomNumber, r.type, r.price });
        }
    }

    private void refreshCustomerTable() {
        customerModel.setRowCount(0);
        for (Customer c : customerService.customers) {
            customerModel.addRow(new Object[] { c.id, c.name, c.phone, c.roomNumber });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainUIPro::new);
    }
}