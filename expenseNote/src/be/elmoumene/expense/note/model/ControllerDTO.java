package be.elmoumene.expense.note.model;

import be.elmoumene.expense.note.entity.Controller;

/**
 * Model class for a Person.
 *
 */
public class ControllerDTO extends PersonDTO {

	
	public ControllerDTO() {
		super();
	}
	
	public ControllerDTO(PersonDTO dto) {
		this.setId(dto.getId());
		this.setFirstName(dto.getFirstName());
		this.setLastName(dto.getLastName());
		this.setStreet(dto.getStreet());
		this.setPostalCode(dto.getPostalCode());
		this.setCity(dto.getCity());
		this.setTitle(dto.getTitle());
		this.setBirthday(dto.getBirthday());
		this.setMobile(dto.getMobile());
		this.setEmail(dto.getEmail());
		this.setIsActive(dto.getIsActive());
		this.setPasswordField(dto.getPasswordField());
		this.setUserRole(dto.getUserRole());
		this.setDepartment(dto.getDepartment());
	}

	private EntityDTO entity;

	public EntityDTO getEntity() {
		return entity;
	}

	public void setEntity(EntityDTO entity) {
		this.entity = entity;
	}

	
	public Controller toEntity(){
		Controller c = new Controller(super.toEntity());
		c.setEntity(EntityDTO.toEntity(this.entity));
		return c;
	}


}