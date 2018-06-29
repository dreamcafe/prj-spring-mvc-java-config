package org.dreamcafe.smjc.service;

import org.dreamcafe.smjc.domain.GreetingInfo;
import org.dreamcafe.smjc.service.impl.WelcomeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WelcomeServiceTests {
    private WelcomeService welcomeService;

    @BeforeEach
    public void setup() {
        welcomeService = new WelcomeServiceImpl();
    }

    @Test
    public void greetingToSam() {
        // Given
        String name = "Sam";

        GreetingInfo info = welcomeService.greeting(name);
        assertThat(info).isNotNull();
        assertThat(info.getName()).isEqualTo(name);
        assertThat(info.getLocalCurrentDatetime()).isNotBlank();
    }
}
