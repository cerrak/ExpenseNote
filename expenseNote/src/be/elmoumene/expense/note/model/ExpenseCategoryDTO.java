package be.elmoumene.expense.note.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class ExpenseCategoryDTO {

	private final IntegerProperty expenseCategoryID;
	private final StringProperty expenseCategoryName;

	public ExpenseCategoryDTO(Integer expenseCategoryID, String expenseCategoryName){
		this.expenseCategoryID=expenseCategoryIDProperty();
		this.expenseCategoryName=expenseCategoryNameProperty();
	}

	public IntegerProperty expenseCategoryIDProperty() {
        return expenseCategoryID;
    }

    public Integer getExpenseCategoryID() {
        return expenseCategoryID.get();
    }

    public void setexpenseCategoryName(String expenseCategoryName) {
        this.expenseCategoryName.set(expenseCategoryName);
    }

    public StringProperty expenseCategoryNameProperty() {
        return expenseCategoryName;
    }

    public String getexpenseCategoryName() {
        return expenseCategoryName.get();
    }



}
