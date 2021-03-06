package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import model.Point;

public class PointDAO {
	
	private final Connection connection;
	
	public PointDAO(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public List<Point> selectPointByHour(String hourBegin, String hourEnd, String data, int weekday){
		//Funcionam apenas se hours forem Integer
//		String sql = "SELECT * FROM pteste WHERE date_part('hour', tempo) >= extract(hour from time " + hourBegin + ");";
//		String sql = "SELECT * FROM pteste WHERE date_part('hour', tempo) >= " + hourBegin + " AND date_part('hour', tempo) < " + hourEnd;
//		String sql = "SELECT * FROM pteste WHERE date_part('hour', tempo) >= " + hourBegin + " AND date_part('hour', tempo) < " + hourEnd + " AND date = '" + data + "'";
		
		
		String sql = "SELECT * FROM points WHERE date_time_timestamp BETWEEN '" + data + " " + hourBegin + "' AND  '" + data + " " + hourEnd + "'";
		
		try {
			ArrayList<Point> points = new ArrayList<>();
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()){
				Point p = new Point(rs.getInt("taxi_id"), rs.getDouble("longitude"), rs.getDouble("latitude"), rs.getInt("id_ponto"));
				p.studentId = 376762;
				p.weekday = weekday;
				points.add(p);
			}
			
			rs.close();
			st.close();
			
			return points;
		} catch (Exception e) {
			System.out.println("[ERROR]: " + e.toString());
		}
		return null;
	}
	
	public List<Point> selectAllPointsByDate(String date){
		String sql = "SELECT * FROM points where date_time = '" + date + "'";
		
		try {
			ArrayList<Point> points = new ArrayList<>();
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()){
				Point p = new Point(rs.getInt("taxi_id"), rs.getDouble("longitude"), rs.getDouble("latitude"), rs.getInt("id_ponto"));
				p.studentId = 376762;
				points.add(p);
			}
			
			rs.close();
			st.close();
			
			return points;
		} catch (Exception e) {
			System.out.println("[ERROR]: " + e.toString());
		}
		return null;
	}
}
