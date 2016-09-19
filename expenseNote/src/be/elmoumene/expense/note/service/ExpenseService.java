package be.elmoumene.expense.note.service;

import java.util.List;

import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.ExpenseDTO;
import be.elmoumene.expense.note.model.PersonDTO;

public interface ExpenseService {

	public ExpenseDTO create(ExpenseDTO e) throws ExpenseNoteException;

	public ExpenseDTO update(ExpenseDTO e) throws ExpenseNoteException;

	public void delete(ExpenseDTO e);

	public ExpenseDTO getExpenseById(Long id);

	public List<ExpenseDTO> getExpenses();

	public List<ExpenseDTO> getAvailableExpense(PersonDTO personDto);

	public List<ExpenseDTO> getExpensesFromExpenseNoteId(Long id);


}
