package be.elmoumene.expense.note.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import be.elmoumene.expense.note.entity.Person;
import be.elmoumene.expense.note.entity.UserRole;
import be.elmoumene.expense.note.util.DateUtil;
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

	public static Person toEntity(PersonDTO model){
		if(model == null)
			return null;

		Person entity = new Person();

		entity.setId(model.getId());
		entity.setFirstName(model.getFirstName());
		entity.setLastName(model.getLastName());
		entity.setStreet(model.getStreet());
		entity.setPostalCode(model.getPostalCode());
		entity.setCity(model.getCity());
		entity.setTitle(model.getTitle());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Calendar cal = Calendar.getInstance();
        String formattedDate = model.getBirthday().toString();

        try {
			cal.setTime(sdf.parse(formattedDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        entity.setBirthday(cal);
		entity.setMobile(model.getMobile());
		entity.setEmail(model.getEmail());
		entity.setIsActive(model.getIsActive());
		entity.setPassword(model.getPasswordField());
		entity.setUserRole(model.getUserRole());

		return entity;

	}

	public static PersonDTO toDto(Person entity){
		if(entity == null)
			return null;
		PersonDTO model = new PersonDTO();

		model.setId(entity.getId());
		model.setFirstName(entity.getFirstName());
		model.setLastName(entity.getLastName());
		model.setStreet(entity.getStreet());
		model.setPostalCode(entity.getPostalCode());
		model.setCity(entity.getCity());
		model.setTitle(entity.getTitle());

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = sdf.format(entity.getBirthday().getTime());

		model.setBirthday(DateUtil.parse(formattedDate));

		model.setMobile(entity.getMobile());
		model.setEmail(entity.getEmail());
		model.setIsActive(entity.getIsActive());
		model.setPasswordField(entity.getPassword());
		model.setUserRole(entity.getUserRole());

		return model;

	}





}