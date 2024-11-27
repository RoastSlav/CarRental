package org.rostislav.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.rostislav.controllers.NavigationController;
import org.rostislav.models.Car;
import org.rostislav.models.Rental;
import org.rostislav.models.User;
import org.rostislav.service.CarService;
import org.rostislav.service.RentalService;
import org.rostislav.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentalsPanel extends JPanel implements NavigablePanel {

    private final RentalService rentalService;
    private final CarService carService;
    private final UserService userService;
    private final DefaultTableModel tableModel;

    @Autowired
    public RentalsPanel(NavigationController navigationController, RentalService rentalService, CarService carService, UserService userService) {
        this.rentalService = rentalService;
        this.carService = carService;
        this.userService = userService;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Rentals", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        String[] columns = {"ID", "User", "Car", "Pickup Date", "Dropoff Date", "Total Price", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add Rental");
        JButton editButton = new JButton("Edit Rental");
        JButton deleteButton = new JButton("Delete Rental");
        JButton backButton = new JButton("Back");

        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(backButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        loadRentals();

        addButton.addActionListener(e -> addRental());
        editButton.addActionListener(e -> editRental(table));
        deleteButton.addActionListener(e -> deleteRental(table));
        backButton.addActionListener(e -> navigationController.navigateTo("Dashboard"));
    }

    private void loadRentals() {
        tableModel.setRowCount(0);
        Collection<Rental> rentals = rentalService.getAllRentals();
        for (Rental rental : rentals) {
            tableModel.addRow(new Object[] {
                    rental.getId(),
                    getUserNameById(rental.getUserId()),
                    getCarLicensePlateById(rental.getCarId()),
                    rental.getPickupDate(),
                    rental.getDropoffDate(),
                    rental.getTotalPrice(),
                    rental.getStatus()
            });
        }
    }

    private String getUserNameById(int userId) {
        User user = userService.getUserById(userId);
        return user != null ? user.getFullName() : "Unknown";
    }

    private String getCarLicensePlateById(int carId) {
        Car car = carService.getCarById(carId);
        return car != null ? car.getLicensePlate() : "Unknown";
    }

    private void addRental() {
        JComboBox<User> userComboBox = new JComboBox<>(userService.getAllUsers().toArray(new User[0]));
        JComboBox<Car> carComboBox = new JComboBox<>(carService.getAllCars().toArray(new Car[0]));
        JTextField pickupDateField = new JTextField();
        JTextField dropoffDateField = new JTextField();
        JTextField totalPriceField = new JTextField();

        JComboBox<String> statusComboBox = new JComboBox<>(new String[] {"Reserved", "InProgress", "Completed", "Cancelled"});

        Object[] fields = {
                "User:", userComboBox,
                "Car:", carComboBox,
                "Pickup Date (YYYY-MM-DD):", pickupDateField,
                "Dropoff Date (YYYY-MM-DD):", dropoffDateField,
                "Total Price:", totalPriceField,
                "Status:", statusComboBox
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Rental", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                User selectedUser = (User) userComboBox.getSelectedItem();
                Car selectedCar = (Car) carComboBox.getSelectedItem();
                LocalDate pickupDate = LocalDate.parse(pickupDateField.getText());
                LocalDate dropoffDate = LocalDate.parse(dropoffDateField.getText());
                BigDecimal totalPrice = new BigDecimal(totalPriceField.getText());
                String status = (String) statusComboBox.getSelectedItem();

                if (selectedUser != null && selectedCar != null) {
                    rentalService.addRental(selectedUser.getId(), selectedCar.getId(), pickupDate, dropoffDate, totalPrice, status);
                    loadRentals();
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a user and car.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (
                    Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editRental(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int rentalId = (int) table.getValueAt(selectedRow, 0);

            Rental rental = rentalService.getRentalById(rentalId);
            if (rental != null) {
                JComboBox<User> userComboBox = new JComboBox<>(userService.getAllUsers().toArray(new User[0]));
                userComboBox.setSelectedItem(userService.getUserById(rental.getUserId()));
                JComboBox<Car> carComboBox = new JComboBox<>(carService.getAllCars().toArray(new Car[0]));
                carComboBox.setSelectedItem(carService.getCarById(rental.getCarId()));
                JTextField pickupDateField = new JTextField(rental.getPickupDate().toString());
                JTextField dropoffDateField = new JTextField(rental.getDropoffDate().toString());
                JTextField totalPriceField = new JTextField(rental.getTotalPrice().toString());

                JComboBox<String> statusComboBox = new JComboBox<>(new String[] {"Reserved", "InProgress", "Completed", "Cancelled"});
                statusComboBox.setSelectedItem(rental.getStatus());

                Object[] fields = {
                        "User:", userComboBox,
                        "Car:", carComboBox,
                        "Pickup Date (YYYY-MM-DD):", pickupDateField,
                        "Dropoff Date (YYYY-MM-DD):", dropoffDateField,
                        "Total Price:", totalPriceField,
                        "Status:", statusComboBox
                };

                int option = JOptionPane.showConfirmDialog(this, fields, "Edit Rental", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        rental.setUserId(((User) userComboBox.getSelectedItem()).getId());
                        rental.setCarId(((Car) carComboBox.getSelectedItem()).getId());
                        rental.setPickupDate(LocalDate.parse(pickupDateField.getText()));
                        rental.setDropoffDate(LocalDate.parse(dropoffDateField.getText()));
                        rental.setTotalPrice(new BigDecimal(totalPriceField.getText()));
                        rental.setStatus((String) statusComboBox.getSelectedItem());

                        rentalService.updateRental(rental);
                        loadRentals();
                    } catch (
                            Exception e) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a rental to edit.", "Edit Rental", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteRental(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int rentalId = (int) table.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this rental?", "Delete Rental", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                rentalService.removeRental(rentalId);
                loadRentals();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a rental to delete.", "Delete Rental", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public String getPanelName() {
        return "RentalsPanel";
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
