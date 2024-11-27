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
import org.rostislav.models.CarColor;
import org.rostislav.service.CarColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarColorsPanel extends JPanel implements NavigablePanel {

    private final CarColorService carColorService;
    private final DefaultTableModel tableModel;

    @Autowired
    public CarColorsPanel(NavigationController navigationController, CarColorService carColorService) {
        this.carColorService = carColorService;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Car Colors", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        String[] columns = {"ID", "Color Name"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = createButtonsPanel(table, navigationController);
        add(buttonsPanel, BorderLayout.SOUTH);

        loadColors();
    }

    private JPanel createButtonsPanel(JTable table, NavigationController navigationController) {
        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton addButton = new JButton("Add Color");
        addButton.addActionListener(e -> addColor());
        panel.add(addButton);

        JButton editButton = new JButton("Edit Color");
        editButton.addActionListener(e -> editColor(table));
        panel.add(editButton);

        JButton deleteButton = new JButton("Delete Color");
        deleteButton.addActionListener(e -> deleteColor(table));
        panel.add(deleteButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigationController.navigateTo("Dashboard"));
        panel.add(backButton);

        return panel;
    }

    private void loadColors() {
        tableModel.setRowCount(0);
        Collection<CarColor> colors = carColorService.getAllColors();
        for (CarColor color : colors) {
            tableModel.addRow(new Object[] {color.getId(), color.getColorName()});
        }
    }

    private void addColor() {
        String colorName = JOptionPane.showInputDialog(this, "Enter Color Name:", "Add Color", JOptionPane.PLAIN_MESSAGE);
        if (colorName != null && !colorName.trim().isEmpty()) {
            carColorService.addCarColor(colorName.trim());
            loadColors();
        } else {
            JOptionPane.showMessageDialog(this, "Color name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editColor(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int colorId = (int) table.getValueAt(selectedRow, 0);
            String currentColorName = (String) table.getValueAt(selectedRow, 1);
            String newColorName = JOptionPane.showInputDialog(this, "Edit Color Name:", currentColorName);
            if (newColorName != null && !newColorName.trim().isEmpty()) {
                carColorService.updateCarColor(colorId, newColorName.trim());
                loadColors();
            } else {
                JOptionPane.showMessageDialog(this, "Color name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a color to edit.", "Edit Color", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteColor(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int colorId = (int) table.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this color?", "Delete Color", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                carColorService.deleteCarColor(colorId);
                loadColors();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a color to delete.", "Delete Color", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public String getPanelName() {
        return "CarColorsPanel";
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
