package be.elmoumene.expense.note.dao;

import java.util.List;

import be.elmoumene.expense.note.entity.Category;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public interface CategoryDao {

	public Category create(Category c) throws ExpenseNoteException;

	public Category update(Category c) throws ExpenseNoteException;

	public void delete(Category c) throws ExpenseNoteException;

	public Category getCategoryById(Long id) throws ExpenseNoteException;

	public List<Category> getCounties() throws ExpenseNoteException;

	public Category getCategoryByName(String name) throws ExpenseNoteException;

}
