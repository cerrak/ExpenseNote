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
import be.elmoumene.expense.note.entity.Country;
import be.elmoumene.expense.note.entity.Expense;
import be.elmoumene.expense.note.entity.ExpenseCategory;
import be.elmoumene.expense.note.entity.Person;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public class ExpenseDaoImpl implements ExpenseDao {


	private final static String COLUMNS = "city, comment, country, currency, expensecategory_id, "
										+ "expense_date, supplier, amount, person_id, receipt" ;

	private final static String COLUMNS_WITH_ID = COLUMNS + ", id";

	private PersonDao personDao = FactoryDao.getPersonDao();


	private static ExpenseDaoImpl uniqueInstance = new ExpenseDaoImpl();
    private Connection con = ConnectionHSQL.getInstance().getConn();
    private PreparedStatement stm;

    public static ExpenseDaoImpl getInstance() {
        return uniqueInstance;
    }

	@Override
	public Expense create(Expense e) throws ExpenseNoteException {

		// INSERT
		StringBuilder sql = new StringBuilder();
		sql.append("Insert into expense ");
		sql.append("("+COLUMNS+") ");
		sql.append("values (?,?,?,?,?,?,?,?,?,?) ");

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, e.getCity());
            stm.setString(2, e.getComment());
            stm.setString(3, e.getCountry().toString());
            stm.setString(4, e.getCurrency());
            stm.setString(5, e.getExpenseCategory().toString());
            stm.setTimestamp(6, new Timestamp(e.getDate().getTimeInMillis()));
            stm.setString(7, e.getSupplier());
            stm.setFloat(8, e.getAmount());
            stm.setLong(9, e.getPerson().getId());
            stm.setBoolean(10, e.getReceipt());

            stm.executeUpdate();

            ResultSet res = stm.getGeneratedKeys();

           // SELECT CREATED EXPENSE
            res.next();
           return getExpenseById(res.getLong(1));

        } catch (Exception ex) {
        	throw new ExpenseNoteException("Expense creation in error", ex);
        }
	}

	@Override
	public List<Expense> getExpenses(Long expenseId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Expense update(Expense e) throws ExpenseNoteException {
		// UPDATE
		StringBuilder sql = new StringBuilder();
		sql.append("Update expense ");
		sql.append("set city=?, comment=?, country=?, expensecategory_id=?, "
					+ "expense_date=?, supplier=?, amount=?, receipt=? ");
		sql.append("where id = "+ e.getId());

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, e.getCity());
            stm.setString(2, e.getComment());
            stm.setString(3, e.getCountry().toString());
            //stm.setString(4, e.getCurrency());
            stm.setString(4, e.getExpenseCategory().toString());
            stm.setTimestamp(5, new Timestamp(e.getDate().getTimeInMillis()));
            stm.setString(6, e.getSupplier());
            stm.setFloat(7, e.getAmount());
            stm.setBoolean(8, e.getReceipt());

            stm.executeUpdate();
           return getExpenseById(e.getId());

        } catch (Exception ex) {
        	throw new ExpenseNoteException("User update in error", ex);
        }
	}

	@Override
	public void delete(Expense e) {
		// TODO Auto-generated method stub

	}

	@Override
	public Expense getExpenseById(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from expense ");
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
	public List<Expense> getExpenses() {
		List<Expense> list = new ArrayList<Expense>();

		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from expense ");

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

	private Expense mapResult(ResultSet res) throws SQLException{
		Expense e = new Expense();


		e.setCity(res.getString(1));
		e.setComment(res.getString(2));
		e.setCountry(Country.fromString(res.getString(3)));
		e.setCurrency(res.getString(4));
		e.setExpenseCategory(ExpenseCategory.fromString(res.getString(5)));

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(res.getTimestamp(6).getTime());

		e.setDate(cal);
		e.setSupplier(res.getString(7));
		e.setAmount(res.getFloat(8));
		e.setReceipt(res.getBoolean(10));

		Person p = personDao.getPersonById(res.getLong(9));
		e.setPerson(p);

		e.setId(res.getLong(11));

		return e;
	}

	public List<Expense> getAvailableExpense(Long id) {
		List<Expense> list = new ArrayList<Expense>();

		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from expense ");
		sql.append("where person_id = ? ");
		sql.append("and expense_note_id is null");

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
	public List<Expense> getExpensesFromExpenseNoteId(Long id) {
		List<Expense> list = new ArrayList<Expense>();

		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from expense ");
		sql.append("where expense_note_id =? ");


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
	public void detacheExpenseNote(Long id) {

		StringBuilder sql = new StringBuilder();
		sql.append("update expense ");
		sql.append("set expense_note_id = null ");
		sql.append("where expense_note_id = ? ");


		try {
            stm = con.prepareStatement(sql.toString());
            stm.setLong(1, id);
            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

	}


}
