package org.rostislav.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import org.rostislav.models.City;
import org.rostislav.models.LocationWithCity;
import org.rostislav.service.CityService;
import org.rostislav.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationsPanel extends JPanel implements NavigablePanel {

    private final NavigationController navigationController;
    private final LocationService locationService;
    private final CityService cityService;
    private JTable locationsTable;
    private DefaultTableModel tableModel;

    @Autowired
    public LocationsPanel(NavigationController navigationController, LocationService locationService, CityService cityService) {
        this.navigationController = navigationController;
        this.locationService = locationService;
        this.cityService = cityService;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Locations", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        setupLocationsTable();
        JPanel buttonsPanel = createButtonsPanel();
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void setupLocationsTable() {
        tableModel = new DefaultTableModel(new Object[] {"ID", "Name", "Address", "City ID", "City Name", "Zip Code"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        locationsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(locationsTable);
        add(scrollPane, BorderLayout.CENTER);

        loadLocations();
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addLocation());
        panel.add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateLocation());
        panel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteLocation());
        panel.add(deleteButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigationController.navigateTo("Dashboard"));
        panel.add(backButton);

        return panel;
    }

    private void loadLocations() {
        tableModel.setRowCount(0);
        Collection<LocationWithCity> locations = locationService.getAllLocationsWithCityNames();
        for (LocationWithCity location : locations) {
            tableModel.addRow(new Object[] {
                    location.getId(),
                    location.getName(),
                    location.getAddress(),
                    location.getCityId(),
                    location.getCityName(),
                    location.getZipCode()
            });
        }
    }

    private void addLocation() {
        JTextField nameField = new JTextField();
        JTextField addressField = new JTextField();
        JComboBox<City> cityComboBox = new JComboBox<>(cityService.getAllCitiesWithStateNames().stream()
                                                                  .map(city -> new City(city.getCityName(), city.getStateId()) {{
                                                                      setId(city.getId());
                                                                  }}).toArray(City[]::new));
        JTextField zipCodeField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("City:"));
        panel.add(cityComboBox);
        panel.add(new JLabel("Zip Code:"));
        panel.add(zipCodeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Location", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            City selectedCity = (City) cityComboBox.getSelectedItem();
            if (selectedCity != null) {
                locationService.addLocation(nameField.getText(), addressField.getText(), selectedCity.getId(), zipCodeField.getText());
                loadLocations();
            }
        }
    }

    private void updateLocation() {
        int selectedRow = locationsTable.getSelectedRow();
        if (selectedRow != -1) {
            int locationId = (int) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            String address = (String) tableModel.getValueAt(selectedRow, 2);
            String zipCode = (String) tableModel.getValueAt(selectedRow, 5);

            JTextField nameField = new JTextField(name);
            JTextField addressField = new JTextField(address);
            JComboBox<City> cityComboBox = new JComboBox<>(cityService.getAllCitiesWithStateNames().stream()
                                                                      .map(city -> new City(city.getCityName(), city.getStateId()) {{
                                                                          setId(city.getId());
                                                                      }}).toArray(City[]::new));
            JTextField zipCodeField = new JTextField(zipCode);

            JPanel panel = new JPanel(new GridLayout(4, 2));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Address:"));
            panel.add(addressField);
            panel.add(new JLabel("City:"));
            panel.add(cityComboBox);
            panel.add(new JLabel("Zip Code:"));
            panel.add(zipCodeField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Update Location", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                City selectedCity = (City) cityComboBox.getSelectedItem();
                if (selectedCity != null) {
                    locationService.updateLocation(locationId, nameField.getText(), addressField.getText(), selectedCity.getId(), zipCodeField.getText());
                    loadLocations();
                }
            }
        }
    }

    private void deleteLocation() {
        int selectedRow = locationsTable.getSelectedRow();
        if (selectedRow != -1) {
            int locationId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this location?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                locationService.deleteLocation(locationId);
                loadLocations();
            }
        }
    }

    @Override
    public String getPanelName() {
        return "LocationsPanel";
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
