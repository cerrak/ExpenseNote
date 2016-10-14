package be.elmoumene.expense.note.model;

import be.elmoumene.expense.note.entity.Supervisor;

/**
 * Model class for a Person.
 *
 */
public class SupervisorDTO extends PersonDTO {

	private DepartmentDTO department;

	public DepartmentDTO getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDTO department) {
		this.department = department;
	}

	public static Supervisor toEntity(SupervisorDTO model){
		if(model == null)
			return null;

		Supervisor entity = new Supervisor();




		//	TODO implément le reste lol
		//ce qu'il veut dire c'est qu'il faut implémenter les informations propre au supervisor


		return entity;

	}

	public static SupervisorDTO toDto(Supervisor entity){
		if(entity == null)
			return null;

		SupervisorDTO model = (SupervisorDTO) PersonDTO.toDto(entity);
		if(model == null)
			return null;

		model.setDepartment(DepartmentDTO.toDto(entity.getDepartment()));

		return model;

	}





}