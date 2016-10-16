package be.elmoumene.expense.note.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import be.elmoumene.expense.note.entity.Person;
import be.elmoumene.expense.note.entity.UserRole;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 */
public class PersonDTO {

	private LongProperty id;
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty street;
	private IntegerProperty postalCode;
	private StringProperty city;
	private ObjectProperty<LocalDate> birthday;
	private StringProperty mobile;
	private StringProperty email;
	private StringProperty title;
	private BooleanProperty isActive;
	private StringProperty password;
	private UserRole userRole;
	private ObjectProperty<DepartmentDTO> department;

	public PersonDTO() {

	}

    public Boolean getIsActive() {
        return isActive==null?null:isActive.get();
    }

    public BooleanProperty isActiveProperty(){
    	return isActive;
    }

	public void setIsActive(Boolean isActive) {
		this.isActive = new SimpleBooleanProperty(isActive);
	}

	public String getPasswordField() {
		return password==null?null:password.get();
	}

	public StringProperty passwordFieldProperty(){
		return password;

	}

	public void setPasswordField(String passwordField) {
		this.password = new SimpleStringProperty(passwordField);
	}

	public String getFirstName() {
        return firstName==null?null:firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName = new SimpleStringProperty(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName==null?null:lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName = new SimpleStringProperty(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getStreet() {
        return street==null?null:street.get();
    }

    public void setStreet(String street) {
    	this.street = new SimpleStringProperty(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

    public Integer getPostalCode() {
        return postalCode==null?null:postalCode.get();
    }

    public void setPostalCode(int postalCode) {
    	this.postalCode = new SimpleIntegerProperty(postalCode);
    }


	public DepartmentDTO getDepartment() {
    	return department==null?null:department.get();
    }

    public void setDepartment(DepartmentDTO department) {
    	this.department = new SimpleObjectProperty<DepartmentDTO>(department);
    }

    public ObjectProperty<DepartmentDTO> entityProperty() {
        return department;
    }
    
	public IntegerProperty postalCodeProperty() {
        return postalCode;
    }

    public String getCity() {
        return city==null?null:city.get();
    }

    public void setCity(String city) {
    	this.city = new SimpleStringProperty(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public LocalDate getBirthday() {
    	return birthday==null?null:birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
    	this.birthday = new SimpleObjectProperty<LocalDate>(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

	public String getEmail() {
		return email==null?null:email.get();
	}

	  public StringProperty emailProperty() {
	        return email;
	  }

	 public void setEmail(String email) {
		 this.email = new SimpleStringProperty(email);
	    }

    public String getMobile() {
        return mobile==null?null:mobile.get();
    }

    public void setMobile(String mobile) {
    	this.mobile = new SimpleStringProperty(mobile);
    }

    public StringProperty mobileProperty() {
        return mobile;
    }

    public String getTitle() {
        return title==null?null:title.get();
    }

    public void setTitle(String title) {
    	this.title = new SimpleStringProperty(title);
    }

    public StringProperty titleProperty() {
        return title;
    }


	public Long getId() {
		return  id==null?null:id.get();
	}

	public void setId(Long id) {
		this.id = new SimpleLongProperty(id);
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public Person toEntity(){
		Person entity = new Person();

		entity.setId(this.getId());
		entity.setFirstName(this.getFirstName());
		entity.setLastName(this.getLastName());
		entity.setStreet(this.getStreet());
		entity.setPostalCode(this.getPostalCode());
		entity.setCity(this.getCity());
		entity.setTitle(this.getTitle());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Calendar cal = Calendar.getInstance();
        String formattedDate = this.getBirthday().toString();

        try {
			cal.setTime(sdf.parse(formattedDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        entity.setBirthday(cal);
		entity.setMobile(this.getMobile());
		entity.setEmail(this.getEmail());
		entity.setIsActive(this.getIsActive());
		entity.setPassword(this.getPasswordField());
		entity.setUserRole(this.getUserRole());
		entity.setDepartment(DepartmentDTO.toEntity(this.getDepartment()));
		return entity;

	}

}