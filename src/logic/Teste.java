package logic;

import java.util.ArrayList;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
import java.util.List;

import dao.PointDAO;
import model.Aresta;
import model.Point;
import model.Vertice;

public class Teste {

	public static void teste1(){
		PointDAO pointdao = new PointDAO();
		
//		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//		Date hora = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
//		String dataFormatada = sdf.format(hora);
		
		List<Point> points = pointdao.selectPointByHour(20, 22, "2008-02-02");
		
		for(Point p : points){
			System.out.println("Taxi id: " + p.taxi_id + "; Longitude: " + p.longitude + "; Latitude: " + p.latitude + "; Student: " + p.studentId);
		}
		
		System.out.println("Contagem: " + points.size());
	}
	
	public static void teste2(){
		Vertice a = new Vertice(1, 1, 3);
		Vertice b = new Vertice(2, 3, 4);
		Vertice c = new Vertice(3, 3, 1);
		Vertice d = new Vertice(4, 5, 4);
		Vertice e = new Vertice(5, 5, 2);
		
		Aresta o = new Aresta(10, 1, 2, 5);
		Aresta p = new Aresta(11, 1, 3, 5);
		Aresta q = new Aresta(12, 2, 4, 5);
		Aresta r = new Aresta(13, 2, 5, 4);
		Aresta s = new Aresta(14, 4, 5, 2);
		Aresta t = new Aresta(15, 3, 5, 8);
		
		List<Vertice> Conjunto_Vertices = new ArrayList<>();
		Conjunto_Vertices.add(a);
		Conjunto_Vertices.add(b);
		Conjunto_Vertices.add(c);
		Conjunto_Vertices.add(d);
		Conjunto_Vertices.add(e);
		
		List<Aresta> Conjunto_Aresta = new ArrayList<>();
		Conjunto_Aresta.add(o);
		Conjunto_Aresta.add(p);
		Conjunto_Aresta.add(q);
		Conjunto_Aresta.add(r);
		Conjunto_Aresta.add(s);
		Conjunto_Aresta.add(t);
		
		
		Dijkstra dijkstra = new Dijkstra(Conjunto_Vertices, Conjunto_Aresta);
		System.out.println("Resultado: " + dijkstra.dijkstra(1, 5));
	}
	
	public static void main(String[] args) {
//		teste1();
//		teste2();
		
	}
}
