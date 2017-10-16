package logic;

import java.io.PrintWriter;
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
	
	public List<Point> DBScan(int minPoints, double eps, List<Point> pontos){
		this.points = pontos;
		int clusterId = 0;
		System.out.println("Quantidade dos Pontos: " + pontos.size());
		
		for(Point point : pontos){
			if(point.visitado == false){ // Se ponto n�o foi visitado, ent�o
				point.visitado = true; // N�o irei mais visitar ele
				
				List<Point> vizinhos = getVizinhos(eps, minPoints, point); // Pegar os vizinhos deste ponto
				List<Point> vizinhos_taxis = getVizinhosTaxis(vizinhos); // Pegar os vizinhos taxistas distintos deste ponto
				
				int countVizinhos = vizinhos_taxis.size();
				
				if(minPoints > countVizinhos){ // Se o minPoints for maior que os meus vizinhos, ent�o ele ser� um outlier
					point.cluster = Point.OUTLIER;
					point.type = Point.OUTLIER;
				}else{
					clusterId++; // Iremos para o pr�ximo cluster
					point.cluster = clusterId; // Esse ponto ficar� dentro do clusterId atual
					point.type = Point.CORE_POINT;// Ele ser� um centro
					expandirCluster(vizinhos, clusterId, minPoints, eps);
				}
			}
		}
		System.out.println("Quantidade de Clusters criados: " + clusterId);
		return this.points;
	}
	
	public void expandirCluster(List<Point> vizinhos, int clusterId, int minPoints, double eps){ // Iremos descobrir at� onde o cluster ir�
		System.out.println("Estamos expandindo o cluster " + clusterId);
		
		for(Point p : vizinhos){ // Primeiro associamos os cluster vizinhos ao mesmo cluster do core
			p.cluster = clusterId;
		}
		
		while(vizinhos.size() > 0){ // Temos que fazer para todos os vizinhos
			Point point = vizinhos.get(0); // Pegaremos o primeiro vizinho da lista
			vizinhos.remove(0); // N�o usaremos mais ele, ent�o iremos o remover da lista
			
			if(point.visitado == false){ // Se ponto n�o foi visitado, ent�o
				point.visitado = true; // N�o irei mais visitar ele
				
				List<Point> vizinhosDestePonto = getVizinhos(eps, minPoints, point); // Pegar os vizinhos deste ponto
				List<Point> vizinhosDestePonto_taxis = getVizinhosTaxis(vizinhos); // Pegar os vizinhos taxistas distintos deste ponto
				
				int countVizinhosDestePonto = vizinhosDestePonto_taxis.size();
				
				
				for (Point p : vizinhosDestePonto) { // Os vizinhos deste ponto tamb�m ir�o pertencer ao cluster
					p.cluster = clusterId;
				}
				
				if(minPoints > countVizinhosDestePonto){ // Ele n�o ser� um outlier, mas sim um border
					point.type = Point.BORDER_POINT;
				}else{
					point.type = Point.CORE_POINT;
					vizinhos.addAll(vizinhosDestePonto); // Adicionaremos a nossa lista de vizinhos, os vizinhosDestePonto para ele tamb�m ser percorrido
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
			if(eps > d){ // Caso o raio for maior que a distancia, ent�o quer dizer que esse 'ponto' est� dentro do range e ser� adicionado como vizinho
				vizinhos.add(ponto);
			}
		}
		return vizinhos;
	}
	
	// Pegar os vizinhos taxistas (distintos)
	public List<Point> getVizinhosTaxis(List<Point> vizinhos){
		ArrayList<Point> vizinhos_taxis = new ArrayList<Point>();
		for (Point point : vizinhos) { // Para cada ponto nos vizinhos, ent�o irei analisar
			if(contains(vizinhos_taxis, point) == false){ // se na nova lista de vizinhos_taxis, o taxi deste ponto est� na nova lista?
				vizinhos_taxis.add(point); // se n�o estiver (false), ent�o irei adicionar
			}
		} // FUTURO: PODERIA USAR UM SET E JOGAR TODOS OS ID DOS TAXISTAS PARA ELE
		return vizinhos_taxis;
	}
	
	public boolean contains(ArrayList<Point> vizinhos_taxis, Point p){ // Fun��o criada para ...
		for (Point point : vizinhos_taxis) { // Para cada ponto na nova lista vizinhos_taxis
			if(point.taxi_id == p.taxi_id){ // Se id do taxi do ponto dentro da nova lista == id do ponto do taxi passado por parametro 
				return true; // n�o ser� adicionado, pois j� existe
			}
		}
		return false; // ser� adicionado a lista de vizinhos_taxis
	}	
	
	public void exportarCSV(String fileName, List<Point> list){
		try{
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			writer.println("student_id;taxi_id;weekday;longitude;latitude;cluster;iscore;id_vertice");
			System.out.println("Exportando arquivo com Clusters ...");
			for (Point p : list) {
				writer.println(p.studentId+";"+p.taxi_id+";"+p.weekday+";"+p.longitude+";"+p.latitude+";"+p.cluster+";"+p.type+";"+p.idVertice);
			}
			System.out.println("Arquivo Exportado com sucesso!");
			
			writer.close();
		}catch (Exception e) {
			System.out.println("[ERROR]: "+e.toString());
		}
	}
	
}