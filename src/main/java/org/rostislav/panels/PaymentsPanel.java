package org.rostislav.panels;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.rostislav.controllers.NavigationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentsPanel extends JPanel implements NavigablePanel {

    @Autowired
    public PaymentsPanel(NavigationController navigationController) {

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Payments", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Placeholder for CRUD operations
        add(new JLabel("CRUD operations for Payments go here"), BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigationController.navigateTo("Dashboard"));
        add(backButton, BorderLayout.SOUTH);
    }

    @Override
    public String getPanelName() {
        return "PaymentsPanel";
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
