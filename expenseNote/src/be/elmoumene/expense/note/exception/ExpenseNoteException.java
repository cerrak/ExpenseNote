package be.elmoumene.expense.note.exception;

public class ExpenseNoteException extends Exception {

	public ExpenseNoteException(String message, Exception exception) {
		super(message, exception);
	}

	public ExpenseNoteException(String string) {
		super(string);
	}


}
