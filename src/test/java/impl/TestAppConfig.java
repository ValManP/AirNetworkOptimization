package impl;

import impl.controllers.BusinessController;
import impl.controllers.MatrixController;
import impl.controllers.NetworkController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestAppConfig {
    @Bean
    public NetworkController networkController() {
        return new NetworkController();
    }

    @Bean
    public MatrixController matrixController() {
        return new MatrixController();
    }

    @Bean
    public BusinessController businessController() {
        return new BusinessController();
    }
}
