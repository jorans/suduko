package se.stark.suduko.core;

import java.util.List;

/**
 *
 */
public interface GameService {
	Board newGame(int size, List<String> initialValues);

	Board getCurrentBoard();

	Board assignNumber(int row, int col, int value);
	Board removeNumber(int row, int col);


}
