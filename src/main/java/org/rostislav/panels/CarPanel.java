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
import org.rostislav.models.Car;
import org.rostislav.models.CarColor;
import org.rostislav.models.CarModel;
import org.rostislav.models.Location;
import org.rostislav.service.CarColorService;
import org.rostislav.service.CarModelService;
import org.rostislav.service.CarService;
import org.rostislav.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarPanel extends JPanel implements NavigablePanel {

    private final CarService carService;
    private final LocationService locationService;
    private final DefaultTableModel tableModel;
    private final CarModelService carModelService;
    private final CarColorService carColorService;

    @Autowired
    public CarPanel(NavigationController navigationController, CarService carService, LocationService locationService, CarModelService carModelService, CarColorService carColorService) {
        this.carService = carService;
        this.locationService = locationService;
        this.carModelService = carModelService;
        this.carColorService = carColorService;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Cars", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        String[] columns = {"ID", "Model", "Year", "Color", "License Plate", "Status", "Location"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add Car");
        JButton editButton = new JButton("Edit Car");
        JButton deleteButton = new JButton("Delete Car");
        JButton backButton = new JButton("Back");

        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(backButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        loadCars();

        addButton.addActionListener(e -> addCar());
        editButton.addActionListener(e -> editCar(table));
        deleteButton.addActionListener(e -> deleteCar(table));
        backButton.addActionListener(e -> navigationController.navigateTo("Dashboard"));
    }

    private void loadCars() {
        tableModel.setRowCount(0);
        Collection<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            tableModel.addRow(new Object[] {
                    car.getId(),
                    car.getModelName(),
                    car.getYear(),
                    car.getColorName(),
                    car.getLicensePlate(),
                    car.getStatus(),
                    car.getLocationName()
            });
        }
    }

    private void addCar() {
        JTextField licensePlateField = new JTextField();
        JTextField yearField = new JTextField();
        JComboBox<Location> locationComboBox = new JComboBox<>(locationService.getAllLocations().toArray(new Location[0]));
        JComboBox<CarModel> modelComboBox = new JComboBox<>(carModelService.getAllCarModels().toArray(new CarModel[0]));
        JComboBox<CarColor> colorComboBox = new JComboBox<>(carColorService.getAllColors().toArray(new CarColor[0]));
        JComboBox<String> statusComboBox = new JComboBox<>(new String[] {"Available", "Unavailable"});

        Object[] fields = {
                "License Plate:", licensePlateField,
                "Year:", yearField,
                "Model:", modelComboBox,
                "Color:", colorComboBox,
                "Location:", locationComboBox,
                "Status:", statusComboBox
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Car", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                CarModel selectedModel = (CarModel) modelComboBox.getSelectedItem();
                CarColor selectedColor = (CarColor) colorComboBox.getSelectedItem();
                Location selectedLocation = (Location) locationComboBox.getSelectedItem();
                String status = (String) statusComboBox.getSelectedItem();

                if (selectedModel != null && selectedColor != null && selectedLocation != null && status != null) {
                    carService.addCar(
                            selectedModel.getId(),
                            Integer.parseInt(yearField.getText()),
                            selectedColor.getId(),
                            licensePlateField.getText(),
                            status,
                            selectedLocation.getId()
                    );
                    loadCars();
                } else {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (
                    NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid year format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editCar(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int carId = (int) table.getValueAt(selectedRow, 0);

            Car car = carService.getCarById(carId);
            if (car != null) {
                JComboBox<CarModel> modelComboBox = new JComboBox<>(carModelService.getAllCarModels().toArray(new CarModel[0]));
                JComboBox<CarColor> colorComboBox = new JComboBox<>(carColorService.getAllColors().toArray(new CarColor[0]));
                JComboBox<Location> locationComboBox = new JComboBox<>(locationService.getAllLocations().toArray(new Location[0]));
                JTextField licensePlateField = new JTextField(car.getLicensePlate());
                JTextField yearField = new JTextField(String.valueOf(car.getYear()));
                JComboBox<String> statusComboBox = new JComboBox<>(new String[] {"Available", "Unavailable"});
                statusComboBox.setSelectedItem(car.getStatus());

                Object[] fields = {
                        "Model:", modelComboBox,
                        "Year:", yearField,
                        "Color:", colorComboBox,
                        "License Plate:", licensePlateField,
                        "Location:", locationComboBox,
                        "Status:", statusComboBox
                };

                int option = JOptionPane.showConfirmDialog(this, fields, "Edit Car", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        CarModel selectedModel = (CarModel) modelComboBox.getSelectedItem();
                        CarColor selectedColor = (CarColor) colorComboBox.getSelectedItem();
                        Location selectedLocation = (Location) locationComboBox.getSelectedItem();

                        car.setModelId(selectedModel.getId());
                        car.setYear(Integer.parseInt(yearField.getText()));
                        car.setColorId(selectedColor.getId());
                        car.setLicensePlate(licensePlateField.getText());
                        car.setLocationId(selectedLocation.getId());
                        car.setStatus((String) statusComboBox.getSelectedItem());

                        carService.updateCar(car);
                        loadCars();
                    } catch (
                            NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Invalid input for year.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a car to edit.", "Edit Car", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteCar(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int carId = (int) table.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this car?", "Delete Car", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                carService.removeCar(carId);
                loadCars();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a car to delete.", "Delete Car", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public String getPanelName() {
        return "CarsPanel";
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
