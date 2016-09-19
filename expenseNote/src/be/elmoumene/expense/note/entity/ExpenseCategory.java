package be.elmoumene.expense.note.entity;

import java.util.ArrayList;
import java.util.List;

public enum ExpenseCategory {

	FOODANDDRINK("Food & Drink"), PARKING("Parking"), FUEL("Fuel"), COMPUTERSH("Computer SH");

	private final String name;

	private ExpenseCategory(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String toString() {
		return name;
	}

	public static ExpenseCategory fromString(String value) {

		switch (value) {
		case "Food & Drink":
			return FOODANDDRINK;

		case "Parking":
			return PARKING;

		case "Fuel":
			return FUEL;

		default:
			return COMPUTERSH;

		}
	}

	public static java.util.Collection<ExpenseCategory> literals() {
		final java.util.Collection<ExpenseCategory> literals = new java.util.ArrayList<ExpenseCategory>(values().length);
		for (int i = 0; i < values().length; i++) {
			literals.add(values()[i]);
		}
		return literals;
	}

	/**
	 * Return a Collection of all literal values for this enumeration
	 *
	 * @return java.util.Collection literal values
	 */
	public static java.util.Collection<String> literalsCode() {
		final java.util.Collection<String> literalsCode = new java.util.ArrayList<String>(values().length);
		for (int i = 0; i < values().length; i++) {
			literalsCode.add(values()[i].toString());
		}
		return literalsCode;
	}

	/**
	 * Convert table to List L_EventType.
	 *
	 * @param types
	 *            the types table
	 * @return list of L_EventType
	 *
	 * @author sr
	 */
	public static List<ExpenseCategory> toList(String[] types) {
		List<ExpenseCategory> typeList = new ArrayList<ExpenseCategory>();
		for (String type : types) {
			typeList.add(ExpenseCategory.fromString(type));
		}
		return typeList;
	}
}

