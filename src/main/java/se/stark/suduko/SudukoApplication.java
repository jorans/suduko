package se.stark.suduko;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import se.stark.suduko.api.events.EventAware;
import se.stark.suduko.api.events.NewGameEvent;
import se.stark.suduko.console.ConsoleInput;
import se.stark.suduko.console.ConsoleOutput;
import se.stark.suduko.core.DefaultGameServiceImpl;
import se.stark.suduko.core.GameService;

@SpringBootApplication
public class SudukoApplication implements CommandLineRunner {
	@Autowired
	EventAware eventHandler;
	@Autowired
	private Integer boardSize;
	@Autowired
	private List initialBoardConfiguration;


	public static void main(String[] args) {
		SpringApplication.run(SudukoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting");

		config2();
	}

	private void config2() {
		eventHandler.handleEvent(new NewGameEvent(initialBoardConfiguration, boardSize));
	}

	private void config1() {
		GameService gameService = new DefaultGameServiceImpl();
		GameManager gameManager = new GameManager(gameService);

		ConsoleOutput output = new ConsoleOutput();
		gameManager.addBoardUpdatedListener(output);
		gameManager.addMessageListener(output);

		ConsoleInput input = new ConsoleInput(gameManager);
		gameManager.addInputProvider(input);

		List<String> initialBoardConfiguration = new ArrayList<>();
		initialBoardConfiguration.add("12  ");
		initialBoardConfiguration.add(" 32 ");
		initialBoardConfiguration.add("2   ");
		initialBoardConfiguration.add("3 1 ");
		gameManager.handleEvent(new NewGameEvent(initialBoardConfiguration, 2));

		List<String> initialBoardConfiguration2 = new ArrayList<>();
		initialBoardConfiguration2.add("12       ");
		initialBoardConfiguration2.add(" 32      ");
		initialBoardConfiguration2.add("2        ");
		initialBoardConfiguration2.add("3 1      ");
		initialBoardConfiguration2.add("         ");
		initialBoardConfiguration2.add("         ");
		initialBoardConfiguration2.add("         ");
		initialBoardConfiguration2.add("         ");
		initialBoardConfiguration2.add("         ");

//		gameManager.handleEvent(new NewGameEvent(initialBoardConfiguration2, 3));
	}


}
