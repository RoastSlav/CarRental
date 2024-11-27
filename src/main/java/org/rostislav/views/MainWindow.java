package org.rostislav.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.rostislav.panels.NavigablePanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainWindow extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private final Map<String, JPanel> panels = new HashMap<>();

    @Autowired
    public MainWindow(List<NavigablePanel> navigablePanels) {
        setTitle("Car Rental Management System");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        for (NavigablePanel panel : navigablePanels) {
            panels.put(panel.getPanelName(), panel.getPanel());
            mainPanel.add(panel.getPanel(), panel.getPanelName());
        }

        switchToPanel("Dashboard");
        setLocationRelativeTo(null);
    }

    public void switchToPanel(String panelName) {
        if (panels.containsKey(panelName)) {
            cardLayout.show(mainPanel, panelName);
        } else {
            JOptionPane.showMessageDialog(this, "Panel not found: " + panelName, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
