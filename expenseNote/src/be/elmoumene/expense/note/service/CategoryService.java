package be.elmoumene.expense.note.service;

import java.util.List;

import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.CategoryDTO;


public interface CategoryService {

	public CategoryDTO create(CategoryDTO c) throws ExpenseNoteException;

	public CategoryDTO update(CategoryDTO c) throws ExpenseNoteException;

	public void delete(CategoryDTO c) throws ExpenseNoteException;

	public CategoryDTO getCategoryById(Long id) throws ExpenseNoteException;

	public List<CategoryDTO> getCategories() throws ExpenseNoteException;


}
