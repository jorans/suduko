package se.stark.suduko.api.events;

/**
 *
 */
public interface EventAware {
	void handleEvent(Event event);
}
