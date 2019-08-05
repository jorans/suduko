package se.stark.suduko;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *
 */
@Configuration
@Profile("large")
public class LargeGameConfiguration {
	@Bean
	public Integer boardSize(){
		return 3;
	}

	@Bean
	public List<String> initialBoardConfiguration() {
		List<String> initialBoardConfiguration = new ArrayList<>();
		initialBoardConfiguration.add("12       ");
		initialBoardConfiguration.add(" 32      ");
		initialBoardConfiguration.add("2        ");
		initialBoardConfiguration.add("3 1      ");
		initialBoardConfiguration.add("         ");
		initialBoardConfiguration.add("         ");
		initialBoardConfiguration.add("         ");
		initialBoardConfiguration.add("         ");
		initialBoardConfiguration.add("         ");
		return initialBoardConfiguration;
	}
}
