package org.rostislav.views;

import org.rostislav.models.CarColor;
import org.rostislav.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    @Autowired
    private CarService carService;

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

        // Optionally, add a welcome label or other elements here

        // Prepare the cards panel with CardLayout
        cardsPanel.add(new JLabel("<html><h1>Welcome to the Car Rental System</h1></html>"), "WELCOME");
        // Placeholder panels for different sections
        cardsPanel.add(new JLabel("Users Management Section"), "USERS");
        cardsPanel.add(new JLabel("Cars Management Section"), "CARS");
        cardsPanel.add(new JLabel("Rentals Management Section"), "RENTALS");
        cardsPanel.add(new JLabel("Locations Management Section"), "LOCATIONS");
        // Add more cards for other sections as needed

        add(cardsPanel, BorderLayout.CENTER); // Add the cards panel to the frame
    }

    private void initNavigation() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10)); // Adjust as needed

        // Navigation buttons
        JButton usersButton = new JButton("Users");
        usersButton.addActionListener(e -> cardLayout.show(cardsPanel, "USERS"));

        JButton carsButton = new JButton("Cars");
        carsButton.addActionListener(e -> {
            CarColor colorById = carService.getColorById(1);
            cardLayout.show(cardsPanel, "CARS");
        });

        JButton rentalsButton = new JButton("Rentals");
        rentalsButton.addActionListener(e -> cardLayout.show(cardsPanel, "RENTALS"));

        JButton locationsButton = new JButton("Locations");
        locationsButton.addActionListener(e -> cardLayout.show(cardsPanel, "LOCATIONS"));

        // Add buttons to the panel
        buttonPanel.add(usersButton);
        buttonPanel.add(carsButton);
        buttonPanel.add(rentalsButton);
        buttonPanel.add(locationsButton);
        // Add more buttons for other sections as needed

        add(buttonPanel, BorderLayout.NORTH); // Add the button panel to the top of the frame
    }
}
