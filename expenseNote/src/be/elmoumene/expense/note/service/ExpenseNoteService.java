package be.elmoumene.expense.note.service;

import java.util.List;

import be.elmoumene.expense.note.model.ExpenseNoteDTO;
import be.elmoumene.expense.note.model.PersonDTO;

public interface ExpenseNoteService {

	public ExpenseNoteDTO create(ExpenseNoteDTO en) throws Exception;

	public ExpenseNoteDTO update(ExpenseNoteDTO en) throws Exception;

	public void delete(ExpenseNoteDTO en);

	public ExpenseNoteDTO getExpenseNoteById(Long id);

	public List<ExpenseNoteDTO> getExpenseNotes();

	public List<ExpenseNoteDTO> getExpenseNotesFromPerson(PersonDTO personDto);



}
