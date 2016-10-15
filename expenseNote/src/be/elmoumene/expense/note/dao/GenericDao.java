package be.elmoumene.expense.note.dao;

import java.util.List;

import be.elmoumene.expense.note.exception.ExpenseNoteException;

public interface GenericDao<T> {

	public T create(T t) throws ExpenseNoteException;

	public T update(T t)throws ExpenseNoteException;

	public void delete(T t) throws ExpenseNoteException;

	public T getById(Long id);

	public List<T> getList();
	
}
