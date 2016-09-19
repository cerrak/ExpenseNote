package be.elmoumene.expense.note.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class CountryDTO {

	private final IntegerProperty countryId;
	private final StringProperty countryName;

	public CountryDTO(Integer expenseCategoryID, String expenseCategoryName){
		this.countryId = countryIdProperty();
		this.countryName = countryNameProperty();
	}

	public IntegerProperty countryIdProperty() {
        return countryId;
    }

    public Integer getExpenseCategoryID() {
        return countryId.get();
    }

    public void setcountryName(String countryName) {
        this.countryName.set(countryName);
    }

    public StringProperty countryNameProperty() {
        return countryName;
    }

    public String getcountryName() {
        return countryName.get();
    }



}
