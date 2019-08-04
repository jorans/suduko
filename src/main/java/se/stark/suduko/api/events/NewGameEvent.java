package se.stark.suduko.api.events;

import java.util.List;

/**
 *
 */
public class NewGameEvent implements Event {
	private List<String> initialBoardConfiguration;
	private int size;

	public NewGameEvent(List<String> initialBoardConfiguration, int size) {
		this.initialBoardConfiguration = initialBoardConfiguration;
		this.size = size;
	}

	public List<String> getInitialBoardConfiguration() {
		return initialBoardConfiguration;
	}

	public int getSize() {
		return size;
	}
}
