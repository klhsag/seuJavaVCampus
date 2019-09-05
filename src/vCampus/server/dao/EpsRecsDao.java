package vCampus.server.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import vCampus.server.dao.driver.ConnectionManager;
import vCampus.server.dao.model.ExpenseRec;

public class EpsRecsDao {
	
	public static void addRec(ExpenseRec s){
	    Connection conn = null;
	    PreparedStatement ptmt = null;
	    ResultSet rs = null;
	    int id = -1;
	    try{
	    	conn = ConnectionManager.getConnection();
	        String sql = "INSERT INTO BankRecSheet(personID, figure, date, source, details)"
	                    +"values(?, ?, ?, ?, ?)";
	        ptmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	        ptmt.setInt(1, s.getPersonID());
	        ptmt.setInt(2, s.getFigure());
	        ptmt.setDate(3, new Date(s.getDate().getTime()));
	        ptmt.setString(4, s.getSource());
	        ptmt.setString(5, s.getDetails());
	        ptmt.executeUpdate();
	        rs = ptmt.getGeneratedKeys();
	        if (!rs.next()) throw new SQLException();
	        id = rs.getInt(1);
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }finally {
	    	try{
	    		if (ptmt!=null) ptmt.close();
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    	}
	    	if (conn!=null) ConnectionManager.close(conn);
	    }
	    s.setId(id);
	}	

	public static void delRec(int ID){
	    Connection conn = null;
	    PreparedStatement ptmt = null;
	    try{
	    	conn = ConnectionManager.getConnection();
	        String sql = "delete from BankRecSheet where id=?";
	        ptmt = conn.prepareStatement(sql);
	        ptmt.setInt(1, ID);
	        ptmt.execute();
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }finally {
	    	try{
	    		if (ptmt!=null) ptmt.close();
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    	}
	    	if (conn!=null) ConnectionManager.close(conn);
	    }	
	}		
	
	public static List<ExpenseRec> getRec(int personID){
	    Connection conn = null;
	    PreparedStatement ptmt = null;
	    ResultSet rs = null;
		ExpenseRec s = null;
		List<ExpenseRec> gs = new ArrayList<ExpenseRec>();
	    try{
	    	conn = ConnectionManager.getConnection();
			String sql = "select * from BankRecSheet where personID=?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, personID);
			rs = ptmt.executeQuery();
			
			while(rs.next()){
				s = new ExpenseRec();
				s.setId(rs.getInt("id"));
				s.setPersonID(rs.getInt("personID"));
				s.setFigure(rs.getInt("figure"));
				s.setDate(rs.getDate("date"));
				s.setSource(rs.getString("source"));
				gs.add(s);
			}
			return gs;
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    	return gs;
	    }finally {
	    	try{
	    		if (ptmt!=null) ptmt.close();
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    	}
	    	try{
	    		if (rs!=null) rs.close();
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    	}
	    	if (conn!=null) ConnectionManager.close(conn);
	    }
	}	
	
	

}
