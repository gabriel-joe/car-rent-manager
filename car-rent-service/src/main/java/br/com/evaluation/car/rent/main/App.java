package br.com.evaluation.car.rent.main;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.evaluation.car.rent.service.CategoryService;

@EnableAutoConfiguration
@SpringBootApplication
@EnableJpaRepositories("br.com.evaluation.car.rent.repository")
@ComponentScan("br.com.evaluation.car.rent")
@EntityScan("br.com.evaluation.car.rent.entity") 
public class App {
	
	public App() {
		
	}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(App.class, args);
		checkProfileToSetDefaultValues(applicationContext);
	}
	
	
	/**
	 * Method responsible to check active profile and save default values if necessary
	 * @param applicationContext
	 */
	private static void checkProfileToSetDefaultValues(ConfigurableApplicationContext applicationContext) {
		String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
		Optional<String> optional = Arrays.stream(profiles)
                .filter(profile -> "mysql".equals(profile))
                .findFirst();
		if(!optional.isPresent()) {
			CategoryService categoryService = applicationContext.getBean(CategoryService.class);
			categoryService.initData();
		}
	}
	
}
