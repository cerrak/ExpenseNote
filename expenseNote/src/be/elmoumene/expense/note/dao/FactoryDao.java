package be.elmoumene.expense.note.dao;

/**
 *
 * @author Mouaad El Moumène
 */
public abstract class FactoryDao {

	  public static PersonDao getPersonDao() {
	        return PersonDaoImpl.getInstance();
	    }

	  public static ExpenseDao getExpenseDao() {
		  return ExpenseDaoImpl.getInstance();
	  }

	  public static ExpenseNoteDao getExpenseNoteDao(){
		  return ExpenseNoteDaoImpl.getInstance();
	  }
}
