package be.elmoumene.expense.note.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import be.elmoumene.expense.note.model.PersonDTO;
import be.elmoumene.expense.note.util.DateUtil;


/**
 * Entity class for a Person.
 *
 */
public class Person {

	private Long id;
	private String firstName;
	private String lastName;
	private String street;
	private Integer postalCode;
	private String city;
	private Calendar birthday;
	private String mobile;
	private String email;
	private String title;
	private List<Expense> expenses;
	private UserRole userRole;
	private String password;
	private Boolean isActive;
	private Department department;

	public Person() {
	}

	public Boolean getIsActive() {
		return isActive;
	}


	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public Integer getPostalCode() {
		return postalCode;
	}


	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public Calendar getBirthday() {
		return birthday;
	}


	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}


	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}


	public UserRole getUserRole() {
		return userRole;
	}


	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password =  password;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public PersonDTO toDto(){
		PersonDTO model = new PersonDTO();

		model.setId(this.getId());
		model.setFirstName(this.getFirstName());
		model.setLastName(this.getLastName());
		model.setStreet(this.getStreet());
		model.setPostalCode(this.getPostalCode());
		model.setCity(this.getCity());
		model.setTitle(this.getTitle());

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = sdf.format(this.getBirthday().getTime());

		model.setBirthday(DateUtil.parse(formattedDate));

		model.setMobile(this.getMobile());
		model.setEmail(this.getEmail());
		model.setIsActive(this.getIsActive());
		model.setPasswordField(this.getPassword());
		model.setUserRole(this.getUserRole());

		return model;

	}

}