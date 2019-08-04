package se.stark.suduko;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import se.stark.suduko.api.events.AddNumberEvent;
import se.stark.suduko.api.events.Event;
import se.stark.suduko.api.events.EventAware;
import se.stark.suduko.api.events.NewGameEvent;
import se.stark.suduko.api.events.QuitEvent;
import se.stark.suduko.api.events.RemoveNumberEvent;
import se.stark.suduko.api.messages.Message;
import se.stark.suduko.api.messages.MessageAware;
import se.stark.suduko.core.Board;
import se.stark.suduko.api.BoardUpdatedAware;
import se.stark.suduko.core.GameService;
import se.stark.suduko.api.InputProvider;

/**
 *
 */
@Component
public class GameManager implements EventAware {
	List<BoardUpdatedAware> boardUpdatedListeners;
	List<InputProvider> inputProviders;
	List<MessageAware> messageListeners;
	GameService gameService;


	public GameManager(GameService gameService) {
		this.gameService = gameService;
		this.boardUpdatedListeners = new ArrayList<>();
		this.inputProviders = new ArrayList<>();
		this.messageListeners = new ArrayList<>();
	}

	@Override
	public void handleEvent(Event event) {
		if(event instanceof AddNumberEvent) {
			AddNumberEvent evt = (AddNumberEvent) event;
			Board nextBoard = gameService.assignNumber(evt.getRow(), evt.getCol(), evt.getValue());
			notifyBoardUpdatedListeners(nextBoard);
			notifyMessageListeners(Message.of(String.format("Added %d at %d:%d", evt.getValue(), evt.getRow(), evt.getCol())));
			notifyInputProviders();
		} else
		if(event instanceof RemoveNumberEvent){
			RemoveNumberEvent evt = (RemoveNumberEvent) event;
			Board nextBoard = gameService.removeNumber(evt.getRow(), evt.getCol());
			notifyBoardUpdatedListeners(nextBoard);
			notifyMessageListeners(Message.of(String.format("Removed %d:%d", evt.getRow(), evt.getCol())));
			notifyInputProviders();

		} else if (event instanceof NewGameEvent) {
			NewGameEvent evt = (NewGameEvent) event;
			Board board = gameService.newGame(evt.getSize(), evt.getInitialBoardConfiguration());
			notifyBoardUpdatedListeners(board);
			notifyInputProviders();
		} else if (event instanceof QuitEvent) {
			notifyMessageListeners(Message.of("Bye for now!"));
			System.exit(0);
		}
	}

	public void addBoardUpdatedListener(BoardUpdatedAware boardUpdatedAware) {
		boardUpdatedListeners.add(boardUpdatedAware);
	}

	public void addInputProvider(InputProvider inputProvider){
		inputProviders.add(inputProvider);
	}

	public void addMessageListener(MessageAware messageAware) {
		messageListeners.add(messageAware);
	}
	private void notifyBoardUpdatedListeners(Board board) {
		boardUpdatedListeners.forEach(boardUpdatedAware -> boardUpdatedAware.updated(board));
	}
	private void notifyInputProviders() {
		inputProviders.forEach(provider -> provider.input());
	}

	private void notifyMessageListeners(Message message) {
		messageListeners.forEach(messageAware -> messageAware.publishMessage(message));
	}
}
