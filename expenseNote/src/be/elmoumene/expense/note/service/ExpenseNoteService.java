package be.elmoumene.expense.note.service;

import java.util.List;

import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.ExpenseNoteDTO;
import be.elmoumene.expense.note.model.PersonDTO;

public interface ExpenseNoteService {

	public ExpenseNoteDTO create(ExpenseNoteDTO en) throws ExpenseNoteException;

	public ExpenseNoteDTO update(ExpenseNoteDTO en) throws ExpenseNoteException;

	public void delete(ExpenseNoteDTO en);

	public ExpenseNoteDTO getExpenseNoteById(Long id);

	public List<ExpenseNoteDTO> getExpenseNotes();

	public List<ExpenseNoteDTO> getExpenseNotesFromPerson(PersonDTO personDto);



}
