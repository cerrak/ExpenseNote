package be.elmoumene.expense.note.dao;

public interface SupervisorDao<T> extends GenericDao<T>{

	public T getByEmail(String email);

}
