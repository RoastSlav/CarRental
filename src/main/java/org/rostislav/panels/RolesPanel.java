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
import org.rostislav.models.Role;
import org.rostislav.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RolesPanel extends JPanel implements NavigablePanel {

    private final RoleService roleService;
    private final DefaultTableModel tableModel;

    @Autowired
    public RolesPanel(NavigationController navigationController, RoleService roleService) {
        this.roleService = roleService;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Roles", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Create a table for displaying roles
        String[] columns = {"ID", "Role Name"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // CRUD buttons panel
        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add Role");
        JButton editButton = new JButton("Edit Role");
        JButton deleteButton = new JButton("Delete Role");
        JButton backButton = new JButton("Back");

        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(backButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Load roles into the table
        loadRoles();

        // Button listeners
        addButton.addActionListener(e -> addRole());
        editButton.addActionListener(e -> editRole(table));
        deleteButton.addActionListener(e -> deleteRole(table));
        backButton.addActionListener(e -> navigationController.navigateTo("Dashboard"));
    }

    private void loadRoles() {
        tableModel.setRowCount(0); // Clear existing rows
        Collection<Role> roles = roleService.getAllRoles();
        for (Role role : roles) {
            tableModel.addRow(new Object[] {role.getId(), role.getRoleName()});
        }
    }

    private void addRole() {
        String roleName = JOptionPane.showInputDialog(this, "Enter Role Name:", "Add Role", JOptionPane.PLAIN_MESSAGE);
        if (roleName != null && !roleName.trim().isEmpty()) {
            roleService.addRole(roleName.trim());
            loadRoles();
        }
    }

    private void editRole(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int roleId = (int) table.getValueAt(selectedRow, 0);
            String currentRoleName = (String) table.getValueAt(selectedRow, 1);
            String newRoleName = JOptionPane.showInputDialog(this, "Edit Role Name:", currentRoleName);
            if (newRoleName != null && !newRoleName.trim().isEmpty()) {
                roleService.updateRole(roleId, newRoleName.trim());
                loadRoles();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a role to edit.", "Edit Role", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteRole(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int roleId = (int) table.getValueAt(selectedRow, 0);
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this role? This will delete all users with this role!", "Delete Role", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                roleService.deleteRole(roleId);
                loadRoles();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a role to delete.", "Delete Role", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public String getPanelName() {
        return "RolesPanel";
    }

    @Override
    public JPanel getPanel() {
        loadRoles();
        return this;
    }
}
