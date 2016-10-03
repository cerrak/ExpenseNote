package be.elmoumene.expense.note.model;

import be.elmoumene.expense.note.entity.Country;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CountryDTO { 

	private Long id;
	private StringProperty name;


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
	
	public static Country toEntity(CountryDTO dto) {
		if (dto == null)
			return null;

		Country entity = new Country();

		entity.setId(dto.getId());
		entity.setName(dto.getName());

		return entity;
	}

	public static CountryDTO toDto(Country entity) {
		if (entity == null)
			return null;
		
		CountryDTO model = new CountryDTO();

		model.setId(entity.getId());
		model.setName(entity.getName());

		return model;

	}
	
	@Override
	public String toString() {
		return this.getName();
	}


}
