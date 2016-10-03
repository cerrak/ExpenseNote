package be.elmoumene.expense.note.entity;

public class Supervisor extends Person {

	public Supervisor(){};
	
	private Department department;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	} 
}
