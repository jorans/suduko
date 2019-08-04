package se.stark.suduko.api.events;

/**
 *
 */
public class AddNumberEvent implements Event {
	int row;
	int col;
	int value;

	public AddNumberEvent(int row, int col, int value) {
		this.row = row;
		this.col = col;
		this.value = value;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getValue() {
		return value;
	}
}
