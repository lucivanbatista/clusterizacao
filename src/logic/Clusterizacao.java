package logic;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
	public HashMap<Integer, Point> pontos_visitados = new HashMap<>(); // Chave = Id_Ponto
	
	public Dijkstra dijkstra;
	public Euclidiana euclidiana;
	
	public Clusterizacao(){
		mapdao = new MapDAO();
		vertices =  mapdao.selectAllVertices();
		arestas = mapdao.selectAllArestas();
		dijkstra = new Dijkstra(vertices, arestas);
		euclidiana = new Euclidiana();
	}
	
	public List<Point> DBScan(int minPoints, double eps, List<Point> pontos){
		this.points = pontos;
		int clusterId = 0;
		System.out.println("Quantidade dos Pontos: " + this.points.size());
		for(Point point : this.points){
			if(point.visitado == false){ // Se ponto não foi visitado, então
				point.visitado = true; // Não irei mais visitar ele
				
				List<Point> vizinhos = getVizinhos(eps, point); // Pegar os vizinhos deste ponto
				int countVizinhos = vizinhos.size();
				
				if(minPoints > countVizinhos){ // Se o minPoints for maior que os meus vizinhos, então ele será um outlier
					point.cluster = Point.OUTLIER;
					point.type = Point.OUTLIER;
				}else{
					clusterId++; // Iremos para o próximo cluster
					point.cluster = clusterId; // Esse ponto ficará dentro do clusterId atual
					point.type = Point.CORE_POINT;// Ele será um centro
					expandirCluster(vizinhos, clusterId, minPoints, eps);
				}
			}
		}
		System.out.println("Quantidade de Clusters criados: " + clusterId);
		return this.points;
	}
	
	public void expandirCluster(List<Point> vizinhos, int clusterId, int minPoints, double eps){ // Iremos descobrir até onde o cluster irá
		System.out.println("Estamos expandindo o cluster " + clusterId);
		
		while(vizinhos.size() > 0){ // Temos que fazer para todos os vizinhos
			Point point = vizinhos.get(0); // Pegaremos o primeiro vizinho da lista
			point.cluster = clusterId;
			vizinhos.remove(0); // Não usaremos mais ele, então iremos o remover da lista
			
			if(point.visitado == false){ // Se ponto não foi visitado, então
				point.visitado = true; // Não irei mais visitar ele
				
				List<Point> vizinhosDestePonto = getVizinhos(eps, point); // Pegar os vizinhos deste ponto
				int countVizinhosDestePonto = vizinhos.size();
				
				if(minPoints > countVizinhosDestePonto){ // Ele não será um outlier, mas sim um border
					point.type = Point.BORDER_POINT;
				}else{
					point.type = Point.CORE_POINT;
					vizinhos.addAll(0, vizinhosDestePonto); // Adicionaremos a nossa lista de vizinhos, os vizinhosDestePonto para ele também ser percorrido
				}	
			}
		}		
	}
	
	// Pegar os vizinhos de um determinado ponto
	public List<Point> getVizinhos(double eps, Point p){
		List<Point> vizinhos = new ArrayList<>();
		for(Point ponto : this.points){
			double d2 = this.euclidiana.dEuclidiana(p, ponto); // Distancia usando o euclidiana
			if(eps >= d2){ // se d2 > eps não é vizinho
				double d = this.dijkstra.dijkstra(p.idVertice, ponto.idVertice); // Distancia usando o dijstra
				if(eps >= d){ // Caso o raio for maior que a distancia, então quer dizer que esse 'ponto' está dentro do range e será adicionado como vizinho
					if(!pontos_visitados.containsKey(ponto.id_ponto)){
						this.pontos_visitados.put(ponto.id_ponto, ponto);
						vizinhos.add(ponto);
					}
				}
			}
		}
		return vizinhos;
	}
	
//	public int getVizinhosTaxis(List<Point> vizinhos, Point p){
//		Set<Integer> vizinhos_taxis = new HashSet<Integer>();
//		for (Point point : vizinhos) { // Para cada ponto nos vizinhos, então irei analisar
//			if(point.taxi_id != p.taxi_id){
//				vizinhos_taxis.add(point.taxi_id);
//			}
//		}
//		return vizinhos_taxis.size();
//	}
	
	public void exportarCSV(String fileName, List<Point> list){
		try{
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			writer.println("student_id;taxi_id;weekday;longitude;latitude;cluster;iscore;id_vertice;longitude_vertice;latitude_vertice");
			System.out.println("Exportando arquivo com Clusters ...");
			for (Point p : list) {
				writer.println(p.studentId+";"+p.taxi_id+";"+p.weekday+";"+p.longitude+";"+p.latitude+";"+p.cluster+";"+p.type+";"+p.idVertice+";"+p.idVerticeLongitude+";"+p.idVerticeLatitude);
			}
			System.out.println("Arquivo Exportado com sucesso!");
			
			writer.close();
		}catch (Exception e) {
			System.out.println("[ERROR]: "+e.toString());
		}
	}
	
}