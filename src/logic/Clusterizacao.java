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
		euclidiana = new Euclidiana();
	}
	
	public void DBScan(int minPoints, double eps, List<Point> pontos){
		this.points = pontos;
		int clusterId = 0;
		System.out.println("Quantidade dos Pontos: " + pontos.size());
		
		for(Point point : pontos){
			if(point.visitado == false){ // Se ponto não foi visitado, então
				point.visitado = true; // Não irei mais visitar ele
				
				List<Point> vizinhos = getVizinhos(eps, minPoints, point); // Pegar os vizinhos deste ponto
				List<Point> vizinhos_taxis = getVizinhosTaxis(vizinhos); // Pegar os vizinhos taxistas distintos deste ponto
				
				int countVizinhos = vizinhos_taxis.size();
				
				if(minPoints > countVizinhos){ // Se o minPoints for maior que os meus vizinhos, então ele será um outlier
					point.cluster = Point.OUTLIER;
					point.type = Point.OUTLIER;
				}else{
					clusterId++; // Iremos para o próximo cluster
					point.cluster = clusterId; // Esse ponto ficará dentro do clusterId atual
					point.type = Point.CORE_POINT;// Ele será um centro
//					expandCluster(vizinhos, clusterId, minPoints, eps);
				}
				 
				
			}
		}
	}
	
	// Pegar os vizinhos de um determinado ponto
	public List<Point> getVizinhos(double eps, int minPoints, Point p){
		List<Point> vizinhos = new ArrayList<>();
		for(Point ponto : this.points){
			double d = this.dijkstra.dijkstra(p.idVertice, ponto.idVertice); // Distancia usando o dijstra
//			double d2 = this.euclidiana.dEuclidiana(p, ponto); // Distancia usando o euclidiana
			if(eps > d){ // Caso o raio for maior que a distancia, então quer dizer que esse 'ponto' está dentro do range e será adicionado como vizinho
				vizinhos.add(ponto);
			}
		}
		return vizinhos;
	}
	
	// Pegar os vizinhos taxistas (distintos)
	public List<Point> getVizinhosTaxis(List<Point> vizinhos){
		ArrayList<Point> vizinhos_taxis = new ArrayList<Point>();
		for (Point point : vizinhos) { // Para cada ponto nos vizinhos, então irei analisar
			if(contains(vizinhos_taxis, point) == false){ // se na nova lista de vizinhos_taxis, o taxi deste ponto está na nova lista?
				vizinhos_taxis.add(point); // se não estiver (false), então irei adicionar
			}
		} // FUTURO: PODERIA USAR UM SET E JOGAR TODOS OS ID DOS TAXISTAS PARA ELE
		return vizinhos_taxis;
	}
	
	public boolean contains(ArrayList<Point> vizinhos_taxis, Point p){ // Função criada para ...
		for (Point point : vizinhos_taxis) { // Para cada ponto na nova lista vizinhos_taxis
			if(point.taxi_id == p.taxi_id){ // Se id do taxi do ponto dentro da nova lista == id do ponto do taxi passado por parametro 
				return true; // não será adicionado, pois já existe
			}
		}
		return false; // será adicionado a lista de vizinhos_taxis
	}	
}