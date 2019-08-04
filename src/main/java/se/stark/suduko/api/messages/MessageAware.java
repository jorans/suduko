package se.stark.suduko.api.messages;


/**
 *
 */
public interface MessageAware {
	void publishMessage(Message message);
}
