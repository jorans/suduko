package se.stark.suduko.core;

import java.util.List;

import se.stark.suduko.api.BoardUpdatedAware;
import se.stark.suduko.api.InputProvider;
import se.stark.suduko.api.events.AddNumberEvent;
import se.stark.suduko.api.events.Event;
import se.stark.suduko.api.events.EventAware;
import se.stark.suduko.api.events.NewGameEvent;
import se.stark.suduko.api.events.QuitEvent;
import se.stark.suduko.api.events.RemoveNumberEvent;
import se.stark.suduko.api.messages.Message;
import se.stark.suduko.api.messages.MessageAware;

/**
 *
 */
public class EventHandler implements EventAware {
	List<BoardUpdatedAware> boardUpdatedListeners;
	List<InputProvider> inputProviders;
	List<MessageAware> messageListeners;
	GameService gameService;

	public EventHandler(List<BoardUpdatedAware> boardUpdatedListeners, List<InputProvider> inputProviders, List<MessageAware> messageListeners, GameService gameService) {
		this.boardUpdatedListeners = boardUpdatedListeners;
		this.inputProviders = inputProviders;
		this.messageListeners = messageListeners;
		this.gameService = gameService;
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
