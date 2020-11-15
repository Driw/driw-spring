package br.com.driw.spring;

import org.springframework.boot.ApplicationArguments;

public interface MainSpringService {
	void initialize(ApplicationArguments args);
	boolean isAlive();
	void tick();
}
