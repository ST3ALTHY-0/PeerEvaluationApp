package edu.pui.peerEvaluation.PeerEvaluationApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
// @EnableJpaRepositories(basePackages = "edu.pui.peerEvaluation.PeerEvaluationApplication.orm")
public class PeerevualuationapplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeerevualuationapplicationApplication.class, args);
	}

}
