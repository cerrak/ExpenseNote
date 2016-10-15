package be.elmoumene.expense.note.service;

import be.elmoumene.expense.note.model.ControllerDTO;
import be.elmoumene.expense.note.model.PersonDTO;

/**
 *
 * @author Mouaad El Moumène
 */
public abstract class FactoryService {

	public static PersonService<PersonDTO> getPersonService() {
		return PersonServiceImpl.getInstance();
	}

	public static ExpenseService getExpenseService() {
		return ExpenseServiceImpl.getInstance();
	}

	public static ExpenseNoteService getExpenseNoteService() {
		return ExpenseNoteServiceImpl.getInstance();
	}

	public static CountryService getCountryService() {
		return CountryServiceImpl.getInstance();
	}

	public static CategoryService getCategoryService() {
		return CategoryServiceImpl.getInstance();
	}

	public static EntityService getEntityService() {
		return EntityServiceImpl.getInstance();
	}

	public static DepartmentService getDepartmentService() {
		return DepartmentServiceImpl.getInstance();
	}

	public static ControllerService<ControllerDTO> getControllerService() {
		return ControllerServiceImpl.getInstance();
	}
}
