package be.elmoumene.expense.note.entity;

import java.util.ArrayList;
import java.util.List;

public enum Status {

	DRAFT("DRAFT"), SUBMITTED("SUBMITTED"), APPROVED("APPROVED"), VALIDATED("VALIDATED"), REJECTED("REJECTED");

	private final String name;

	private Status(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String toString() {
		return name;
	}

	public static Status fromString(String value) {

		switch (value) {
		case "REJECTED":
			return REJECTED;

		case "SUBMITTED":
			return SUBMITTED;

		case "APPROVED":
			return APPROVED;

		case "VALIDATED":
			return VALIDATED;

		default:
			return DRAFT;

		}
	}

	public static java.util.Collection<Status> literals() {
		final java.util.Collection<Status> literals = new java.util.ArrayList<Status>(values().length);
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
	 */

	public static List<Status> toList(String[] types) {
		List<Status> typeList = new ArrayList<Status>();
		for (String type : types) {
			typeList.add(Status.fromString(type));
		}
		return typeList;
	}
}
