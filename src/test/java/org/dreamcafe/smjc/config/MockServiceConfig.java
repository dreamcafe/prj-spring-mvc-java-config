package org.dreamcafe.smjc.config;

import org.dreamcafe.smjc.service.WelcomeService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockServiceConfig {

    @Bean
    public WelcomeService welcomeServiceMock() {
        return Mockito.mock(WelcomeService.class);
    }

}
