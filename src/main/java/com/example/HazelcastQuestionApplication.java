package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicReference;

@SpringBootApplication
@EnableScheduling
@Component
public class HazelcastQuestionApplication {

    private static final String REF = "default";

    public static void main(String[] args) {
        SpringApplication.run(HazelcastQuestionApplication.class, args);
    }

    @Bean
    Config config() {
        return new Config();
    }

    @Bean
    CommandLineRunner cli(HazelcastInstance hzi) {
        return (args) -> {
            IAtomicReference<Boolean> ckeck = hzi.getAtomicReference(REF);
            if (ckeck.compareAndSet(null, Boolean.TRUE)) {
                System.out.println("We are started!");
            }
        };
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 10000)
    public void dummy() {
        // Prevent app to shutdown after System.out.println.
    }

}
