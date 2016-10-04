package be.elmoumene.expense.note.controller;

import be.elmoumene.expense.note.controller.category.CategoryNewController;
import be.elmoumene.expense.note.controller.category.CategoryOverviewController;
import be.elmoumene.expense.note.controller.country.CountryNewController;
import be.elmoumene.expense.note.controller.country.CountryOverviewController;
import be.elmoumene.expense.note.view.ExpenseNoteOverviewController;
import be.elmoumene.expense.note.view.ExpenseOverviewController;
import be.elmoumene.expense.note.view.PersonNewDialogController;
import be.elmoumene.expense.note.view.PersonOverviewController;

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
	  
	  public static CategoryOverviewController getCategoryOverviewController(){
		  return CategoryOverviewController.getInstance();
	  }
	  
	  public static CategoryNewController getCategoryNewController(){
		  return CategoryNewController.getInstance();
	  }
}
