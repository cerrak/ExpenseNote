package be.elmoumene.expense.note.dao;

import java.util.List;

import be.elmoumene.expense.note.entity.Expense;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public interface ExpenseDao {

	public Expense create(Expense e) throws ExpenseNoteException;

	public Expense update(Expense e) throws ExpenseNoteException;

	public void delete(Expense e);

	public Expense getExpenseById(Long id);

	public List<Expense> getExpenses();

	public List<Expense> getExpenses(Long expenseId);

	public List<Expense> getAvailableExpense(Long id);

	public List<Expense> getExpensesFromExpenseNoteId(Long id);


	/**
	 *  Supprime l'id de la note de frais contenu dans la/les dépense/s.
	 *
	 * @param expenseNoteId
	 * @author Mouaad
	 */
	public void detacheExpenseNote(Long expenseNoteId);

}
