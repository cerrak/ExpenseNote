package be.elmoumene.expense.note.service;

import java.util.ArrayList;
import java.util.List;

import be.elmoumene.expense.note.dao.ExpenseDao;
import be.elmoumene.expense.note.dao.FactoryDao;
import be.elmoumene.expense.note.entity.Expense;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.ExpenseDTO;
import be.elmoumene.expense.note.model.PersonDTO;

public class ExpenseServiceImpl implements ExpenseService {

	private static ExpenseService uniqueInstance = new ExpenseServiceImpl();

	private ExpenseDao expenseDao = FactoryDao.getExpenseDao();


	public static ExpenseService getInstance() {
		return uniqueInstance;
	}

	@Override
	public ExpenseDTO create(ExpenseDTO dto) throws ExpenseNoteException {
		// Business logic

		Expense entity = expenseDao.create(ExpenseDTO.expenseToEntity(dto));

		return ExpenseDTO.expenseToModel(entity);
	}

	@Override
	public ExpenseDTO update(ExpenseDTO e) throws ExpenseNoteException {
		// Business Logic

		Expense entity = expenseDao.update(ExpenseDTO.expenseToEntity(e));

		return ExpenseDTO.expenseToModel(entity);
	}

	@Override
	public void delete(ExpenseDTO e) {
		// TODO Auto-generated method stub

	}

	@Override
	public ExpenseDTO getExpenseById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExpenseDTO> getAvailableExpense(PersonDTO personDto) { //question 4

		List<ExpenseDTO> expensesDto = new ArrayList<ExpenseDTO>();

		List<Expense> entities = expenseDao.getAvailableExpense(personDto.getId());

		if(entities!=null)
			entities.forEach(entity -> expensesDto.add(ExpenseDTO.expenseToModel(entity)));

		return expensesDto;
	}

	@Override
	public List<ExpenseDTO> getExpenses() {
		List<ExpenseDTO> expensesDto = new ArrayList<ExpenseDTO>();

		List<Expense> entities = expenseDao.getExpenses();
		
		if(entities!=null)
			entities.forEach(entity -> expensesDto.add(ExpenseDTO.expenseToModel(entity)));

		return expensesDto;
	}

	@Override
	public List<ExpenseDTO> getExpensesFromExpenseNoteId(Long id) {
		List<ExpenseDTO> expensesDto = new ArrayList<ExpenseDTO>();

		List<Expense> entities = expenseDao.getExpensesFromExpenseNoteId(id);
		
		if(entities!=null)
			entities.forEach(entity -> expensesDto.add(ExpenseDTO.expenseToModel(entity)));

		return expensesDto;
	}

}
