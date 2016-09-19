package be.elmoumene.expense.note.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class EntityDTO {


	private final IntegerProperty entityID;
	private final StringProperty entityName;
	private final StringProperty entityCountry;
	private final StringProperty entityTown;
	private final StringProperty entityLocality;

	public EntityDTO(){
		this.entityID=entityIDProperty();
		this.entityName=entityNameProperty();
		this.entityCountry=entityCountryProperty();
		this.entityTown=entityTownProperty();
		this.entityLocality=entityLocalityProperty();
	}

	public Integer getEntityId() {
        return entityID.get();
    }

	 public IntegerProperty entityIDProperty() {
	        return entityID;
	    }

	public String getEntityName(){
		return entityName.get();
	}

	public void setEntityName(String entityName) {
        this.entityName.set(entityName);
    }

    public StringProperty entityNameProperty() {
        return entityName;
    }

    public String getEntityCountry(){
		return entityCountry.get();
	}

	public void setEntityCountry(String entityCountry) {
        this.entityCountry.set(entityCountry);
    }

    public StringProperty entityCountryProperty() {
        return entityCountry;
    }

    public String getEntityTown(){
		return entityTown.get();
	}

	public void setEntityTown(String entityTown) {
        this.entityTown.set(entityTown);
    }

    public StringProperty entityTownProperty() {
        return entityTown;
    }

    public String getEntityLocality(){
		return entityLocality.get();
	}

	public void setEntityLocality(String entityLocality) {
        this.entityLocality.set(entityLocality);
    }

    public StringProperty entityLocalityProperty() {
        return entityLocality;
    }
}
