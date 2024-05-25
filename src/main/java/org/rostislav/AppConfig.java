package org.rostislav;

import org.rostislav.service.CarService;
import org.rostislav.views.Dashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import panels.CarsPanel;

@Configuration
public class AppConfig {
    @Bean
    @Autowired
    public Dashboard dashboard(CarsPanel carsPanel) {
        return new Dashboard(carsPanel);
    }

    @Bean
    @Autowired
    public CarsPanel carsPanel(CarService carService) {
        return new CarsPanel(carService);
    }
}
