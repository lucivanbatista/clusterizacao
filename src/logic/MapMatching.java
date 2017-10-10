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
	
	public void runMapMatching(List<Point> points){ // Para cada ponto, irei coloc�-lo na rede
		for(Point p : points){
			p.idVertice = procurarVerticeMap(p);
		}
	}
	
	public int procurarVerticeMap(Point p){ // Dentro dos v�rtices, ele ir� procurar o v�rtice mais pr�ximo e ir� retornar
		Vertice vProximo = this.vertices.get(0); // Esse v�rtice ficar� sendo trocado para o v�rtice mais pr�ximo (Map Matching)
		for(Vertice v : this.vertices){
			if(euclidiana.dEuclidiana(p, new Point(0, vProximo.longitude, vProximo.latitude)) > euclidiana.dEuclidiana(p, new Point(0, v.longitude, v.latitude))){
				vProximo = v; // Se o meu vertice mais pr�ximo for maior que a distancia euclidiana do v, ent�o o v ser� meu vertice mais proximo
			}
		}
		// No final da itera��o, teremos que o retorno ser� o vertice mais proximo, ou seja, teremos colocado o ponto p dentro da nossa rede
		return vProximo.id;
	}
	
}
