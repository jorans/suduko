package se.stark.suduko.core;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class DefaultGameServiceImpl implements GameService {
	Board board;

	@Override
	public Board newGame(int size, List<String> initialValues) {
		board = Board.builder()
				.size(size)
				.rows(initialValues)
				.build();
		return board;
	}

	@Override
	public Board getCurrentBoard() {
		return board;
	}

	@Override
	public Board assignNumber(int row, int col, int value) {
		board = board.add(row, col,value);
		return board;
	}
	@Override
	public Board removeNumber(int row, int col) {
		board = board.remove(row, col);
		return board;
	}
}
