package org.rostislav;

import com.sun.tools.javac.Main;
import org.mybatis.spring.annotation.MapperScan;
import org.rostislav.views.Dashboard;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import javax.swing.*;

@SpringBootApplication
@MapperScan("org.rostislav.repository")
public class App implements CommandLineRunner {
    static ApplicationContext context;
    public static void main(String[] args) {
        context = new SpringApplicationBuilder(App.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
        SwingUtilities.invokeLater(() -> {
            Dashboard dashboard = context.getBean(Dashboard.class);
            dashboard.setVisible(true);
        });
    }

    @Override
    public void run(String... args) {

    }
}
