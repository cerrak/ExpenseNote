package be.elmoumene.expense.note.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import be.elmoumene.expense.note.database.ConnectionHSQL;
import be.elmoumene.expense.note.entity.Department;
import be.elmoumene.expense.note.entity.Entity;
import be.elmoumene.expense.note.entity.Expense;
import be.elmoumene.expense.note.entity.ExpenseNote;
import be.elmoumene.expense.note.entity.Person;
import be.elmoumene.expense.note.entity.Status;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public class ExpenseNoteDaoImpl implements ExpenseNoteDao {

	private final static String COLUMNS = "en_name, comment, en_date, person_id, "
										+ "en_status " ;

	private final static String COLUMNS_WITH_ID = COLUMNS + ", id";
	private PersonDao<Person> personDao = FactoryDao.getPersonDao();

	private static ExpenseNoteDaoImpl uniqueInstance = new ExpenseNoteDaoImpl();
    private Connection con = ConnectionHSQL.getInstance().getConn();
    private PreparedStatement stm;

    public static ExpenseNoteDaoImpl getInstance() {
        return uniqueInstance;
    }

	@Override
	public ExpenseNote create(ExpenseNote en) throws Exception {

		Long expenseNoteId = null;
		Exception error = null;

		// INSERT
		StringBuilder sql = new StringBuilder();
		sql.append("Insert into expense_note ");
		sql.append("(" + COLUMNS + ") ");
		sql.append("values (?,?,?,?,?) ");

		try {
			con.setAutoCommit(false);

			stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, en.getName());
			stm.setString(2, en.getComment());
			stm.setTimestamp(3, new Timestamp(en.getCeationDate().getTimeInMillis()));
			stm.setLong(4, en.getPerson().getId());
			stm.setString(5, en.getStatus().toString());

			stm.executeUpdate();

			ResultSet res = stm.getGeneratedKeys();

			// SELECT CREATED EXPENSENOTE
			res.next();

			expenseNoteId = res.getLong(1);

			for (Expense e : en.getExpenses()) {
				// update All Expense one by one

				sql = new StringBuilder();
				sql.append("Update expense ");
				sql.append("set expense_note_id=? ");
				sql.append("where id = ?");

				stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				stm.setLong(1, expenseNoteId);
				stm.setLong(2,  e.getId());

				stm.executeUpdate();
			}

			con.commit();

		} catch (Exception ex) {
			error = ex;
			try {
				con.rollback();
			} catch (Exception e) {
				error = e;
			}
		}

		con.setAutoCommit(true);

		if (error == null){
			return getExpenseNoteById(expenseNoteId);
		}else{
			throw new ExpenseNoteException("Expense creation in error", error);
		}
	}


	@Override
	public ExpenseNote update(ExpenseNote en) throws Exception {

		Exception error = null;

		// UPDATE
		StringBuilder sql = new StringBuilder();
		sql.append("Update expense_note ");
		sql.append("set en_name=?, comment=?, person_id=?, en_status=? ");
		sql.append("where id = ?" );

		try {
			con.setAutoCommit(false);

			// UPDATE EXPENSENOTE
			stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			stm.setString(1, en.getName());
			stm.setString(2, en.getComment());
			stm.setLong(3, en.getPerson().getId());
			stm.setString(4, en.getStatus().toString());
			stm.setLong(5,  en.getId());
			stm.executeUpdate();

			// REMOVE ALL EXPENSES FROM EXPENSE_NOTE

			sql = new StringBuilder();
			sql.append("Update expense ");
			sql.append("set expense_note_id = null ");
			sql.append("where expense_note_id = ?");

			stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stm.setLong(1, en.getId());
			stm.executeUpdate();

			// ADD ALL SELECTED EXPENSE TO THE EXPENSE NOTE
			for (Expense e : en.getExpenses()) {
				// update All Expense one by one

				sql = new StringBuilder();
				sql.append("Update expense ");
				sql.append("set expense_note_id=? ");
				sql.append("where id = ?");

				stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				stm.setLong(1, en.getId());
				stm.setLong(2,  e.getId());
				stm.executeUpdate();
			}

			con.commit();

		} catch (Exception ex) {
			error = ex;
			try {
				con.rollback();
			} catch (Exception e) {
				error = e;
			}
		}

		con.setAutoCommit(true);

		if (error == null){
			return getExpenseNoteById(en.getId());
		}else{
			throw new ExpenseNoteException("Expense creation in error", error);
		}
	}

	@Override
	public ExpenseNote getExpenseNoteById(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from expense_note ");
		sql.append("where id = ?");

		try {
            stm = con.prepareStatement(sql.toString());
            stm.setLong(1, id);

            ResultSet res = stm.executeQuery();
            res.next();

            return mapResult(res);

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;

	}

	private ExpenseNote mapResult(ResultSet res) throws SQLException{
		ExpenseNote en = new ExpenseNote();


		en.setName(res.getString(1));
		en.setComment(res.getString(2));

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(res.getTimestamp(3).getTime());

		en.setCeationDate(cal);
		en.setStatus(Status.fromString(res.getString(5)));
		en.setId(res.getLong(6));

		Person p = personDao.getById(res.getLong(4));
		en.setPerson(p);

		return en;

	}

	public List<ExpenseNote> getExpenseNotesFromPerson(Long id) {
		List<ExpenseNote> list = new ArrayList<ExpenseNote>();

		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from expense_note ");
		sql.append("where person_id = ? ");


		try {
            stm = con.prepareStatement(sql.toString());
            stm.setLong(1, id);

            ResultSet res = stm.executeQuery();

            while(res.next() ){
            	list.add(mapResult(res));
            }

            return list;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
	}

	@Override
	public void delete(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete ");
		sql.append("from expense_note ");
		sql.append("where id = ?");

		try {
            stm = con.prepareStatement(sql.toString());
            stm.setLong(1, id);

            stm.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.toString());
        }

	}

	@Override
	public List<ExpenseNote> getExpenseNotesForSupervisor(Department dep) {
		List<ExpenseNote> list = new ArrayList<ExpenseNote>();

		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from expense_note ");
		sql.append("where person_id in (select person_id from person where department_id = ? ) ");
		sql.append("and en_status IN ('SUBMITTED', 'CONTROLLED', 'VALIDATED', 'APPROVED')");


		try {
            stm = con.prepareStatement(sql.toString());
            stm.setLong(1, dep.getId());
            ResultSet res = stm.executeQuery();

            while(res.next() ){
            	list.add(mapResult(res));
            }

            return list;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
	}

	@Override
	public List<ExpenseNote> getExpenseNotesForController(Entity entity) {
		List<ExpenseNote> list = new ArrayList<ExpenseNote>();

		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from expense_note ");
		sql.append("where person_id in (select person_id from person where department_id in ");
		sql.append("(select id from department where entity_id = ?) ");
		sql.append("and en_status IN ('SUBMITTED', 'CONTROLLED', 'VALIDATED', 'APPROVED')");


		try {
            stm = con.prepareStatement(sql.toString());
            stm.setLong(1, entity.getId());
            ResultSet res = stm.executeQuery();

            while(res.next() ){
            	list.add(mapResult(res));
            }

            return list;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
	}

}
