/**
 * Copyright (c)
 * 2016 Tsuyoyo. All Rights Reserved.
 */
package tsuyogoro.sugorokuon.server;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
public class SugorokuonApp {

    public static void main(String[] args) {
        SpringApplication.run(SugorokuonApp.class, args);
        Logger.getInstance(SugorokuonApp.class).setLevel(Level.INFO);
    }
}
