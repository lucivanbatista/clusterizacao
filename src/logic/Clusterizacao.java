package logic;

import java.util.ArrayList;
import java.util.List;

import dao.MapDAO;
import model.Aresta;
import model.Point;
import model.Vertice;

public class Clusterizacao {

	public List<Point> points;
	
	public MapDAO mapdao;
	public List<Vertice> vertices;
	public List<Aresta> arestas;
	
	public Dijkstra dijkstra;
	public Euclidiana euclidiana;
	
	public Clusterizacao(){
		mapdao = new MapDAO();
		vertices =  mapdao.selectAllVertices();
		arestas = mapdao.selectAllArestas();
		dijkstra = new Dijkstra(vertices, arestas);
	}
	
	public void DBScan(int minPoints, double eps, List<Point> pontos){
		this.points = pontos;
		int clusterId = 0;
		System.out.println("Quantidade dos Pontos: " + pontos.size());
		
		for(Point point : pontos){
			if(point.visitado == false){ // Se ponto n�o foi visitado, ent�o
				point.visitado = true; // N�o irei mais visitar ele
				
				List<Point> vizinhos = new ArrayList<>();
//				int totalVizinhos = 
				
			}
		}
	}
	
	// Pegar os vizinhos vizinhos de um determinado ponto
	public List<Point> getVizinhos(double eps, int minPoints, Point p){
		List<Point> vizinhos = new ArrayList<>();
		for(Point ponto : this.points){
			double d = this.dijkstra.dijkstra(p.idVertice, ponto.idVertice); // Distancia usando o dijstra
			double d2 = this.euclidiana.dEuclidiana(p, ponto); // Distancia usando o euclidiana
			
			if(eps > d){ // Caso o raio for maior que a distancia, ent�o quer dizer que esse 'ponto' est� dentro do range e ser� adicionado como vizinho
				vizinhos.add(ponto);
			}
		}
		
		// N�O COMPLETO
		
	}
	
}
