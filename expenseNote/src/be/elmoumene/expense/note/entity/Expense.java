package be.elmoumene.expense.note.entity;

import java.util.Calendar;

public class Expense {

	private Long id;
	private Float amount;
	private Boolean receipt;
	private Calendar date;
	private String comment;
	private Country country;
	private String supplier;
	private String city;
	private String currency;
	private Person person;
	private ExpenseNote expenseNote;
	private ExpenseCategory expenseCategory;

	public Expense(){
	}

	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public Boolean getReceipt() {
		return receipt;
	}
	public void setReceipt(Boolean receipt) {
		this.receipt = receipt;
	}
	public ExpenseCategory getExpenseCategory() {
		return expenseCategory;
	}
	public void setExpenseCategory(ExpenseCategory expenseCategory) {
		this.expenseCategory = expenseCategory;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;

	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public ExpenseNote getExpenseNote() {
		return expenseNote;
	}

	public void setExpenseNote(ExpenseNote expenseNote) {
		this.expenseNote = expenseNote;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
