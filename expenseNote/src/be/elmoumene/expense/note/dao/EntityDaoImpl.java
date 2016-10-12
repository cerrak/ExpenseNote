package be.elmoumene.expense.note.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import be.elmoumene.expense.note.database.ConnectionHSQL;
import be.elmoumene.expense.note.entity.Country;
import be.elmoumene.expense.note.entity.Entity;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public class EntityDaoImpl implements EntityDao {


	private final static String TABLE_NAME = "entity";

	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String Country = "country_id";
	private final static String City = "city";
	private final static String Locality = "locality";
	private final static String Deleted = "deleted";


	private static Map<String, Integer> columns;
	private static Map<String, Integer> columnsWithId;

    private static EntityDaoImpl uniqueInstance = new EntityDaoImpl();
    private CountryDao countryDao = FactoryDao.getCountryDao();
    private Connection con = ConnectionHSQL.getInstance().getConn();
    private PreparedStatement stm;

    public static EntityDaoImpl getInstance() {
        return uniqueInstance;
    }

    public EntityDaoImpl(){
    	columns = new LinkedHashMap<String, Integer>();
    	columns.put(NAME, columns.size()+1);
    	columns.put(City, columns.size()+1);
    	columns.put(Locality, columns.size()+1);
    	columns.put(Country, columns.size()+1);
    	//columns.put(Deleted, columns.size()+1);

    	columnsWithId = new LinkedHashMap<String, Integer>(columns);
    	columnsWithId.put(ID, columns.size()+1);
    }

	private Entity mapResult(ResultSet res) throws SQLException {
		Entity e = new Entity();
		try {
			e.setId(res.getLong(columnsWithId.get(ID)));
			e.setName(res.getString(columnsWithId.get(NAME)));
			e.setCity(res.getString(columnsWithId.get(City)));
			e.setLocality(res.getString(columnsWithId.get(Locality)));

			Country country = countryDao.getCountryById(res.getLong(5));
			e.setCountry(country);

			//e.setCountry(res.getObject(columnsWithId.get(Country)));
			//e.setDeleted(res.getBoolean((columnsWithId).get(Deleted)));

			return e;

		} catch (Exception ex) {
		throw new SQLException(ex);
		}
	}

	@Override
	public Entity create(Entity e) throws ExpenseNoteException {
		String sqlColumns = columns.keySet().stream().collect(Collectors.joining(", "));
		String sqlValues = columns.keySet().stream().map(string -> "?").collect(Collectors.joining(", "));

		// INSERT
		StringBuilder sql = new StringBuilder();
		sql.append("Insert into "+TABLE_NAME+" ");
		sql.append("("+sqlColumns+") ");
		sql.append("values ("+sqlValues+") ");

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(columns.get(NAME), e.getName());
            stm.setString(columns.get(City), e.getCity());
            stm.setString(columns.get(Locality), e.getLocality());

            stm.setLong(4, e.getCountry().getId());
            //stm.setBoolean(columns.get(Deleted), e.getDeleted());

            stm.executeUpdate();
            ResultSet res = stm.getGeneratedKeys();

            res.next();
           return getEntityById(res.getLong(1));

        } catch (Exception ex) {
        	throw new ExpenseNoteException("Entity creation in error", ex);
        }
	}

	@Override
	public Entity update(Entity e) throws ExpenseNoteException {


		StringBuilder sql = new StringBuilder();
		sql.append("Update entity ");
		sql.append("set name=?, city=?, locality=?, country_id=? ");
		sql.append("where id = "+ e.getId());

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, e.getName());
            stm.setString(2, e.getCity());
            stm.setString(3, e.getLocality());
            stm.setLong(4, e.getCountry().getId());

            stm.executeUpdate();
           return getEntityById(e.getId());

        } catch (Exception ex) {
        	throw new ExpenseNoteException("User update in error", ex);
        }

      //String sqlColumnsAndValues = columns.keySet().stream().map(string -> string + " = ?").collect(Collectors.joining(", "));
//		StringBuilder sql = new StringBuilder();
//		sql.append("Update "+TABLE_NAME+" ");
//		sql.append("set " + sqlColumnsAndValues + " ");
//		sql.append("where id = ?");
//
//        try {
//            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
//            stm.setString(columns.get(NAME), e.getName());
//            stm.setString(columns.get(City), e.getCity());
//            stm.setString(columns.get(Locality), e.getLocality());
//            stm.setObject(columns.get(Country), e.getCountry());
//            //stm.setBoolean(columns.get(Deleted), e.getDeleted());
//
//
//            stm.setLong(columnsWithId.get(ID), e.getId());
//
//            stm.executeUpdate();
//           return getEntityById(e.getId());
//
//        } catch (Exception ex) {
//        	throw new ExpenseNoteException("Entity update in error", ex);
//        }
	}

	@Override
	public void delete(Entity e) throws ExpenseNoteException {
		StringBuilder sql = new StringBuilder();
		sql.append("delete ");
		sql.append("from "+TABLE_NAME+" ");
		sql.append("where id = ?");

        try {
            stm = con.prepareStatement(sql.toString());
            stm.setLong(1, e.getId());
            stm.executeUpdate();

        } catch (Exception ex) {
        	throw new ExpenseNoteException("Entity delete in error", ex);
        }

	}

	@Override
	public Entity getEntityById(Long id) throws ExpenseNoteException {
		if(id==null)
			return null;

		String sqlColumns = columnsWithId.keySet().stream().collect(Collectors.joining(", "));
		StringBuilder sql = new StringBuilder();
		sql.append("select " + sqlColumns + " ");
		sql.append("from "+TABLE_NAME+" ");
		sql.append("where id = ?");

		try {
            stm = con.prepareStatement(sql.toString());
            stm.setLong(1, id);

            ResultSet res = stm.executeQuery();
            res.next();
            if(res.getRow() > 0){
            	return mapResult(res);
            }
            return null;

        } catch (SQLException e) {
            throw new ExpenseNoteException(e);
        }
	}

	@Override
	public Entity getEntityByName(String name) throws ExpenseNoteException {

		String sqlColumns = columnsWithId.keySet().stream().collect(Collectors.joining(", "));
		StringBuilder sql = new StringBuilder();
		sql.append("select " + sqlColumns + " ");
		sql.append("from "+TABLE_NAME+" ");
		sql.append("where name = ?");

		try {
            stm = con.prepareStatement(sql.toString());
            stm.setString(1, name.trim());

            ResultSet res = stm.executeQuery();
            res.next();
            if(res.getRow() > 0){
            	return mapResult(res);
            }
            return null;

        } catch (SQLException e) {
            throw new ExpenseNoteException(e);
        }
	}

	@Override
	public List<Entity> getCountries() throws ExpenseNoteException {
		List<Entity> list = new ArrayList<Entity>();
		String sqlColumns = columnsWithId.keySet().stream().collect(Collectors.joining(", "));
		StringBuilder sql = new StringBuilder();
		sql.append("select " + sqlColumns + " ");
		sql.append("from "+TABLE_NAME+" ");

		try {
            stm = con.prepareStatement(sql.toString());

            ResultSet res = stm.executeQuery();

            while( res.next() ){
            	list.add(mapResult(res));
            }

            return list;
        } catch (SQLException e) {
           throw new ExpenseNoteException(e);
        }
	}

}
