package be.elmoumene.expense.note.dao;

public interface PersonDao<T> extends GenericDao<T>{

	public T getByEmail(String email);

}
