package logic;

import java.util.ArrayList;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
import java.util.List;

import dao.MapDAO;
import dao.PointDAO;
import model.Aresta;
import model.Point;
import model.Vertice;

public class Teste {

	public static void teste1(){}
	
	public static void teste2(){ // Teste para Dijkstra
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
		
//		Dijkstra dijkstra = new Dijkstra(Conjunto_Vertices, Conjunto_Aresta);
//		System.out.println("Resultado: " + dijkstra.dijkstra(1, 5));
	}
	
	public static void teste3(){ // Teste para MapDAO - select vertices
		MapDAO map = new MapDAO();
		
		List<Vertice> vertices = map.selectAllVertices();
		
		for(Vertice v : vertices){
			System.out.println("Vertice id: " + v.id + "; Longitude: " + v.longitude + "; Latitude: " + v.latitude);
		}
		
		System.out.println("Contagem Vertices: " + vertices.size());
	}
	
	public static void teste4(){ // Teste para MapDAO - select arestas
		MapDAO map = new MapDAO();
		
		List<Aresta> arestas = map.selectAllArestas();
		
		for(Aresta a : arestas){
			System.out.println("Aresta id: " + a.idAresta + "; Vertice Inicio: " + a.verticeInicio + "; Vertice Destino: " + a.verticeDestino + "; Custo: " + a.custo);
		}
		
		System.out.println("Contagem: " + arestas.size());
	}
	
	public static void teste5(){ // Teste para o MapMatching
		System.out.println("Iniciando Map Matching...");
		MapMatching mm = new MapMatching();
		
		System.out.println("Iniciando PointDAO...");
		PointDAO pointdao = new PointDAO();
		List<Point> points = pointdao.selectPointByHour("20:00:00", "22:00:00", "2008-02-06", 1);
		System.out.println("Armazenando os Pontos...");
//		List<Point> points = pointdao.selectAllPointsByDate("2008-02-02");
		System.out.println("Pontos armazenados!");
		List<Point> newPoints = mm.runMapMatching(points);
		mm.exportarCSV("points_date_02", newPoints);
	}
	
	public static void teste6(){ // Teste para o MapMatching
		System.out.println("Iniciando Map Matching...");
		MapMatching mm = new MapMatching();
		
		System.out.println("Iniciando PointDAO...");
		PointDAO pointdao = new PointDAO();
		
		System.out.println("Armazenando os Pontos do dia 06...");
		List<Point> points = pointdao.selectPointByHour("20:00:00", "22:00:00", "2008-02-06", 3);
		System.out.println("Pontos armazenados!");
		List<Point> newPoints = mm.runMapMatching(points);
		mm.exportarCSV("points_date_06", newPoints);
		
		System.out.println("Armazenando os Pontos do dia 04...");
		points = pointdao.selectPointByHour("20:00:00", "22:00:00", "2008-02-04", 1);
		System.out.println("Pontos armazenados!");
		newPoints = mm.runMapMatching(points);
		mm.exportarCSV("points_date_04", newPoints);
		
		System.out.println("Armazenando os Pontos do dia 05...");
		points = pointdao.selectPointByHour("20:00:00", "22:00:00", "2008-02-05", 2);
		System.out.println("Pontos armazenados!");
		newPoints = mm.runMapMatching(points);
		mm.exportarCSV("points_date_05", newPoints);
	}

	public static void teste7(){
		System.out.println("Inicializando Map Matching...");
		MapMatching mm = new MapMatching();
		
		System.out.println("Iniciando PointDAO...");
		PointDAO pointdao = new PointDAO();

		System.out.println("Pontos armazenados!");
//		List<Point> points = pointdao.selectPointByHour("15:00:00", "15:02:00", "2008-02-04", 1);
		
		List<Point> points = pointdao.selectPointByHour("20:00:00", "20:30:00", "2008-02-04", 1);
		List<Point> newPoints = mm.runMapMatching(points);
		
		Clusterizacao clusterizar = new Clusterizacao();
		int minPoints = 80;
		double eps = 0.01;
//		int minPoints = 3;
//		double eps = 0.05;
		//clusterizado_day_1 = 10 e 0.005
		//clusterizado_day_11 = 10 e 0.01
		List<Point> newPointsCluster = clusterizar.DBScan(minPoints, eps, newPoints);
		clusterizar.exportarCSV("clusterizado_day_111", newPointsCluster);
	}
	
	public static void teste8(){
		System.out.println("Iniciando Map Matching...");
		MapMatching mm = new MapMatching();
		
		System.out.println("Iniciando PointDAO...");
		PointDAO pointdao = new PointDAO();
		
//		List<Point> points = pointdao.selectAllPointsByDate("2008-02-04");
		List<Point> points = pointdao.selectPointByHour("20:00:00", "20:30:00", "2008-02-05", 2);
		List<Point> newPoints = mm.runMapMatching(points);
		mm.exportarCSV("mapMatchingDia5", newPoints);
	}
		
	public static void main(String[] args) {
//		teste1();
//		teste2();
//		teste3();
//		teste4();
//		teste5();
//		teste6();
		teste7();
//		teste8();
	}
}
