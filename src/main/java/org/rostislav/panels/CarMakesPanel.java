package org.rostislav.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.rostislav.controllers.NavigationController;
import org.rostislav.models.CarMake;
import org.rostislav.service.CarMakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarMakesPanel extends JPanel implements NavigablePanel {

    private final CarMakeService carMakeService;
    private final DefaultTableModel tableModel;

    @Autowired
    public CarMakesPanel(NavigationController navigationController, CarMakeService carMakeService) {
        this.carMakeService = carMakeService;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Car Makes", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Create a table to display car makes
        String[] columns = {"ID", "Make Name"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for CRUD operations
        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add Make");
        JButton editButton = new JButton("Edit Make");
        JButton deleteButton = new JButton("Delete Make");
        JButton backButton = new JButton("Back");

        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(backButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Load car makes into the table
        loadCarMakes();

        // Button listeners
        addButton.addActionListener(e -> addCarMake());
        editButton.addActionListener(e -> editCarMake(table));
        deleteButton.addActionListener(e -> deleteCarMake(table));
        backButton.addActionListener(e -> navigationController.navigateTo("Dashboard"));
    }

    private void loadCarMakes() {
        tableModel.setRowCount(0); // Clear existing rows
        Collection<CarMake> carMakes = carMakeService.getAllCarMakes();
        for (CarMake carMake : carMakes) {
            tableModel.addRow(new Object[] {carMake.getId(), carMake.getMakeName()});
        }
    }

    private void addCarMake() {
        String makeName = JOptionPane.showInputDialog(this, "Enter Make Name:", "Add Car Make", JOptionPane.PLAIN_MESSAGE);
        if (makeName != null && !makeName.trim().isEmpty()) {
            carMakeService.addCarMake(new CarMake(makeName.trim()));
            loadCarMakes();
        }
    }

    private void editCarMake(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int makeId = (int) table.getValueAt(selectedRow, 0);
            String currentMakeName = (String) table.getValueAt(selectedRow, 1);
            String newMakeName = JOptionPane.showInputDialog(this, "Edit Make Name:", currentMakeName);
            if (newMakeName != null && !newMakeName.trim().isEmpty()) {
                CarMake updatedCarMake = new CarMake(newMakeName.trim());
                updatedCarMake.setId(makeId);
                carMakeService.updateCarMake(updatedCarMake);
                loadCarMakes();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a car make to edit.", "Edit Car Make", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteCarMake(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int makeId = (int) table.getValueAt(selectedRow, 0);
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this car make?", "Delete Car Make", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                carMakeService.deleteCarMake(makeId);
                loadCarMakes();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a car make to delete.", "Delete Car Make", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public String getPanelName() {
        return "CarMakesPanel";
    }

    @Override
    public JPanel getPanel() {
        loadCarMakes();
        return this;
    }
}
