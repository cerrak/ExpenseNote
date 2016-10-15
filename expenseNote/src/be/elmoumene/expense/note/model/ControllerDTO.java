package be.elmoumene.expense.note.model;

import be.elmoumene.expense.note.entity.Controller;

/**
 * Model class for a Person.
 *
 */
public class ControllerDTO extends PersonDTO {

	public ControllerDTO(PersonDTO dto) {
		super(dto);
	}
	
	public ControllerDTO() {
		super();
	}
	
	private EntityDTO entity;

	public EntityDTO getEntity() {
		return entity;
	}

	public void setEntity(EntityDTO entity) {
		this.entity = entity;
	}

	public static Controller toEntity(Controller model){
		if(model == null)
			return null;

		Controller entity = new Controller();

		//	TODO implément le reste lol
		//ce qu'il veut dire c'est qu'il faut implémenter les informations propre au supervisor


		return entity;

	}

	public static ControllerDTO toDto(Controller entity){
		if(entity == null)
			return null;

		ControllerDTO model = (ControllerDTO) PersonDTO.toDto(entity);
		if(model == null)
			return null;

		model.setEntity(EntityDTO.toDto(entity.getEntity()));

		return model;

	}





}