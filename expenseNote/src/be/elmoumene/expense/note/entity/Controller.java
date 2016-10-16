package be.elmoumene.expense.note.entity;

import be.elmoumene.expense.note.model.ControllerDTO;
import be.elmoumene.expense.note.model.EntityDTO;

public class Controller extends Person {

	public Controller(){};
	
	public Controller(Person entity) {
		this.setId(entity.getId());
		this.setFirstName(entity.getFirstName());
		this.setLastName(entity.getLastName());
		this.setStreet(entity.getStreet());
		this.setPostalCode(entity.getPostalCode());
		this.setCity(entity.getCity());
		this.setTitle(entity.getTitle());
		this.setBirthday(entity.getBirthday());
		this.setMobile(entity.getMobile());
		this.setEmail(entity.getEmail());
		this.setIsActive(entity.getIsActive());
		this.setPassword(entity.getPassword());
		this.setUserRole(entity.getUserRole());
		this.setDepartment(entity.getDepartment());
	}

	private Entity entity;

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}


	public ControllerDTO toDto(){
		ControllerDTO p = new ControllerDTO(super.toDto());
		p.setEntity(EntityDTO.toDto(this.entity));
		return p;

	}

}
