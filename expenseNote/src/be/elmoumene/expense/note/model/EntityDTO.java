package be.elmoumene.expense.note.model;

import be.elmoumene.expense.note.entity.Entity;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EntityDTO {


	private  Long id;
	private  StringProperty name;
	private ObjectProperty<CountryDTO> country;
	private  StringProperty city;
	private  StringProperty locality;
	private  BooleanProperty deleted;


	public Long getId() {
        return id;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name == null ? null : name.get();
	}

	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public CountryDTO getCountry() {
    	return country==null?null:country.get();
    }

    public void setCountry(CountryDTO country) {
    	this.country = new SimpleObjectProperty<CountryDTO>(country);
    }

    public ObjectProperty<CountryDTO> countryProperty() {
        return country;
    }

    public String getCity(){
    	return city == null ? null : city.get();
	}

	public void setCity(String city) {
        this.city = new SimpleStringProperty(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getLocality() {
		return locality == null ? null : locality.get();
	}

	public void setLocality(String locality) {
		this.locality = new SimpleStringProperty(locality);
	}

	public StringProperty localityProperty() {
		return locality;
	}

	public Boolean getDeleted() {
		return deleted == null ? null : deleted.get();
	}

//	public void setDeleted(Boolean deleted) {
//		this.deleted = new SimpleBooleanProperty(deleted);
//	}

	public BooleanProperty deletedProperty() {
		return deleted;
	}



	public static Entity toEntity(EntityDTO dto) {
		if (dto == null)
			return null;

		Entity entity = new Entity();

		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setCountry(CountryDTO.toEntity(dto.getCountry()));
		entity.setCity(dto.getCity());
		entity.setLocality(dto.getLocality());
		//entity.setDeleted(dto.getDeleted());

		return entity;
	}

	public static EntityDTO toDto(Entity entity) {
		if (entity == null)
			return null;

		EntityDTO model = new EntityDTO();

		model.setId(entity.getId());
		model.setName(entity.getName());
		model.setCountry(CountryDTO.toDto(entity.getCountry()));
		model.setCity(entity.getCity());
		model.setLocality(entity.getLocality());
		//model.setDeleted(entity.getDeleted());

		return model;

	}

	@Override
	public String toString() {
		return this.getName();

	}
}
