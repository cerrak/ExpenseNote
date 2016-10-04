package be.elmoumene.expense.note.service;

/**
 *
 * @author Mouaad El Moum�ne
 */
public abstract class FactoryService {

	  public static PersonService getPersonService() {
	        return PersonServiceImpl.getInstance();
	    }

	  public static ExpenseService getExpenseService() {
		  return ExpenseServiceImpl.getInstance();
	  }

	  public static ExpenseNoteService getExpenseNoteService(){
		  return ExpenseNoteServiceImpl.getInstance();
	  }
	  
	  public static CountryService getCountryService(){
		  return CountryServiceImpl.getInstance();
	  }
	  
	  public static CategoryService getCategoryService(){
		  return CategoryServiceImpl.getInstance();
	  }
}

