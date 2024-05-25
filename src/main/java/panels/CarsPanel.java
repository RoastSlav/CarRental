package panels;

import org.rostislav.models.Car;
import org.rostislav.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Controller
public class CarsPanel extends JPanel {
    private final CarService carService;
    private JList<Car> carsList;
    private DefaultListModel<Car> carsListModel;

    private JTextField idField, modelIdField, yearField, colorIdField, licensePlateField, statusField, locationIdField;
    private JButton createButton, updateButton, deleteButton;

    @Autowired
    public CarsPanel(CarService carService) {
        this.carService = carService;

        idField = new JTextField(10);
        modelIdField = new JTextField(10);
        yearField = new JTextField(4);
        colorIdField = new JTextField(10);
        licensePlateField = new JTextField(15);
        statusField = new JTextField(10);
        locationIdField = new JTextField(10);

        createButton = new JButton("Create");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        createButton.addActionListener(e -> {
            Car car = new Car();
            car.setModelId(Integer.parseInt(modelIdField.getText()));
            car.setYear(Integer.parseInt(yearField.getText()));
            car.setColorId(Integer.parseInt(colorIdField.getText()));
            car.setLicensePlate(licensePlateField.getText());
            car.setStatus(statusField.getText());
            car.setLocationId(Integer.parseInt(locationIdField.getText()));
            carService.addCar(car);
            JOptionPane.showMessageDialog(null, "Car created successfully!");
        });

        updateButton.addActionListener(e -> {
            Car car = new Car();
            car.setId(Integer.parseInt(idField.getText()));
            car.setModelId(Integer.parseInt(modelIdField.getText()));
            car.setYear(Integer.parseInt(yearField.getText()));
            car.setColorId(Integer.parseInt(colorIdField.getText()));
            car.setLicensePlate(licensePlateField.getText());
            car.setStatus(statusField.getText());
            car.setLocationId(Integer.parseInt(locationIdField.getText()));
            carService.updateCar(car);
            JOptionPane.showMessageDialog(null, "Car updated successfully!");
        });

        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                carService.removeCar(id);
                JOptionPane.showMessageDialog(null, "Car deleted successfully!");
                loadCars(); // Refresh the car list
                clearForm(); // Clear the form fields
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error deleting car: " + ex.getMessage());
            }
        });

        carsListModel = new DefaultListModel<>();
        carsList = new JList<>(carsListModel);
        carsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        loadCars();

        carsList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            Car selectedCar = carsList.getSelectedValue();
            if (selectedCar != null) {
                idField.setText(String.valueOf(selectedCar.getId()));
                modelIdField.setText(String.valueOf(selectedCar.getModelId()));
                yearField.setText(String.valueOf(selectedCar.getYear()));
                colorIdField.setText(String.valueOf(selectedCar.getColorId()));
                licensePlateField.setText(selectedCar.getLicensePlate());
                statusField.setText(selectedCar.getStatus());
                locationIdField.setText(String.valueOf(selectedCar.getLocationId()));
            }
        });

        JScrollPane listScrollPane = new JScrollPane(carsList);
        listScrollPane.setPreferredSize(new Dimension(200, 150));

        // Add components to the panel
        add(listScrollPane);
    }

    private void loadCars() {
        List<Car> cars = (List<Car>) carService.getAllCars(); // This method needs to be implemented in the repository
        carsListModel.removeAllElements();
        for (Car car : cars) {
            carsListModel.addElement(car);
        }
    }

    private void clearForm() {
        idField.setText("");
        modelIdField.setText("");
        yearField.setText("");
        colorIdField.setText("");
        licensePlateField.setText("");
        statusField.setText("");
        locationIdField.setText("");
        carsList.clearSelection();
    }
}
