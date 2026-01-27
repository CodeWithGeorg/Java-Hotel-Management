package src;

import javax.swing.*;
import java.awt.*;

public class StatisticsUI extends JPanel {
    private final BookingService bookingService;
    private final RoomService roomService;
    private final CustomerService customerService;

    private JPanel totalBookingsLabel;
    private JPanel activeBookingsLabel;
    private JPanel totalRevenueLabel;
    private JPanel totalRoomsLabel;
    private JPanel totalCustomersLabel;
    private JPanel occupancyRateLabel;

    public StatisticsUI(BookingService bookingService, RoomService roomService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.roomService = roomService;
        this.customerService = customerService;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Hotel Statistics & Reports"));
        setBackground(new Color(245, 245, 250));

        // Title
        JLabel title = new JLabel("Hotel Statistics Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0, 102, 204));
        add(title, BorderLayout.NORTH);

        // Statistics panel
        JPanel statsPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        statsPanel.setBackground(new Color(245, 245, 250));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        totalBookingsLabel = createStatLabel("Total Bookings", "0");
        activeBookingsLabel = createStatLabel("Active Bookings", "0");
        totalRevenueLabel = createStatLabel("Total Revenue", "$0.00");
        totalRoomsLabel = createStatLabel("Total Rooms", "0");
        totalCustomersLabel = createStatLabel("Total Customers", "0");
        occupancyRateLabel = createStatLabel("Occupancy Rate", "0%");

        statsPanel.add(totalBookingsLabel);
        statsPanel.add(activeBookingsLabel);
        statsPanel.add(totalRevenueLabel);
        statsPanel.add(totalRoomsLabel);
        statsPanel.add(totalCustomersLabel);
        statsPanel.add(occupancyRateLabel);

        add(statsPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.setBackground(new Color(245, 245, 250));

        JButton refreshButton = createButton("Refresh Statistics", new Color(60, 179, 113));
        JButton viewBookingsButton = createButton("View All Bookings", new Color(30, 144, 255));
        JButton generateReportButton = createButton("Generate Report", new Color(255, 165, 0));

        refreshButton.addActionListener(e -> refreshStatistics());
        viewBookingsButton.addActionListener(e -> bookingService.viewBookings());
        generateReportButton.addActionListener(e -> generateReport());

        buttonsPanel.add(refreshButton);
        buttonsPanel.add(viewBookingsButton);
        buttonsPanel.add(generateReportButton);

        add(buttonsPanel, BorderLayout.SOUTH);

        // Initial refresh
        refreshStatistics();
    }

    private JPanel createStatLabel(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(new Color(0, 102, 204));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 35));
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public void refreshStatistics() {
        int totalBookings = bookingService.bookings.size();
        int activeBookings = bookingService.getActiveBookingsCount();
        double totalRevenue = bookingService.getTotalRevenue();
        int totalRooms = roomService.rooms.size();
        int totalCustomers = customerService.customers.size();

        double occupancyRate = totalRooms > 0 ? (double) activeBookings / totalRooms * 100 : 0;

        totalBookingsLabel.getComponent(1).setVisible(false);
        totalBookingsLabel.removeAll();
        totalBookingsLabel.add(createStatValuePanel("Total Bookings", String.valueOf(totalBookings)),
                BorderLayout.CENTER);

        activeBookingsLabel.getComponent(1).setVisible(false);
        activeBookingsLabel.removeAll();
        activeBookingsLabel.add(createStatValuePanel("Active Bookings", String.valueOf(activeBookings)),
                BorderLayout.CENTER);

        totalRevenueLabel.getComponent(1).setVisible(false);
        totalRevenueLabel.removeAll();
        totalRevenueLabel.add(createStatValuePanel("Total Revenue", String.format("$%.2f", totalRevenue)),
                BorderLayout.CENTER);

        totalRoomsLabel.getComponent(1).setVisible(false);
        totalRoomsLabel.removeAll();
        totalRoomsLabel.add(createStatValuePanel("Total Rooms", String.valueOf(totalRooms)), BorderLayout.CENTER);

        totalCustomersLabel.getComponent(1).setVisible(false);
        totalCustomersLabel.removeAll();
        totalCustomersLabel.add(createStatValuePanel("Total Customers", String.valueOf(totalCustomers)),
                BorderLayout.CENTER);

        occupancyRateLabel.getComponent(1).setVisible(false);
        occupancyRateLabel.removeAll();
        occupancyRateLabel.add(createStatValuePanel("Occupancy Rate", String.format("%.1f%%", occupancyRate)),
                BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private JPanel createStatValuePanel(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(new Color(0, 102, 204));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    private void generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=================================================\n");
        report.append("           HOTEL MANAGEMENT REPORT\n");
        report.append("=================================================\n\n");

        report.append("--- Room Statistics ---\n");
        report.append("Total Rooms: ").append(roomService.rooms.size()).append("\n");
        report.append("Available Rooms: ").append(roomService.rooms.size() - bookingService.getActiveBookingsCount())
                .append("\n\n");

        report.append("--- Customer Statistics ---\n");
        report.append("Total Customers: ").append(customerService.customers.size()).append("\n\n");

        report.append("--- Booking Statistics ---\n");
        report.append("Total Bookings: ").append(bookingService.bookings.size()).append("\n");
        report.append("Active Bookings: ").append(bookingService.getActiveBookingsCount()).append("\n");
        report.append("Total Revenue: $").append(String.format("%.2f", bookingService.getTotalRevenue()))
                .append("\n\n");

        report.append("--- Booking Details ---\n");
        if (!bookingService.bookings.isEmpty()) {
            for (Booking b : bookingService.bookings) {
                report.append(String.format("ID: %s | %s | Room: %s | %s to %s | $%.2f | %s\n",
                        b.id, b.customerName, b.roomNumber, b.checkInDate, b.checkOutDate, b.totalAmount, b.status));
            }
        } else {
            report.append("No bookings available.\n");
        }

        report.append("\n=================================================\n");
        report.append("Report generated successfully.\n");

        JTextArea textArea = new JTextArea(report.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(null, scrollPane, "Hotel Report", JOptionPane.INFORMATION_MESSAGE);
    }
}
