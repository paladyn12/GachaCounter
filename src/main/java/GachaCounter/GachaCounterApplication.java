package GachaCounter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GachaCounterApplication {

	public static void main(String[] args) {
		SpringApplication.run(GachaCounterApplication.class, args);
	}

}
