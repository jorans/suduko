package se.stark.suduko.console;

import java.util.Scanner;

import se.stark.suduko.api.InputProvider;
import se.stark.suduko.api.events.AddNumberEvent;
import se.stark.suduko.api.events.EventAware;
import se.stark.suduko.api.events.QuitEvent;
import se.stark.suduko.api.events.RemoveNumberEvent;

/**
 *
 */
public class ConsoleInput implements InputProvider {
	private EventAware eventHandler;

	public ConsoleInput(EventAware eventHandler) {
		this.eventHandler = eventHandler;
	}

	public void input(){
		System.out.printf("Input command: ");
		Scanner scanner = new Scanner(System.in);
		String cmd = scanner.next();
		switch (cmd) {
			case "a": {
				int row = scanner.nextInt();
				int col = scanner.nextInt();
				int value = scanner.nextInt();
				eventHandler.handleEvent(new AddNumberEvent(row, col, value));
				break;
			}
			case "d": {
				int row = scanner.nextInt();
				int col = scanner.nextInt();
				eventHandler.handleEvent(new RemoveNumberEvent(row, col));
				break;
			}
			case "q":
				eventHandler.handleEvent(new QuitEvent());
				break;
			default:
				System.out.println("(a)dd <row> <col> <value>");
				System.out.println("(q)uit");
				input();
		}
	}
}
