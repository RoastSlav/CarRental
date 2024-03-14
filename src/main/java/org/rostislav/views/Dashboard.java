package org.rostislav.views;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardsPanel = new JPanel(cardLayout);

    public Dashboard() {
        initUI();
        initNavigation();
    }

    private void initUI() {
        setTitle("Car Rental System Dashboard");
        setSize(800, 600);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Welcome label
        JLabel welcomeLabel = new JLabel("<html><h1>Welcome to the Car Rental System</h1></html>", SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        // Initialize cards panel with CardLayout for different sections
        cardsPanel.setLayout(new CardLayout());

        // Combined placeholders for sections
        cardsPanel.add(createPlaceholderPanel("Welcome to the Car Rental System Dashboard. Please select a section."), "WELCOME");
        cardsPanel.add(createPlaceholderPanel("Manage Users"), "USERS");
        cardsPanel.add(createPlaceholderPanel("Manage Cars & Rentals"), "CARS_RENTALS");
        cardsPanel.add(createPlaceholderPanel("Manage Locations"), "LOCATIONS");
        cardsPanel.add(createPlaceholderPanel("Manage Rates"), "RATES");
        cardsPanel.add(createPlaceholderPanel("Manage Payments"), "PAYMENTS");
        cardsPanel.add(createPlaceholderPanel("Manage Roles"), "ROLES");

        add(cardsPanel, BorderLayout.CENTER);
    }

    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        panel.add(label);
        return panel;
    }

    private void initNavigation() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10)); // Adjust as needed for the number of buttons

        // Initialize and add buttons for navigation
        buttonPanel.add(createNavButton("Users", "USERS"));
        buttonPanel.add(createNavButton("Cars & Rentals", "CARS_RENTALS"));
        buttonPanel.add(createNavButton("Locations", "LOCATIONS"));
        buttonPanel.add(createNavButton("Rates", "RATES"));
        buttonPanel.add(createNavButton("Payments", "PAYMENTS"));
        buttonPanel.add(createNavButton("Roles", "ROLES"));

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createNavButton(String label, String card) {
        JButton button = new JButton(label);
        button.addActionListener(e -> ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, card));
        return button;
    }
}
