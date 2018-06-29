package org.dreamcafe.smjc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.dreamcafe.smjc.domain.GreetingInfo;
import org.dreamcafe.smjc.service.WelcomeService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class WelcomeServiceImpl implements WelcomeService {
    private final static DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS O");

    @Override
    public GreetingInfo greeting(final String name) {
        log.info("Greeting to {}", name);
        GreetingInfo info = new GreetingInfo();
        info.setName(name);
        info.setLocalCurrentDatetime(DATE_TIME_FORMATTER.format(ZonedDateTime.now()));

        return info;
    }
}
