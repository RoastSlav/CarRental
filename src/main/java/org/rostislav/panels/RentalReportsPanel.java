package org.rostislav.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
import org.rostislav.models.RentalReport;
import org.rostislav.service.RentalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentalReportsPanel extends JPanel implements NavigablePanel {
    private final RentalReportService rentalReportService;
    private final DefaultTableModel tableModel;

    @Autowired
    public RentalReportsPanel(NavigationController navigationController, RentalReportService rentalReportService) {
        this.rentalReportService = rentalReportService;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Rental Reports", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        String[] columns = {"User", "Car License Plate", "Pickup Date", "Dropoff Date", "Total Price", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel filtersPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        JTextField userField = new JTextField();
        JTextField carField = new JTextField();
        JComboBox<String> statusComboBox = new JComboBox<>(new String[] {"All", "Pending", "InProgress", "Completed", "Cancelled"});
        JTextField startDateField = new JTextField("YYYY-MM-DD");
        JTextField endDateField = new JTextField("YYYY-MM-DD");

        startDateField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (startDateField.getText().equals("YYYY-MM-DD")) {
                    startDateField.setText("");
                }
            }
        });

        endDateField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (endDateField.getText().equals("YYYY-MM-DD")) {
                    endDateField.setText("");
                }
            }
        });

        filtersPanel.add(new JLabel("User Name:"));
        filtersPanel.add(userField);
        filtersPanel.add(new JLabel("Car License Plate:"));
        filtersPanel.add(carField);
        filtersPanel.add(new JLabel("Status:"));
        filtersPanel.add(statusComboBox);
        filtersPanel.add(new JLabel("Start Date:"));
        filtersPanel.add(startDateField);
        filtersPanel.add(new JLabel("End Date:"));
        filtersPanel.add(endDateField);

        add(filtersPanel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        JButton generateButton = new JButton("Generate Report");
        buttonsPanel.add(generateButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigationController.navigateTo("Dashboard"));
        buttonsPanel.add(backButton);

        add(buttonsPanel, BorderLayout.SOUTH);

        generateButton.addActionListener(e -> {
            String userName = userField.getText().trim();
            String carLicensePlate = carField.getText().trim();
            String status = statusComboBox.getSelectedItem().toString();
            String startDate = startDateField.getText().trim();
            String endDate = endDateField.getText().trim();

            generateReport(userName, carLicensePlate, status, startDate, endDate);
        });
    }

    private void generateReport(String userName, String carLicensePlate, String status, String startDate, String endDate) {
        try {
            userName = userName.isEmpty() ? null : userName;
            carLicensePlate = carLicensePlate.isEmpty() ? null : carLicensePlate;
            status = status.equals("All") ? null : status;

            LocalDate start = null;
            LocalDate end = null;

            if (!startDate.isEmpty() && !startDate.equals("YYYY-MM-DD")) {
                start = LocalDate.parse(startDate);
            }
            if (!endDate.isEmpty() && !endDate.equals("YYYY-MM-DD")) {
                end = LocalDate.parse(endDate);
            }

            if (start != null && end != null && start.isAfter(end)) {
                throw new IllegalArgumentException("Start date cannot be after end date.");
            }

            Collection<RentalReport> reports = rentalReportService.getRentalReport(userName, carLicensePlate, status, start, end);
            loadReportData(reports);
        } catch (
                DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (
                IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (
                Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred while generating the report. Please check your filters.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadReportData(Collection<RentalReport> reports) {
        tableModel.setRowCount(0);
        for (RentalReport report : reports) {
            tableModel.addRow(new Object[] {
                    report.getUserName(),
                    report.getCarLicensePlate(),
                    report.getPickupDate(),
                    report.getDropoffDate(),
                    report.getTotalPrice(),
                    report.getStatus()
            });
        }
    }

    @Override
    public String getPanelName() {
        return "RentalReportsPanel";
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
