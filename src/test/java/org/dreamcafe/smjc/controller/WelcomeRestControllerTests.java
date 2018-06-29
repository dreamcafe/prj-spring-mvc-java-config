package org.dreamcafe.smjc.controller;

import org.dreamcafe.smjc.domain.GreetingInfo;
import org.dreamcafe.smjc.service.WelcomeService;
import org.dreamcafe.smjc.util.DomainUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WelcomeRestControllerTests extends AbstractControllerTests {
    @Autowired
    private WelcomeService welcomeService;

    @Override
    protected void beforeTest() {
        Mockito.reset(welcomeService);
    }

    @Test
    public void greeting() throws Exception {
        // Given
        String name = "sam";
        String url = "/api/welcome/" + name;
        GreetingInfo info = DomainUtils.newGreetingInfo(name);

        // When
        when(welcomeService.greeting(name)).thenReturn(info);

        // Then
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").hasJsonPath())
                .andExpect(jsonPath("$.datetime").hasJsonPath())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.datetime").isNotEmpty());

        verify(welcomeService, times(1)).greeting(name);
    }
}
