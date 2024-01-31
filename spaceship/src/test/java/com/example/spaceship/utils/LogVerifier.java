package com.example.spaceship.utils;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import lombok.experimental.UtilityClass;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@UtilityClass
public class LogVerifier {

    public static final String MESSAGE_PROPERTY_FIELD = "message";

    public static void verifyLogMessage(Class<?> producingLogsClass, Runnable runnable, String expectedMessage) {
        var logger = (Logger) LoggerFactory.getLogger(producingLogsClass);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        runnable.run();

        assertThat(listAppender.list).extracting(MESSAGE_PROPERTY_FIELD).contains(expectedMessage);
        logger.detachAppender(listAppender);
    }
}
