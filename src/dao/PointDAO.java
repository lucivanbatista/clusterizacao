package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import factory.ConnectionFactory;
import model.Point;

public class PointDAO {
	
	private final Connection connection;
	
	public PointDAO(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public List<Point> selectPointByHour(Integer hourBegin, Integer hourEnd, String data){
//		String sql = "SELECT * FROM pteste WHERE date_part('hour', tempo) >= extract(hour from time " + hourBegin + ");";
		String sql = "SELECT * FROM pteste WHERE date_part('hour', tempo) >= " + hourBegin + " AND date_part('hour', tempo) <= " + hourEnd;
		try {
			ArrayList<Point> points = new ArrayList<>();
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			int count = 0;
			while(rs.next() || count < 1000){
				Point p = new Point(rs.getInt("taxi_id"), rs.getDouble("longitude"), rs.getDouble("latitude"));
				p.studentId = 376762;
				points.add(p);
				count++;
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
