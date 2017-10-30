package logic;

import java.io.PrintWriter;
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
		this.euclidiana = new Euclidiana();
	}
	
	public List<Point> runMapMatching(List<Point> points){ // Para cada ponto, irei coloc�-lo na rede
		System.out.println("Iniciando Map Matching...");
		System.out.println("Quantidade de pontos: " + points.size());
		for(Point p : points){
			Vertice v = procurarVerticeMap(p);
			p.idVertice = v.id;
			p.idVerticeLatitude = v.latitude;
			p.idVerticeLongitude = v.longitude;
//			p.idVertice = procurarVerticeMap(p);			
		}
		System.out.println("Finalizado Map Matching!");
		return points;
	}
	
	public Vertice procurarVerticeMap(Point p){ // Dentro dos v�rtices, ele ir� procurar o v�rtice mais pr�ximo e ir� retornar
		Vertice vProximo = this.vertices.get(0); // Esse v�rtice ficar� sendo trocado para o v�rtice mais pr�ximo (Map Matching)
		for(Vertice v : this.vertices){
			if(euclidiana.dEuclidiana(p, new Point(vProximo.id, vProximo.longitude, vProximo.latitude)) > euclidiana.dEuclidiana(p, new Point(v.id, v.longitude, v.latitude))){
				vProximo = v; // Se o meu vertice mais pr�ximo for maior que a distancia euclidiana do v, ent�o o v ser� meu vertice mais proximo
			}
		}
		// No final da itera��o, teremos que o retorno ser� o vertice mais proximo, ou seja, teremos colocado o ponto p dentro da nossa rede
		return vProximo;
	}
	
	public void exportarCSV(String fileName, List<Point> list){
		try{
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			
			writer.println("student_id;id_taxista;longitude;latitude;id_vertice;id_ponto");
			System.out.println("Exportando arquivo com Map Matching...");
			for (Point p : list) {
				writer.println(p.studentId+";"+p.taxi_id+";"+p.idVerticeLongitude+";"+p.idVerticeLatitude+";"+p.idVertice+";"+p.id_ponto);
//				writer.println(p.studentId+";"+p.taxi_id+";"+p.longitude+";"+p.latitude+";"+p.idVertice+";"+p.id_ponto);
			}
			System.out.println("Arquivo Exportado com Sucesso!");
			
			writer.close();
		}catch (Exception e) {
			System.out.println("[ERROR]: "+e.toString());
		}
	}
	
}
