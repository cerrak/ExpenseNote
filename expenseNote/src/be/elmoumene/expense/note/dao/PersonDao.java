package be.elmoumene.expense.note.dao;

public interface PersonDao<Person> extends GenericDao<Person>{

	public Person getByEmail(String email);

}
