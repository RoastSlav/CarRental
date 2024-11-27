package org.rostislav;

import javax.swing.SwingUtilities;

import org.mybatis.spring.annotation.MapperScan;
import org.rostislav.views.MainWindow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"org.rostislav"})
@MapperScan("org.rostislav.repository")
public class App {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);

        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = context.getBean(MainWindow.class);
            mainWindow.setVisible(true);
        });
    }
}
