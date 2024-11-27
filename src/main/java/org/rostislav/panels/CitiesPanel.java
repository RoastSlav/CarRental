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
import org.rostislav.models.CityWithState;
import org.rostislav.models.State;
import org.rostislav.service.CityService;
import org.rostislav.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CitiesPanel extends JPanel implements NavigablePanel {

    private final NavigationController navigationController;
    private final CityService cityService;
    private final StateService stateService;
    private JTable citiesTable;
    private DefaultTableModel tableModel;

    @Autowired
    public CitiesPanel(NavigationController navigationController, CityService cityService, StateService stateService) {
        this.navigationController = navigationController;
        this.cityService = cityService;
        this.stateService = stateService;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Cities", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        setupCitiesTable();
        JPanel buttonsPanel = createButtonsPanel();
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void setupCitiesTable() {
        tableModel = new DefaultTableModel(new Object[] {"ID", "City Name", "State ID", "State Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        citiesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(citiesTable);
        add(scrollPane, BorderLayout.CENTER);

        loadCities();
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addCity());
        panel.add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateCity());
        panel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteCity());
        panel.add(deleteButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigationController.navigateTo("Dashboard"));
        panel.add(backButton);

        return panel;
    }

    private void loadCities() {
        tableModel.setRowCount(0);
        Collection<CityWithState> cities = cityService.getAllCitiesWithStateNames();
        for (CityWithState city : cities) {
            tableModel.addRow(new Object[] {city.getId(), city.getCityName(), city.getStateId(), city.getStateName()});
        }
    }

    private void addCity() {
        JTextField cityNameField = new JTextField();
        JComboBox<State> stateComboBox = new JComboBox<>();
        for (State state : stateService.getAllStates()) {
            stateComboBox.addItem(state);
        }

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("City Name:"));
        panel.add(cityNameField);
        panel.add(new JLabel("State:"));
        panel.add(stateComboBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add City", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            State selectedState = (State) stateComboBox.getSelectedItem();
            if (selectedState != null) {
                cityService.addCity(cityNameField.getText(), selectedState.getId());
                loadCities();
            }
        }
    }

    private void updateCity() {
        int selectedRow = citiesTable.getSelectedRow();
        if (selectedRow != -1) {
            int cityId = (int) tableModel.getValueAt(selectedRow, 0);
            String cityName = (String) tableModel.getValueAt(selectedRow, 1);

            JTextField cityNameField = new JTextField(cityName);
            JComboBox<State> stateComboBox = new JComboBox<>();
            for (State state : stateService.getAllStates()) {
                stateComboBox.addItem(state);
            }

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("City Name:"));
            panel.add(cityNameField);
            panel.add(new JLabel("State:"));
            panel.add(stateComboBox);

            int result = JOptionPane.showConfirmDialog(this, panel, "Update City", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                State selectedState = (State) stateComboBox.getSelectedItem();
                if (selectedState != null) {
                    cityService.updateCity(cityId, cityNameField.getText(), selectedState.getId());
                    loadCities();
                }
            }
        }
    }

    private void deleteCity() {
        int selectedRow = citiesTable.getSelectedRow();
        if (selectedRow != -1) {
            int cityId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this city?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                cityService.deleteCity(cityId);
                loadCities();
            }
        }
    }

    @Override
    public String getPanelName() {
        return "CitiesPanel";
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
