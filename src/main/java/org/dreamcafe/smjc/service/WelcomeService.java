package org.dreamcafe.smjc.service;

import org.dreamcafe.smjc.domain.GreetingInfo;

public interface WelcomeService {
    GreetingInfo greeting(String name);
}
