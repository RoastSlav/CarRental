package org.rostislav.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.rostislav.controllers.NavigationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DashboardPanel extends JPanel implements NavigablePanel {

    private final NavigationController navigationController;

    @Autowired
    public DashboardPanel(NavigationController navigationController) {
        this.navigationController = navigationController;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Dashboard", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel categoriesPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        categoriesPanel.add(createCategoryPanel("Locations Management", new String[] {
                "Manage Cities", "Manage States", "Manage Locations"
        }, new String[] {
                "CitiesPanel", "StatesPanel", "LocationsPanel"
        }));

        categoriesPanel.add(createCategoryPanel("Rentals Management", new String[] {
                "Manage Rentals", "Manage Payments"
        }, new String[] {
                "RentalsPanel", "PaymentsPanel"
        }));

        categoriesPanel.add(createCategoryPanel("Cars Management", new String[] {
                "Manage Cars", "Manage Car Makes", "Manage Car Models", "Manage Car Colors"
        }, new String[] {
                "CarsPanel", "CarMakesPanel", "CarModelsPanel", "CarColorsPanel"
        }));

        categoriesPanel.add(createCategoryPanel("Users Management", new String[] {
                "Manage Users", "Manage Roles"
        }, new String[] {
                "UsersPanel", "RolesPanel"
        }));

        categoriesPanel.add(createCategoryPanel("Reports", new String[] {
                "Rental Reports", "Revenue Reports"
        }, new String[] {
                "RentalReportsPanel", "RevenueReportsPanel"
        }));

        add(categoriesPanel, BorderLayout.CENTER);
    }

    private JPanel createCategoryPanel(String title, String[] buttonLabels, String[] panelNames) {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BorderLayout());
        categoryPanel.setBorder(BorderFactory.createTitledBorder(title));

        JPanel buttonPanel = new JPanel(new GridLayout(buttonLabels.length, 1, 10, 10));
        for (int i = 0; i < buttonLabels.length; i++) {
            String label = buttonLabels[i];
            String panelName = panelNames[i];
            JButton button = new JButton(label);
            button.addActionListener(e -> navigationController.navigateTo(panelName));
            buttonPanel.add(button);
        }

        categoryPanel.add(buttonPanel, BorderLayout.CENTER);
        return categoryPanel;
    }

    @Override
    public String getPanelName() {
        return "Dashboard";
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
