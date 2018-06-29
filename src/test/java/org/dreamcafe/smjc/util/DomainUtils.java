package org.dreamcafe.smjc.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dreamcafe.smjc.domain.GreetingInfo;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class DomainUtils {
    private final static DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS O");

    public static GreetingInfo newGreetingInfo(String name) {
        GreetingInfo info = new GreetingInfo();
        info.setName(name);
        info.setLocalCurrentDatetime(DATE_TIME_FORMATTER.format(ZonedDateTime.now()));

        return info;
    }
}
