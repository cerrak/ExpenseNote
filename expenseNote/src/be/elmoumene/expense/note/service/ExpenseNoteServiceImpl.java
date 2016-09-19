package be.elmoumene.expense.note.service;

import java.util.ArrayList;
import java.util.List;

import be.elmoumene.expense.note.dao.ExpenseNoteDao;
import be.elmoumene.expense.note.dao.FactoryDao;
import be.elmoumene.expense.note.entity.ExpenseNote;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.ExpenseNoteDTO;
import be.elmoumene.expense.note.model.PersonDTO;

public class ExpenseNoteServiceImpl implements ExpenseNoteService {

	private static ExpenseNoteService uniqueInstance = new ExpenseNoteServiceImpl();

	private ExpenseNoteDao ExpenseNoteDao = FactoryDao.getExpenseNoteDao();

	public static ExpenseNoteService getInstance() {
		return uniqueInstance;
	}

	@Override
	public ExpenseNoteDTO create(ExpenseNoteDTO dto) throws ExpenseNoteException {
		// Business logic

		ExpenseNote entity = ExpenseNoteDao.create(ExpenseNoteDTO.toEntity(dto));

		return ExpenseNoteDTO.toDto(entity);
	}

	@Override
	public ExpenseNoteDTO update(ExpenseNoteDTO en) throws ExpenseNoteException {
		// Business Logic

		ExpenseNote entity = ExpenseNoteDao.update(ExpenseNoteDTO.toEntity(en));

		return ExpenseNoteDTO.toDto(entity);
	}

	@Override
	public void delete(ExpenseNoteDTO en) {
		// TODO Auto-generated method stub

	}

	@Override
	public ExpenseNoteDTO getExpenseNoteById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExpenseNoteDTO> getExpenseNotesFromPerson(PersonDTO personDto) {

		List<ExpenseNoteDTO> ExpenseNotesDto = new ArrayList<ExpenseNoteDTO>();

		List<ExpenseNote> entities = ExpenseNoteDao.getExpenseNotesFromPerson(personDto.getId());

		entities.forEach(entity -> ExpenseNotesDto.add(ExpenseNoteDTO.toDto(entity)));

		return ExpenseNotesDto;
	}

	@Override
	public List<ExpenseNoteDTO> getExpenseNotes() {
		List<ExpenseNoteDTO> expenseNotesDto = new ArrayList<ExpenseNoteDTO>();

		List<ExpenseNote> entities = ExpenseNoteDao.getExpenseNotes();

		entities.forEach(entity -> expenseNotesDto.add(ExpenseNoteDTO.toDto(entity)));

		return expenseNotesDto;
	}


}
