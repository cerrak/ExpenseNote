package be.elmoumene.expense.note.entity;

import java.util.ArrayList;
import java.util.List;

public enum UserRole {

	ADMIN("ADMIN"), USER("USER"), SUPERVISOR("SUPERVISOR"), CONTROLLER("CONTROLLER");

	private final String name;

	private UserRole(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String toString() {
		return name;
	}

	public static UserRole fromString(String value) {

		switch (value) {
		case "ADMIN":
			return ADMIN;

		case "SUPERVISOR":
			return SUPERVISOR;

		case "CONTROLLER":
			return CONTROLLER;

		default:
			return USER;

		}
	}

	public static java.util.Collection<UserRole> literals() {
		final java.util.Collection<UserRole> literals = new java.util.ArrayList<UserRole>(values().length);
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
	 * @author Mouaad
	 */
	public static List<UserRole> toList(String[] types) {
		List<UserRole> typeList = new ArrayList<UserRole>();
		for (String type : types) {
			typeList.add(UserRole.fromString(type));
		}
		return typeList;
	}
}
