package project.moseup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
public class MoseupApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoseupApplication.class, args);
	}

}
