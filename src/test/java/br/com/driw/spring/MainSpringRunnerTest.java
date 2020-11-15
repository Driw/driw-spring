package br.com.driw.spring;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.atomic.AtomicBoolean;

class MainSpringRunnerTest {

	private MainSpringRunner mainSpringRunner;

	@Mock
	private MainSpringService service;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);

		assertNotNull(this.service);

		this.mainSpringRunner = new MainSpringRunner(this.service);
	}

	@Test
	void testRun() {
		AtomicBoolean calledInitialize = new AtomicBoolean();
		AtomicBoolean calledTick = new AtomicBoolean();
		ApplicationArguments args = new DefaultApplicationArguments();

		when(this.service.isAlive()).thenReturn(true);
		willAnswer(this.answerCalled(calledInitialize)).given(this.service).initialize(args);
		willAnswer(this.answerNotAlive(this.service, calledTick)).given(this.service).tick();

		assertDoesNotThrow(() -> this.mainSpringRunner.run(args));
		assertTrue(calledInitialize.get());
		assertTrue(calledTick.get());
	}

	private Answer<?> answerCalled(AtomicBoolean called) {
		return (Answer<Object>) invocation -> {
			called.set(true);
			return null;
		};
	}

	private Answer<?> answerNotAlive(MainSpringService service, AtomicBoolean calledTick) {
		return (Answer<Object>) invocation -> {
			calledTick.set(true);
			when(service.isAlive()).thenReturn(false);
			return null;
		};
	}

}
