package be.elmoumene.expense.note.view;

/**
 *
 * @author Mouaad El Moumène
 */
public class FactoryController {

	  public static PersonNewDialogController getPersonNewDialog() {
		  return PersonNewDialogController.getInstance();
	  }

	  public static PersonOverviewController getPersonOverviewController() {
		  return PersonOverviewController.getInstance();
	    }

	  public static ExpenseOverviewController getExpenseOverviewController(){
		  return ExpenseOverviewController.getInstance();
	  }

	  public static ExpenseNoteOverviewController getExpenseNoteOverviewController(){
		  return ExpenseNoteOverviewController.getInstance();
	  }


}
