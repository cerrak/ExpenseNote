package be.elmoumene.expense.note.model;

import be.elmoumene.expense.note.entity.Category;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CategoryDTO { 

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
	
	public static Category toEntity(CategoryDTO dto) {
		if (dto == null)
			return null;

		Category entity = new Category();

		entity.setId(dto.getId());
		entity.setName(dto.getName());

		return entity;
	}

	public static CategoryDTO toDto(Category entity) {
		if (entity == null)
			return null;
		
		CategoryDTO model = new CategoryDTO();

		model.setId(entity.getId());
		model.setName(entity.getName());

		return model;

	}
	
	@Override
	public String toString() {
		return this.getName();
	}


}
