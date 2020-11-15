package br.com.driw.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainSpringRunner implements ApplicationRunner {

	private final MainSpringService mainSpringService;

	@Autowired
	public MainSpringRunner(MainSpringService mainSpringService) {
		this.mainSpringService = mainSpringService;
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.mainSpringService.initialize(args);

		while (this.mainSpringService.isAlive()) {
			this.mainSpringService.tick();
		}
	}

}
