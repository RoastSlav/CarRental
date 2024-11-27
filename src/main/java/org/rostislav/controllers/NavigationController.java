package org.rostislav.controllers;

import org.rostislav.views.MainWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class NavigationController {

    private final MainWindow mainWindow;

    @Autowired
    public NavigationController(@Lazy MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void navigateTo(String panelName) {
        mainWindow.switchToPanel(panelName);
    }
}
