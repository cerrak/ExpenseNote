package be.elmoumene.expense.note.entity;

import java.util.ArrayList;
import java.util.List;

public enum Country {

	Belgium("Belgium"), France("France"), Germany("Germany"), Luxembourg("Luxembourg"),
	Netherlands("Netherlands"), Bulgaria("Bulgaria"), Croatia("Croatia"), Romania("Romania"), Denmark("Denmar"),
	Spain("Spain"), Italy("Italy"), Greece("Greece");

	private final String name;

	private Country(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String toString() {
		return name;
	}

	public static Country fromString(String value) {

		switch (value) {
		case "France":
			return France;

		case "Germany":
			return Germany;

		case "Luxembourg":
			return Luxembourg;

		case "Netherlands":
			return Netherlands;

		case "Bulgaria":
			return Bulgaria;

		case "Croatia":
			return Croatia;

		case "Romania":
			return Romania;

		case "Denmark":
			return Denmark;

		case "Spain":
			return Spain;

		case "Italy":
			return Italy;

		case "Greece":
			return Greece;

		default:
			return Belgium;

		}
	}

	public static java.util.Collection<Country> literals() {
		final java.util.Collection<Country> literals = new java.util.ArrayList<Country>(values().length);
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
	public static List<Country> toList(String[] types) {
		List<Country> typeList = new ArrayList<Country>();
		for (String type : types) {
			typeList.add(Country.fromString(type));
		}
		return typeList;
	}
}

