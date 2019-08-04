package se.stark.suduko.api.events;

/**
 *
 */
public interface EventAware {
	public void handleEvent(Event command);
}
