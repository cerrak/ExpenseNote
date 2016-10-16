package be.elmoumene.expense.note.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import be.elmoumene.expense.note.entity.Expense;
import be.elmoumene.expense.note.entity.ExpenseNote;
import be.elmoumene.expense.note.entity.Status;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class ExpenseNoteDTO {

	private Long id;
	private StringProperty name;
	private StringProperty comment;
	private Calendar ceationDate;
	private Status status;
	private PersonDTO person;
	private List<ExpenseDTO> expenses;


	public ExpenseNoteDTO(){

	}

	public static ExpenseNote toEntity(ExpenseNoteDTO dto) {
		if (dto == null)
			return null;

		ExpenseNote entity = new ExpenseNote();

		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setComment(dto.getComment());
		entity.setCeationDate(dto.getCeationDate());
		entity.setStatus(dto.getStatus());
		entity.setPerson(dto.getPerson().toEntity());

		if (dto.getExpenses() != null){
			List<Expense> expenses = new ArrayList<Expense>();

			for(ExpenseDTO e: dto.getExpenses())
				expenses.add(ExpenseDTO.expenseToEntity(e));

			entity.setExpenses(expenses);

		}

		return entity;
	}

	public static ExpenseNoteDTO toDto(ExpenseNote entity) {

		if (entity == null)
			return null;

		ExpenseNoteDTO dto = new ExpenseNoteDTO();

		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setComment(entity.getComment());
		dto.setCeationDate(entity.getCeationDate());
		dto.setStatus(entity.getStatus());
		dto.setPerson(entity.getPerson().toDto());

		if (entity.getExpenses() != null){
			List<ExpenseDTO> expenses = new ArrayList<ExpenseDTO>();

			for(Expense e: entity.getExpenses())
				expenses.add(ExpenseDTO.expenseToModel(e));

			dto.setExpenses(expenses);

		}


		return dto;

	}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}


		public StringProperty nameProperty() {
			return name;
		}

		public void setName(String name) {
			this.name = new SimpleStringProperty(name);
		}

		public String getName() {
			return name==null?null:name.get();
		}

		public void setComment(String comment) {
			this.comment = new SimpleStringProperty(comment);
		}

		public StringProperty commentProperty() {
	        return comment;
	    }

		public String getComment() {
			return comment==null?null:comment.get();
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

		public PersonDTO getPerson() {
			return person;
		}

		public void setPerson(PersonDTO person) {
			this.person = person;
		}

		public List<ExpenseDTO> getExpenses() {
			return expenses;
		}

		public void setExpenses(List<ExpenseDTO> expenses) {
			this.expenses = expenses;
		}



}
