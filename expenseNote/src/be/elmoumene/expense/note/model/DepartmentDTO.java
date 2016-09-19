package be.elmoumene.expense.note.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class DepartmentDTO {

	private final IntegerProperty departmentID;
	private final StringProperty departmentName;

	public DepartmentDTO(Integer departmentID, StringProperty departmentName){
			this.departmentID = departmentIDProperty();
			this.departmentName=departmentName;
	}

	public Integer getDepartmentId() {
        return departmentID.get();
    }

	 public IntegerProperty departmentIDProperty() {
	        return departmentID;
	    }

	public String getDepartmentName(){
		return departmentName.get();
	}

    public void setDepartmentName(String departmentName) {
        this.departmentName.set(departmentName);
    }

    public StringProperty departmentNameProperty() {
        return departmentName;
    }

}
