package org.rostislav.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.rostislav.controllers.NavigationController;
import org.rostislav.models.State;
import org.rostislav.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatesPanel extends JPanel implements NavigablePanel {

    private final NavigationController navigationController;
    private final StateService stateService;
    private JTable statesTable;
    private DefaultTableModel tableModel;

    @Autowired
    public StatesPanel(NavigationController navigationController, StateService stateService) {
        this.navigationController = navigationController;
        this.stateService = stateService;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage States", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        setupStatesTable();
        JPanel buttonsPanel = createButtonsPanel();
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void setupStatesTable() {
        tableModel = new DefaultTableModel(new Object[] {"ID", "State Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // ID is not editable
            }
        };
        statesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(statesTable);
        add(scrollPane, BorderLayout.CENTER);

        loadStates();
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addState());
        panel.add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateState());
        panel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteState());
        panel.add(deleteButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigationController.navigateTo("Dashboard"));
        panel.add(backButton);

        return panel;
    }

    private void loadStates() {
        tableModel.setRowCount(0);
        Collection<State> states = stateService.getAllStates();
        for (State state : states) {
            tableModel.addRow(new Object[] {state.getId(), state.getStateName()});
        }
    }

    private void addState() {
        String stateName = JOptionPane.showInputDialog(this, "Enter State Name:");
        if (stateName != null && !stateName.trim().isEmpty()) {
            stateService.addState(stateName);
            loadStates();
        }
    }

    private void updateState() {
        int selectedRow = statesTable.getSelectedRow();
        if (selectedRow != -1) {
            int stateId = (int) tableModel.getValueAt(selectedRow, 0);
            String stateName = JOptionPane.showInputDialog(this, "Update State Name:", tableModel.getValueAt(selectedRow, 1));
            if (stateName != null && !stateName.trim().isEmpty()) {
                stateService.updateState(stateId, stateName);
                loadStates();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a state to update.");
        }
    }

    private void deleteState() {
        int selectedRow = statesTable.getSelectedRow();
        if (selectedRow != -1) {
            int stateId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this state? This action cannot be undone and will delete all other data reliant on it.", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                stateService.deleteState(stateId);
                loadStates();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a state to delete.");
        }
    }

    @Override
    public String getPanelName() {
        return "StatesPanel";
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}