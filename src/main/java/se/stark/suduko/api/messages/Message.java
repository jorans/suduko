package se.stark.suduko.api.messages;

/**
 *
 */
public class Message {
	private String text;

	public static Message of(String text) {
		return new Message(text);
	}
	Message(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
