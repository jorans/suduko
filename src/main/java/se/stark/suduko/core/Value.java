package se.stark.suduko.core;

import java.util.Objects;

/**
 *
 */
public class Value {
	static Value EMPTY = new Value(" ");

	private String value;

	private Value(String value) {
		this.value = value;
	}

	static Value of(String value) {
		if (EMPTY.value.equals(value)) {
			return EMPTY;
		}
		return new Value(value);
	}

	public String getValue() {
		return value;
	}

	boolean isEmpty() {
		return this == EMPTY;
	}
	public boolean isNotEmpty() {
		return !isEmpty();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Value value1 = (Value) o;
		return Objects.equals(value, value1.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
