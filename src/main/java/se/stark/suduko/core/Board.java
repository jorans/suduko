package se.stark.suduko.core;

import static se.stark.suduko.core.Value.EMPTY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class Board {
	private int size;

	private List<List<Pos>> indicies;

	private List<List<Value>> values;

	public Board(int size) {
		this.size = size;
		indicies = getIndicies(size);
		values = getInitialValues(size);
	}

	private Board(Builder builder) {
		this.size = builder.size;
		values = builder.values;
		indicies = getIndicies(this.size);
	}
	private Board(Board board, int row, int col, int value) {
		this.size = board.size;
		indicies = board.indicies;
		values = new ArrayList<>(size*size);

		for (int rowCounter = 0; rowCounter < board.values.size(); rowCounter++) {
			List<Value> rowValues = new ArrayList<>(size*size);
			for (int colCounter = 0; colCounter < board.values.get(rowCounter).size(); colCounter++) {
				if (rowCounter == row && colCounter == col) {
					rowValues.add(Value.of(String.valueOf(value)));
				} else {
					rowValues.add(board.values.get(rowCounter).get(colCounter));
				}
			}
			values.add(rowValues);
		}

	}

	public static Builder builder() {
		return new Builder();
	}

	public List<List<Value>> getValues() {
		return values;
	}

	public int getSize() {
		return size;
	}

	private List<List<Value>> getInitialValues(int size) {
		int numOfRows = size*size;
		int numOfCols = size*size;
		List<List<Value>> values = new ArrayList<>();
		for (int i = 0; i < numOfRows; i++) {
			List<Value> rowValues = new ArrayList<>();
			for (int j = 0; j < numOfCols; j++) {
				rowValues.add(EMPTY);
			}
			values.add(rowValues);
		}
		return values;
	}

	public boolean isValid() {
		for (List<Pos> indicy : indicies) {
			Set<Value> valuesFound = new HashSet<>();
			for (Pos pos : indicy) {
				Value value = values.get(pos.row).get(pos.col);
				if (value.isEmpty()) {
					continue;
				} else if (valuesFound.contains(value)) {
					System.out.printf("Value %s found multiple times%n", value.getValue());
					return false;
				}
				valuesFound.add(value);
			}
		}
		return true;
	}

	public Board add(int row, int col, int value) {
		return new Board(this, row, col, value);
	}

	public Board remove(int row, int col) {
		List<List<Value>> newRows = new ArrayList<>();
		for (int rowCounter = 0; rowCounter < this.values.size(); rowCounter++) {
			List<Value> newRow = new ArrayList<>();
			for (int colCounter = 0; colCounter < this.values.get(rowCounter).size(); colCounter++) {
				if (row == rowCounter && col == colCounter) {
					newRow.add(EMPTY);
				} else {
					newRow.add(this.values.get(rowCounter).get(colCounter));
				}
			}
			newRows.add(newRow);
		}
		return Board.builder()
				.size(this.size)
				.values(newRows)
				.build();
	}


	private List<List<Pos>> getIndicies(int size) {
		int numOfRows = size*size;
		int numOfCols = size*size;

		List<List<Pos>> allIndicies = new ArrayList<>();
		for (int row = 0; row < numOfRows; row++) {
			List<Pos> rowIndicies = new ArrayList<>();
			for(int col = 0; col < numOfCols; col++){
				rowIndicies.add(pos(row, col));
			}
			allIndicies.add(rowIndicies);
		}

		for (int col = 0; col < numOfCols; col++) {
			List<Pos> colIndicies = new ArrayList<>();
			for(int row = 0; row < numOfRows; row++){
				colIndicies.add(pos(row, col));
			}
			allIndicies.add(colIndicies);
		}

		/*
		*  0  1  |  4  5
		*  2  3  |  6  7
		* -------+-------
		*  8  9  | 12 13
		* 10 11  | 14 15
		*
		*  0 -> 0:0 1 -> 0:1   2 -> 1:0   3 -> 1:1
		*  4 -> 0:2 5 -> 0:3   6 -> 1:2   7 -> 1:3
		*  8 -> 2:0 9 -> 2:1  10 -> 3:0  11 -> 3:1
		* 12 -> 2:2 13 -> 2:3 14 -> 3:2  15 -> 3:3
		*
		* */
		for (int quadrantRow = 0; quadrantRow < size; quadrantRow++) {
			for (int quadrantCol = 0; quadrantCol < size; quadrantCol++) {
				List<Pos> quadrantIndicies = new ArrayList<>();
				for (int row = 0; row < size; row++) {
					for (int col = 0; col < size; col++) {
						quadrantIndicies.add(pos((quadrantRow * size) + row, (quadrantCol * size) + col));
					}
				}
				allIndicies.add(quadrantIndicies);
			}
		}
		return allIndicies;
	}
	private static Pos pos(int row, int col) {
		return new Pos(row, col);
	}

	private static class Pos {
		public Pos(int row, int col) {
			this.row = row;
			this.col = col;
		}

		int row;
		int col;

		@Override
		public String toString() {
			return "Pos{" +
					"row=" + row +
					", col=" + col +
					'}';
		}
	}

	public static class Builder {
		private int size;
		private List<List<Value>> values;

		public Builder size(int size){
			this.size = size;
			return this;
		}
		public Builder values(List<List<Value>> values) {
			this.values = values;
			return this;
		}

		public Builder rows(List<String> rows) {
			Builder b = this;
			for (String row : rows) {
				b = b.row(row);
			}
			return b;

		}
		public Builder row(String rowValues) {
			if (values == null) {
				values = new ArrayList<>();
			}
			List<Value> rowValues2 = new ArrayList<>();
			values.add(Stream.of(rowValues.split("(?!^)"))
					.map(Value::of)
					.collect(Collectors.toList()));

			return this;
		}

		public Board build() {
			if (values.size() != size*size)
				throw new IllegalStateException(String.format("Number of rows (%d) does not correspond to the given size (%d)", values.size(), size));

			for (List<Value> value : values) {
				if (values.size() != size*size)
					throw new IllegalStateException("Number of elements in row " + value + " does not correspond to the given size");
			}
			return new Board(this);
		}
	}
}
