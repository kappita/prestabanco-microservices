package tingeso.prestabanco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class MortgageRequestApplication {
	public static void main(String[] args) {
		SpringApplication.run(MortgageRequestApplication.class, args);
	}

}
