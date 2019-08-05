package se.stark.suduko.console;

import static java.lang.String.format;

import java.util.List;

import se.stark.suduko.api.BoardUpdatedAware;
import se.stark.suduko.api.messages.Message;
import se.stark.suduko.api.messages.MessageAware;
import se.stark.suduko.core.Board;
import se.stark.suduko.core.Value;

/**
 *
 */
public class ConsoleOutput implements BoardUpdatedAware, MessageAware {
	private static final char START_SE_THIN = '\u2514';
	private static final char SPACER_THIN = '─';
	private static final char START_E_THIN = '├';
	private static final char DIVIDER_THIN = '┼';
	private static final char BLOCK_DIVIDER_THIN = '╫';
	private static final char END_W_THIN = '┤';
	private static final char START_NE_THIN = '┌';
	private static final char START_E_THICK = '\u255E';
	private static final char SPACER_THICK = '\u2550';
	private static final char DIVIDER_THICK = '\u256A';
	private static final char BLOCK_DIVIDER_THICK = '\u256C';
	private static final char END_W_THICK = '\u2561';
	private static final char DIVIDER_N_THIN = '┬';
	private static final char BLOCK_DIVIDER_N_THICK = '╥';
	private static final char END_NE_THIN = '┐';
	private static final char DIVIDER_S_THIN = '\u2534';
	private static final char BLOCK_DIVIDER_S_THICK = '\u2568';
	private static final char END_SE_THIN = '\u2518';

	@Override
	public void updated(Board board) {
		System.out.println(toString(board));
	}

	@Override
	public void publishMessage(Message message) {
		System.out.println("Message: " + message.getText());
	}

	public String toString(Board board) {
		List<List<Value>> values = board.getValues();
		int size = board.getSize();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < values.size(); i++) {
			if (i == 0) {
				buf.append(getRowSeparator(size, START_NE_THIN, SPACER_THIN, DIVIDER_N_THIN, BLOCK_DIVIDER_N_THICK, END_NE_THIN));
			} else if(i % size == 0){
				buf.append(getRowSeparator(size, START_E_THICK, SPACER_THICK, DIVIDER_THICK, BLOCK_DIVIDER_THICK, END_W_THICK));
			} else {
				buf.append(getRowSeparator(size, START_E_THIN, SPACER_THIN, DIVIDER_THIN, BLOCK_DIVIDER_THIN, END_W_THIN));
			}

			List<Value> row = values.get(i);
			for (int j = 0; j <= row.size(); j++) {
				if  (j == 0) {
					buf.append(format("│ %s ", row.get(j).getValue()));
				} else if  (j == size*size) {
					buf.append("│");
				} else if  (j % size == 0) {
					buf.append(format("║ %s ", row.get(j).getValue()));
				} else {
					buf.append(format("│ %s ", row.get(j).getValue()));
				}
			}
			buf.append('\n');
		}
		buf.append(getRowSeparator(size, START_SE_THIN, SPACER_THIN, DIVIDER_S_THIN, BLOCK_DIVIDER_S_THICK, END_SE_THIN));

		return buf.toString();
	}

	private String getRowSeparator(int size, char start, char spacer, char divider, char blockDivider, char end) {
		StringBuffer rowSeparator = new StringBuffer();
		for (int i = 0; i <= size * size; i++) {
			if  (i == 0) {
				rowSeparator.append(start);
				rowSeparator.append(spacer);
				rowSeparator.append(spacer);
				rowSeparator.append(spacer);
			} else if  (i == size*size) {
				rowSeparator.append(end);
			} else if  (i % size == 0) {
				rowSeparator.append(blockDivider);
				rowSeparator.append(spacer);
				rowSeparator.append(spacer);
				rowSeparator.append(spacer);
			} else {
				rowSeparator.append(divider);
				rowSeparator.append(spacer);
				rowSeparator.append(spacer);
				rowSeparator.append(spacer);

			}
		}
		rowSeparator.append("\n");
		return rowSeparator.toString();
	}
}
