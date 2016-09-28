package be.elmoumene.expense.note.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import be.elmoumene.expense.note.database.ConnectionHSQL;
import be.elmoumene.expense.note.entity.Entity;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public class EntityDaoImpl implements EntityDao {


	private final static String COLUMNS = "name, country, city, locality ";

	private final static String COLUMNS_WITH_ID = COLUMNS + ", id";

	private EntityDao EntityDao = FactoryDao.getEntityDao();


	private static EntityDaoImpl uniqueInstance = new EntityDaoImpl();
    private Connection con = ConnectionHSQL.getInstance().getConn();
    private PreparedStatement stm;

    public static EntityDaoImpl getInstance() {
        return uniqueInstance;
    }

	@Override
	public Entity create(Entity e) throws ExpenseNoteException {

		// INSERT
		StringBuilder sql = new StringBuilder();
		sql.append("Insert into entity ");
		sql.append("("+COLUMNS+") ");
		sql.append("values (?,?,?,?) ");

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, e.getName());
            stm.setString(2, e.getCountry());
            stm.setString(3, e.getCity());
            stm.setString(4, e.getLocality());

            stm.executeUpdate();

            ResultSet res = stm.getGeneratedKeys();

           // SELECT CREATED ENTITY
            res.next();
           return getEntityById(res.getLong(1));

        } catch (Exception ex) {
        	throw new ExpenseNoteException("ENtity creation in error", ex);
        }
	}

	@Override
	public List<Entity> getEntities(Long entityId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Entity update(Entity e) throws ExpenseNoteException {
		// UPDATE
		StringBuilder sql = new StringBuilder();
		sql.append("Update Entity ");
		sql.append("set name=?, country=?, city=?, locality=? ");
		sql.append("where id = "+ e.getId());

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, e.getName());
            stm.setString(2, e.getCountry());
            stm.setString(3, e.getCity());
            stm.setString(4, e.getLocality());

            stm.executeUpdate();
           return getEntityById(e.getId());

        } catch (Exception ex) {
        	throw new ExpenseNoteException("Entity update in error", ex);
        }
	}

	@Override
	public void delete(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public Entity getEntityById(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from entity ");
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
	public List<Entity> getEntities() {
		List<Entity> list = new ArrayList<Entity>();

		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from entity ");

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

	private Entity mapResult(ResultSet res) throws SQLException{
		Entity e = new Entity();


		e.setName(res.getString(1));
		e.setCountry(res.getString(2));
		e.setCity(res.getString(3));
		e.setLocality(res.getString(4));

		return e;
	}

}
