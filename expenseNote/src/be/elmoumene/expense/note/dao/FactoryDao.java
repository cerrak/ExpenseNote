package be.elmoumene.expense.note.dao;

import be.elmoumene.expense.note.entity.Controller;
import be.elmoumene.expense.note.entity.Person;

/**
 *
 * @author Mouaad El Moumène
 */
public abstract class FactoryDao {

	public static PersonDao<Person> getPersonDao() {
		return (PersonDao<Person>) PersonDaoImpl.getInstance();
	}
	
	public static GenericDao<Controller> getControllerDao() {
		return ControllerDaoImpl.getInstance();
	}

	public static ExpenseDao getExpenseDao() {
		return ExpenseDaoImpl.getInstance();
	}

	public static ExpenseNoteDao getExpenseNoteDao() {
		return ExpenseNoteDaoImpl.getInstance();
	}

	public static EntityDao getEntityDao() {
		return EntityDaoImpl.getInstance();
	}

	public static CountryDao getCountryDao() {
		return CountryDaoImpl.getInstance();
	}

	public static CategoryDao getCategoryDao() {
		return CategoryDaoImpl.getInstance();
	}

	public static DepartmentDao getDepartmentDao(){
		return DepartmentDaoImpl.getInstance();
	}

}
