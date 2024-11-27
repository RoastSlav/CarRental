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
import org.rostislav.models.Role;
import org.rostislav.models.User;
import org.rostislav.service.RoleService;
import org.rostislav.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersPanel extends JPanel implements NavigablePanel {

    private final UserService userService;
    private final RoleService roleService;
    private final DefaultTableModel tableModel;

    @Autowired
    public UsersPanel(NavigationController navigationController, UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Users", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Table to display users
        String[] columns = {"ID", "Full Name", "Email", "Phone", "License Number", "Role"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // CRUD buttons
        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Add User");
        JButton editButton = new JButton("Edit User");
        JButton deleteButton = new JButton("Delete User");
        JButton backButton = new JButton("Back");

        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(backButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        loadUsers();

        addButton.addActionListener(e -> addUser());
        editButton.addActionListener(e -> editUser(table));
        deleteButton.addActionListener(e -> deleteUser(table));
        backButton.addActionListener(e -> navigationController.navigateTo("Dashboard"));
    }

    private void loadUsers() {
        tableModel.setRowCount(0);
        Collection<User> users = userService.getAllUsers();
        for (User user : users) {
            String roleName = roleService.getRoleNameById(user.getRoleId());
            tableModel.addRow(new Object[] {user.getId(), user.getFullName(), user.getEmail(), user.getPhone(), user.getLicenseNumber(), roleName});
        }
    }

    private void addUser() {
        JTextField fullNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField licenseField = new JTextField();
        JComboBox<Role> roleComboBox = new JComboBox<>(getRolesArray());

        Object[] fields = {
                "Full Name:", fullNameField,
                "Email:", emailField,
                "Password:", passwordField,
                "Phone:", phoneField,
                "License Number:", licenseField,
                "Role:", roleComboBox
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add User", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Role selectedRole = (Role) roleComboBox.getSelectedItem();
            if (selectedRole != null) {
                userService.addUser(
                        fullNameField.getText(),
                        emailField.getText(),
                        passwordField.getText(),
                        phoneField.getText(),
                        licenseField.getText(),
                        selectedRole.getId()
                );
                loadUsers();
            }
        }
    }

    private void editUser(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) table.getValueAt(selectedRow, 0);
            String currentFullName = (String) table.getValueAt(selectedRow, 1);
            String currentEmail = (String) table.getValueAt(selectedRow, 2);
            String currentPhone = (String) table.getValueAt(selectedRow, 3);
            String currentLicense = (String) table.getValueAt(selectedRow, 4);
            String currentRoleName = (String) table.getValueAt(selectedRow, 5);

            JTextField fullNameField = new JTextField(currentFullName);
            JTextField emailField = new JTextField(currentEmail);
            JTextField phoneField = new JTextField(currentPhone);
            JTextField licenseField = new JTextField(currentLicense);
            JComboBox<Role> roleComboBox = new JComboBox<>(getRolesArray());
            roleComboBox.setSelectedItem(getRoleByName(currentRoleName));

            Object[] fields = {
                    "Full Name:", fullNameField,
                    "Email:", emailField,
                    "Phone:", phoneField,
                    "License Number:", licenseField,
                    "Role:", roleComboBox
            };

            int option = JOptionPane.showConfirmDialog(this, fields, "Edit User", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Role selectedRole = (Role) roleComboBox.getSelectedItem();
                if (selectedRole != null) {
                    userService.updateUser(
                            userId,
                            fullNameField.getText(),
                            emailField.getText(),
                            phoneField.getText(),
                            licenseField.getText(),
                            selectedRole.getId()
                    );
                    loadUsers();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.", "Edit User", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteUser(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) table.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Delete User", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                userService.deleteUser(userId);
                loadUsers();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Delete User", JOptionPane.WARNING_MESSAGE);
        }
    }

    private Role[] getRolesArray() {
        Collection<Role> roles = roleService.getAllRoles();
        return roles.toArray(new Role[0]);
    }

    private Role getRoleByName(String roleName) {
        return roleService.getAllRoles().stream()
                          .filter(role -> role.getRoleName().equals(roleName))
                          .findFirst()
                          .orElse(null);
    }

    @Override
    public String getPanelName() {
        return "UsersPanel";
    }

    @Override
    public JPanel getPanel() {
        loadUsers();
        return this;
    }
}