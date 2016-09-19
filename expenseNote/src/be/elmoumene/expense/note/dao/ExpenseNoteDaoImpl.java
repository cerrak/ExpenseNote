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
import be.elmoumene.expense.note.entity.Expense;
import be.elmoumene.expense.note.entity.ExpenseNote;
import be.elmoumene.expense.note.entity.Status;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public class ExpenseNoteDaoImpl implements ExpenseNoteDao {

	private final static String COLUMNS = "en_name, comment, en_date, person_id, "
										+ "en_status " ;

	private final static String COLUMNS_WITH_ID = COLUMNS + ", id";

	private static ExpenseNoteDaoImpl uniqueInstance = new ExpenseNoteDaoImpl();
    private Connection con = ConnectionHSQL.getInstance().getConn();
    private PreparedStatement stm;

    public static ExpenseNoteDaoImpl getInstance() {
        return uniqueInstance;
    }

	@Override
	public ExpenseNote create(ExpenseNote en) throws ExpenseNoteException {

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

			Long expenseNoteId = res.getLong(1);

			for (Expense e : en.getExpenses()) {
				// update All Expense one by one

				sql = new StringBuilder();
				sql.append("Update expense ");
				sql.append("set expense_note_id=? ");
				sql.append("where id = " + e.getId());

				stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				stm.setLong(1, expenseNoteId);

				stm.executeUpdate();
			}

			con.commit();
			con.setAutoCommit(true);
			return getExpenseNoteById(res.getLong(1));

		} catch (SQLException ex) {
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException e) {
				throw new ExpenseNoteException("Expense creation in error", e);
			}
			throw new ExpenseNoteException("Expense creation in error", ex);
		}
	}

	@Override
	public List<ExpenseNote> getExpenseNotes(Long expenseNoteId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ExpenseNote update(ExpenseNote en) throws ExpenseNoteException {
		// UPDATE
		StringBuilder sql = new StringBuilder();
		sql.append("Update expense_note ");
		//sql.append("("+COLUMNS+") ");
		sql.append("set en_name=?, comment=?, en_status=? ");
		sql.append("where id = "+ en.getId());

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, en.getName());
            stm.setString(2, en.getComment());
            stm.setString(3, en.getStatus().toString());


            stm.executeUpdate();
           return getExpenseNoteById(en.getId());

        } catch (Exception ex) {
        	throw new ExpenseNoteException("User update in error", ex);
        }
	}

	@Override
	public void delete(ExpenseNote en) {
		// TODO Auto-generated method stub

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


	@Override
	public List<ExpenseNote> getExpenseNotes() {
		List<ExpenseNote> list = new ArrayList<ExpenseNote>();

		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from expense_note ");

		try {
            stm = con.prepareStatement(sql.toString());

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

	private ExpenseNote mapResult(ResultSet res) throws SQLException{
		ExpenseNote en = new ExpenseNote();


		en.setName(res.getString(1));
		en.setComment(res.getString(2));

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(res.getTimestamp(3).getTime());

		en.setCeationDate(cal);
		en.setStatus(Status.fromString(res.getString(5)));
		en.setId(res.getLong(6));


		return en;

	}

	public List<ExpenseNote> getExpenseNotesFromPerson(Long id) {
		List<ExpenseNote> list = new ArrayList<ExpenseNote>();

		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from expense_note ");
		sql.append("where person_id = ?");

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

}
