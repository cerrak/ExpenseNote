package be.elmoumene.expense.note.view;

import be.elmoumene.expense.note.controller.country.CountryNewController;
import be.elmoumene.expense.note.controller.country.CountryOverviewController;

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

	  public static CountryOverviewController getCountryOverviewController(){
		  return CountryOverviewController.getInstance();
	  }
	  
	  public static CountryNewController getCountryNewController(){
		  return CountryNewController.getInstance();
	  }
}
