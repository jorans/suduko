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
@Profile("small")
public class SmallGameConfiguration {
	@Bean
	public Integer boardSize(){
		return 2;
	}

	@Bean
	public List<String> initialBoardConfiguration() {
		List<String> initialBoardConfiguration = new ArrayList<>();
		initialBoardConfiguration.add("12  ");
		initialBoardConfiguration.add(" 32 ");
		initialBoardConfiguration.add("2   ");
		initialBoardConfiguration.add("3 14");
		return initialBoardConfiguration;
	}
}
