package be.elmoumene.expense.note.dao;

import java.util.List;

import be.elmoumene.expense.note.entity.ExpenseNote;


public interface ExpenseNoteDao {

	public ExpenseNote create(ExpenseNote en) throws Exception;

	public ExpenseNote update(ExpenseNote en) throws Exception;

	public void delete(Long id);

	public ExpenseNote getExpenseNoteById(Long id);

	public List<ExpenseNote> getExpenseNotes();

	public List<ExpenseNote> getExpenseNotes(Long personId);

	public List<ExpenseNote> getExpenseNotesFromPerson(Long id);

}
