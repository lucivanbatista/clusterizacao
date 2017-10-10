package logic;

import java.util.List;

import dao.MapDAO;
import model.Point;
import model.Vertice;

public class MapMatching {
	
	public List<Vertice> vertices;
	public MapDAO mapdao;
	public Euclidiana euclidiana;
	
	public MapMatching() {
		this.mapdao = new MapDAO();
		this.vertices = mapdao.selectAllVertices();
	}
	
	public void runMapMatching(List<Point> points){ // Para cada ponto, irei colocá-lo na rede
		for(Point p : points){
			p.idVertice = procurarVerticeMap(p);
		}
	}
	
	public int procurarVerticeMap(Point p){ // Dentro dos vértices, ele irá procurar o vértice mais próximo e irá retornar
		Vertice vProximo = this.vertices.get(0); // Esse vértice ficará sendo trocado para o vértice mais próximo (Map Matching)
		for(Vertice v : this.vertices){
			if(euclidiana.dEuclidiana(p, new Point(0, vProximo.longitude, vProximo.latitude)) > euclidiana.dEuclidiana(p, new Point(0, v.longitude, v.latitude))){
				vProximo = v; // Se o meu vertice mais próximo for maior que a distancia euclidiana do v, então o v será meu vertice mais proximo
			}
		}
		// No final da iteração, teremos que o retorno será o vertice mais proximo, ou seja, teremos colocado o ponto p dentro da nossa rede
		return vProximo.id;
	}
	
}
