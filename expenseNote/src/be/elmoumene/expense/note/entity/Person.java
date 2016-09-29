package be.elmoumene.expense.note.entity;

import java.util.Calendar;
import java.util.List;


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

}