package org.rostislav.panels;

import java.awt.BorderLayout;
import java.awt.Font;
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
import org.rostislav.models.CarMake;
import org.rostislav.models.CarModel;
import org.rostislav.service.CarMakeService;
import org.rostislav.service.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarModelsPanel extends JPanel implements NavigablePanel {

    private final CarModelService carModelService;
    private final CarMakeService carMakeService;
    private final DefaultTableModel tableModel;

    @Autowired
    public CarModelsPanel(NavigationController navigationController, CarModelService carModelService, CarMakeService carMakeService) {
        this.carModelService = carModelService;
        this.carMakeService = carMakeService;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Car Models", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Create a table to display car models
        String[] columns = {"ID", "Model Name", "Make Name"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for CRUD operations
        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add Model");
        JButton editButton = new JButton("Edit Model");
        JButton deleteButton = new JButton("Delete Model");
        JButton backButton = new JButton("Back");

        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(backButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Load car models into the table
        loadCarModels();

        // Button listeners
        addButton.addActionListener(e -> addCarModel());
        editButton.addActionListener(e -> editCarModel(table));
        deleteButton.addActionListener(e -> deleteCarModel(table));
        backButton.addActionListener(e -> navigationController.navigateTo("Dashboard"));
    }

    private void loadCarModels() {
        tableModel.setRowCount(0); // Clear existing rows
        Collection<CarModel> carModels = carModelService.getAllCarModels();
        Collection<CarMake> carMakes = carMakeService.getAllCarMakes();

        for (CarModel carModel : carModels) {
            String makeName = carMakes.stream()
                                      .filter(make -> make.getId() == carModel.getMakeId())
                                      .map(CarMake::getMakeName)
                                      .findFirst()
                                      .orElse("Unknown");
            tableModel.addRow(new Object[] {carModel.getId(), carModel.getModelName(), makeName});
        }
    }

    private void addCarModel() {
        JTextField modelNameField = new JTextField();
        JComboBox<CarMake> makeComboBox = new JComboBox<>(getCarMakesArray());

        Object[] fields = {
                "Model Name:", modelNameField,
                "Make:", makeComboBox
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Car Model", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            CarMake selectedMake = (CarMake) makeComboBox.getSelectedItem();
            if (selectedMake != null) {
                CarModel carModel = new CarModel(modelNameField.getText(), selectedMake.getId());
                carModelService.addCarModel(carModel);
                loadCarModels();
            }
        }
    }

    private void editCarModel(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int modelId = (int) table.getValueAt(selectedRow, 0);
            String currentModelName = (String) table.getValueAt(selectedRow, 1);
            String currentMakeName = (String) table.getValueAt(selectedRow, 2);

            JTextField modelNameField = new JTextField(currentModelName);
            JComboBox<CarMake> makeComboBox = new JComboBox<>(getCarMakesArray());
            makeComboBox.setSelectedItem(getCarMakeByName(currentMakeName));

            Object[] fields = {
                    "Model Name:", modelNameField,
                    "Make:", makeComboBox
            };

            int option = JOptionPane.showConfirmDialog(this, fields, "Edit Car Model", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                CarMake selectedMake = (CarMake) makeComboBox.getSelectedItem();
                if (selectedMake != null) {
                    CarModel carModel = new CarModel(modelNameField.getText(), selectedMake.getId());
                    carModel.setId(modelId);
                    carModelService.updateCarModel(carModel);
                    loadCarModels();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a car model to edit.", "Edit Car Model", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteCarModel(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int modelId = (int) table.getValueAt(selectedRow, 0);
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this car model?", "Delete Car Model", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                carModelService.deleteCarModel(modelId);
                loadCarModels();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a car model to delete.", "Delete Car Model", JOptionPane.WARNING_MESSAGE);
        }
    }

    private CarMake[] getCarMakesArray() {
        Collection<CarMake> carMakes = carMakeService.getAllCarMakes();
        return carMakes.toArray(new CarMake[0]);
    }

    private CarMake getCarMakeByName(String makeName) {
        return carMakeService.getAllCarMakes().stream()
                             .filter(make -> make.getMakeName().equals(makeName))
                             .findFirst()
                             .orElse(null);
    }

    @Override
    public String getPanelName() {
        return "CarModelsPanel";
    }

    @Override
    public JPanel getPanel() {
        loadCarModels();
        return this;
    }
}
