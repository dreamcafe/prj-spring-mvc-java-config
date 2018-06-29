package org.dreamcafe.smjc.controller;

import org.dreamcafe.smjc.domain.GreetingInfo;
import org.dreamcafe.smjc.service.WelcomeService;
import org.dreamcafe.smjc.util.DomainUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class WelcomeControllerTests extends AbstractControllerTests {
    @Autowired
    private WelcomeService welcomeServiceMock;

    @Override
    protected void beforeTest() {
        // Usually, every bean in spring context is Singleton.
        // This is to clear the play record of the Spring's bean
        // for each test.
        Mockito.reset(welcomeServiceMock);
    }

    @Test
    public void greeting() throws Exception {
        // Given
        String name = "sam";
        String url = "/welcome/" + name;
        String infoName = "info";
        GreetingInfo info = DomainUtils.newGreetingInfo(name);

        // When
        when(welcomeServiceMock.greeting(name)).thenReturn(info);

        // Then
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(model().attribute(infoName, notNullValue()))
                .andExpect(model().attribute(infoName, instanceOf(GreetingInfo.class)))
                .andExpect(model().attribute(infoName, is(info)))
                .andExpect(view().name("templates/welcome"));

        verify(welcomeServiceMock, times(1)).greeting(name);
    }
}
