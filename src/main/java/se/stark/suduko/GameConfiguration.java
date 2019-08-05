package se.stark.suduko;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import se.stark.suduko.api.BoardUpdatedAware;
import se.stark.suduko.api.InputProvider;
import se.stark.suduko.api.events.EventAware;
import se.stark.suduko.api.messages.MessageAware;
import se.stark.suduko.console.ConsoleInput;
import se.stark.suduko.console.ConsoleOutput;
import se.stark.suduko.core.DefaultGameServiceImpl;
import se.stark.suduko.core.EventHandler;
import se.stark.suduko.core.GameService;

/**
 *
 */
@Configuration
public class GameConfiguration {
	@Bean
	GameService gameService() {
		return new DefaultGameServiceImpl();
	}

/*
	@Bean
	GameManager gameManager(GameService gameService) {
		return new GameManager(gameService);
	}

*/
	@Bean
	EventAware eventAware(GameService gameService, List<BoardUpdatedAware> boardUpdatedAwares, List<InputProvider> inputProviders, List<MessageAware> messageAwares) {
		return new EventHandler(boardUpdatedAwares, inputProviders, messageAwares, gameService);
	}

	@Bean
	ConsoleOutput consoleOutput() {
		return new ConsoleOutput();
	}

	@Bean
	ConsoleInput consoleInput(@Lazy EventAware eventAware) {
		return new ConsoleInput(eventAware);
	}
}
