package se.stark.suduko.api;

import se.stark.suduko.core.Board;

/**
 *
 */
public interface BoardUpdatedAware {
	void updated(Board board);
}
