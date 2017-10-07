package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import model.Aresta;
import model.Point;
import model.Vertice;

public class MapDAO {
	
	private final Connection connection;
	
	public MapDAO(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public List<Vertice> selectAllVertices(){
		String sql = "SELECT * FROM table_vertices";
		
		try {
			ArrayList<Vertice> vertices = new ArrayList<>();
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()){
				Vertice v = new Vertice(rs.getInt("id_vertice"), rs.getDouble("longitude_vertice"), rs.getDouble("latitude_vertice"));
				vertices.add(v);
			}
			
			rs.close();
			st.close();
			
			return vertices;
		} catch (Exception e) {
			System.out.println("[ERROR]: " + e.toString());
		}
		return null;
	}
	
	public List<Aresta> selectAllArestas(){
		String sql = "SELECT * FROM table_roads";
		
		try {
			ArrayList<Aresta> arestas = new ArrayList<>();
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()){
				Aresta a = new Aresta(rs.getInt("id_road"), rs.getInt("idInicio"), rs.getInt("idDestino"), rs.getDouble("cost"));
				arestas.add(a);
			}
			
			rs.close();
			st.close();
			
			return arestas;
		} catch (Exception e) {
			System.out.println("[ERROR]: " + e.toString());
		}
		return null;
	}
}
