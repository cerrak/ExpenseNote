package be.elmoumene.expense.note.model;

import be.elmoumene.expense.note.entity.Department;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DepartmentDTO {

	private Long id;
	private StringProperty name;
	private ObjectProperty<EntityDTO> entity;


	public DepartmentDTO(){

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name == null ? null : name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}


    public void setDepartmentName(String departmentName) {
        this.name = new SimpleStringProperty(departmentName);
    }

    public StringProperty departmentNameProperty() {
        return name;
    }

	public EntityDTO getEntity() {
    	return entity==null?null:entity.get();
    }

    public void setEntity(EntityDTO entity) {
    	this.entity = new SimpleObjectProperty<EntityDTO>(entity);
    }

    public ObjectProperty<EntityDTO> entityProperty() {
        return entity;
    }

    public static Department toEntity(DepartmentDTO dto) {
		if (dto == null)
			return null;

		Department entity = new Department();

		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setEntity(EntityDTO.toEntity(dto.getEntity()));

		return entity;
	}

	public static DepartmentDTO toDto(Department entity) {
		if (entity == null)
			return null;

		DepartmentDTO model = new DepartmentDTO();

		model.setId(entity.getId());
		model.setDepartmentName(entity.getName());
		model.setEntity(EntityDTO.toDto(entity.getEntity()));

		return model;

	}

	@Override
	public String toString() {
		return this.getName();
	}

}
