package be.elmoumene.expense.note.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class StatusDTO {

	private final IntegerProperty statusID;
	private final StringProperty statusName;

	public StatusDTO(Integer statusID, String statusName){
			this.statusID=statusIDProperty();
			this.statusName=statusNameProperty();
	}


	    public IntegerProperty statusIDProperty() {
	        return statusID;
	    }

	    public Integer getStatusID() {
	        return statusID.get();
	    }

	    public void setStatusName(String statusName) {
	        this.statusName.set(statusName);
	    }

	    public StringProperty statusNameProperty() {
	        return statusName;
	    }

	    public String getStatusName() {
	        return statusName.get();
	    }



}
