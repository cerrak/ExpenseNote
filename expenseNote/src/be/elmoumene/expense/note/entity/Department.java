package be.elmoumene.expense.note.entity;

public class Department {

	private Long id;
	private String name;
	private Entity entity; 
	
	public Department(){};
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Entity getEntity() {
		return entity;
	}
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
}
