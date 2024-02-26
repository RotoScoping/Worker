package core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application
 */
@SpringBootApplication
public class Main {

    /**
     * The Main method provides information about the class that contains @SpringBootApplication,
     * which causes autoconfiguration, component scanning, and other IoC container initialization steps.
     *
     * @param args the input arguments
     */
    public static void main(String[] args)
    {

        SpringApplication.run(
                Main.class, args);
    }
}