package se.stark.suduko.api.events;

/**
 *
 */
public class RemoveNumberEvent implements Event {
	int row;
	int col;

	public RemoveNumberEvent(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

}
