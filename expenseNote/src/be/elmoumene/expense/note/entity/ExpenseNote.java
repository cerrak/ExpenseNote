package be.elmoumene.expense.note.entity;

import java.util.Calendar;
import java.util.List;

public class ExpenseNote {

	private Long id;
	private String name;
	private String comment;
	private Calendar ceationDate;
	private Status status;
	private Person person;
	private List<Expense> expenses;

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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Calendar getCeationDate() {
		return ceationDate;
	}
	public void setCeationDate(Calendar ceationDate) {
		this.ceationDate = ceationDate;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Person getPerson() {
		return person;
	}
	public List<Expense> getExpenses() {
		return expenses;
	}
	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}
	public void setPerson(Person person) {
		this.person = person;
	}



}
