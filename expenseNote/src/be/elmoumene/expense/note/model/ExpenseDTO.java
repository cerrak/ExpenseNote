package be.elmoumene.expense.note.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import be.elmoumene.expense.note.entity.Expense;
import be.elmoumene.expense.note.util.DateUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExpenseDTO {
	private LongProperty id;
	private FloatProperty amount;
	private StringProperty supplier;
	private ObjectProperty<CountryDTO> country;
	private StringProperty city;
	private ObjectProperty<LocalDate> dateExpense;
	private StringProperty comment;
	private StringProperty currency;
	private BooleanProperty receipt;
	private ObjectProperty<CategoryDTO> category;
	private ObjectProperty<PersonDTO> person;

	/**
	 * Default constructor.
	 */
	public ExpenseDTO() {

	}

	public void setCategory(CategoryDTO category) {
		this.category = new SimpleObjectProperty<CategoryDTO> (category);
	}

	public CategoryDTO getCategory(){
		return category==null?null:category.get();
	}

	public ObjectProperty<CategoryDTO> categoryProperty() {
		return category;
	}

	public Long getId() {
		return id == null?null:id.get();
	}

	public void setId(Long id) {
		this.id = new SimpleLongProperty(id);
	}

	public LongProperty idProperty(){
		return id;
	}

	public Float getAmount() {
		return amount == null ? null : amount.get();
	}

	public void setAmount(Float amount) {
		this.amount = new SimpleFloatProperty(amount);
	}

	public FloatProperty amountProperty() {
		return amount;
	}


    public CountryDTO getCountry() {
    	return country==null?null:country.get();
    }

    public void setCountry(CountryDTO country) {
    	this.country = new SimpleObjectProperty<CountryDTO>(country);
    }

    public ObjectProperty<CountryDTO> countryProperty() {
        return country;
    }

	public String getSupplier() {
		return supplier == null ? null : supplier.get();
	}

	public void setSupplier(String supplier) {
		this.supplier = new SimpleStringProperty(supplier);
	}

	public StringProperty supplierProperty() {
		return supplier;
	}

	public String getCity() {
		return city == null ? null : city.get();
	}

	public void setCity(String city) {
		this.city = new SimpleStringProperty(city);
	}

	public StringProperty cityProperty() {
		return city;
	}

	public LocalDate getDateExpense() {
		return dateExpense == null ? null : dateExpense.get();
	}

	public void setDateExpense(LocalDate dateExpense) {
		this.dateExpense = new SimpleObjectProperty<LocalDate>(dateExpense);
	}

	public ObjectProperty<LocalDate> dateExpenseProperty() {
		return dateExpense;
	}

	public String getComment() {
		return comment == null ? null : comment.get();
	}

	public void setComment(String comment) {
		this.comment = new SimpleStringProperty(comment);
	}

	public StringProperty commentProperty() {
		return comment;
	}

	public String getCurrency() {
		return currency == null ? null : currency.get();
	}

	public void setCurrency(String currency) {
		this.currency = new SimpleStringProperty(currency);
	}

	public StringProperty currencyProperty() {
		return currency;
	}

	public Boolean getReceipt() {
		return receipt == null ? null : receipt.get();
	}

	public void setReceipt(Boolean receipt) {
		this.receipt = new SimpleBooleanProperty(receipt);
	}

	public BooleanProperty receiptProperty() {
		return receipt;
	}

	public PersonDTO getPerson() {
		return person == null ? null : person.get();
	}

	public void setPerson(PersonDTO person) {
		this.person = new SimpleObjectProperty<PersonDTO>(person);
	}

	public ObjectProperty<PersonDTO> person() {
		return person;
	}


	public static Expense expenseToEntity(ExpenseDTO dto) {
		if (dto == null)
			return null;

		Expense entity = new Expense();

		entity.setId(dto.getId());
		entity.setSupplier(dto.getSupplier());
		entity.setCity(dto.getCity());
		entity.setAmount(dto.getAmount());
		entity.setReceipt(dto.getReceipt());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String formattedDate = dto.getDateExpense().toString();

        try {
			cal.setTime(sdf.parse(formattedDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        entity.setDate(cal);
		entity.setComment(dto.getComment());
		entity.setCountry(CountryDTO.toEntity(dto.getCountry()));
		entity.setPerson(dto.getPerson().toEntity());
		entity.setCurrency(dto.getCurrency());
		entity.setCategory(CategoryDTO.toEntity(dto.getCategory()));

		return entity;
	}

	public static ExpenseDTO expenseToModel(Expense entity) {

		ExpenseDTO model = new ExpenseDTO();

		model.setId(entity.getId());
		model.setCity(entity.getCity());
		model.setComment(entity.getComment());
		model.setCountry(CountryDTO.toDto(entity.getCountry()));
		model.setCurrency(entity.getCurrency());
		model.setCategory(CategoryDTO.toDto(entity.getCategory()));

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String formattedDate = sdf.format(entity.getDate().getTime());

		model.setDateExpense(DateUtil.parse(formattedDate));
		model.setSupplier(entity.getSupplier());
		model.setAmount(entity.getAmount());
		model.setReceipt(entity.getReceipt());
		model.setPerson(entity.getPerson().toDto());

		return model;

	}

	@Override
	public String toString() {

		return this.getAmount() + "� - " + this.getSupplier() + " - " + this.getCity() + " - " + this.getDateExpense();
	}

//	@Override
//	public boolean equals(Object obj) {
//		if(obj instanceof ExpenseDTO){
//			ExpenseDTO e = (ExpenseDTO) obj;
//			if(this.getId() == e.getId())
//				return true;
//		}
//
//		return false;
//	}
}
