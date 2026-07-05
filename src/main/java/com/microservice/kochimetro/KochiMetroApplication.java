package com.microservice.kochimetro;

import com.microservice.kochimetro.optimization.orTools.solver.BasicTrainSelectionSolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KochiMetroApplication {

    public static void main(String[] args) {

        SpringApplication.run(KochiMetroApplication.class, args);

    }

}
