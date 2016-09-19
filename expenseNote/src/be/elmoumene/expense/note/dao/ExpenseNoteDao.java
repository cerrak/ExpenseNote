package be.elmoumene.expense.note.dao;

import java.util.List;

import be.elmoumene.expense.note.entity.ExpenseNote;
import be.elmoumene.expense.note.exception.ExpenseNoteException;


public interface ExpenseNoteDao {

	public ExpenseNote create(ExpenseNote en) throws ExpenseNoteException;

	public ExpenseNote update(ExpenseNote en) throws ExpenseNoteException;

	public void delete(ExpenseNote en);

	public ExpenseNote getExpenseNoteById(Long id);

	public List<ExpenseNote> getExpenseNotes();

	public List<ExpenseNote> getExpenseNotes(Long personId);

	public List<ExpenseNote> getExpenseNotesFromPerson(Long id);

}
