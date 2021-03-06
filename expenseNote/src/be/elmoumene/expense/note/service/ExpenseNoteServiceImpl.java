package be.elmoumene.expense.note.service;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import be.elmoumene.expense.note.controller.FactoryController;
import be.elmoumene.expense.note.dao.ExpenseDao;
import be.elmoumene.expense.note.dao.ExpenseNoteDao;
import be.elmoumene.expense.note.dao.FactoryDao;
import be.elmoumene.expense.note.entity.Department;
import be.elmoumene.expense.note.entity.ExpenseNote;
import be.elmoumene.expense.note.model.ControllerDTO;
import be.elmoumene.expense.note.model.DepartmentDTO;
import be.elmoumene.expense.note.model.EntityDTO;
import be.elmoumene.expense.note.model.ExpenseNoteDTO;
import be.elmoumene.expense.note.model.PersonDTO;

public class ExpenseNoteServiceImpl implements ExpenseNoteService {

	private static ExpenseNoteService uniqueInstance = new ExpenseNoteServiceImpl();

	private ExpenseNoteDao expenseNoteDao = FactoryDao.getExpenseNoteDao();
	private ExpenseDao expenseDao = FactoryDao.getExpenseDao();
	private ControllerService<ControllerDTO> controllerService = FactoryService.getControllerService();

	public static ExpenseNoteService getInstance() {
		return uniqueInstance;
	}

	@Override
	public ExpenseNoteDTO create(ExpenseNoteDTO dto) throws Exception {
		// Business logic

		ExpenseNote entity = expenseNoteDao.create(ExpenseNoteDTO.toEntity(dto));

		return ExpenseNoteDTO.toDto(entity);
	}

	@Override
	public ExpenseNoteDTO update(ExpenseNoteDTO en) throws Exception {
		// Business Logic

		ExpenseNote entity = expenseNoteDao.update(ExpenseNoteDTO.toEntity(en));

		return ExpenseNoteDTO.toDto(entity);
	}

	@Override
	public void delete(ExpenseNoteDTO en) {
		// 1)  supprime l'id de la note de frais contenu dans la/les d�pense/s.
		expenseDao.detacheExpenseNote(en.getId());

		// 2)supprimer la note de frais
		expenseNoteDao.delete(en.getId());
	}

	@Override
	public ExpenseNoteDTO getExpenseNoteById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExpenseNoteDTO> getExpenseNotes(PersonDTO personDto) {

		List<ExpenseNoteDTO> ExpenseNotesDto = new ArrayList<ExpenseNoteDTO>();

		List<ExpenseNote> entities = new ArrayList<ExpenseNote>();

		switch(personDto.getUserRole()){

		case USER : entities = expenseNoteDao.getExpenseNotesFromPerson(personDto.getId());

			break;
		case SUPERVISOR : entities = expenseNoteDao.getExpenseNotesForSupervisor(DepartmentDTO.toEntity(personDto.getDepartment()));

			break;
		case CONTROLLER :
			ControllerDTO controllerDto = controllerService.getById(personDto.getId());
			entities = expenseNoteDao.getExpenseNotesForController(EntityDTO.toEntity(controllerDto.getEntity()));
			break;
		default :
			break;
		}

		entities.forEach(entity -> {
				entity.setExpenses(expenseDao.getExpensesFromExpenseNoteId(entity.getId()));
				ExpenseNotesDto.add(ExpenseNoteDTO.toDto(entity));
			}
		);

		return ExpenseNotesDto;
	}



}
