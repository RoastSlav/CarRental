package org.rostislav;

import org.rostislav.views.Dashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Dashboard dashboard() {
        return new Dashboard();
    }
}
