package model;

public class Vertice {

	public int id;
	public double longitude;
	public double latitude;
	public double estimativa;
	public Vertice antecessor;
	public boolean visitar; // Atributo usado no Dijkstra (true = iremos visitar; false = não visitaremos mais)
	
	public Vertice() {
		this.visitar = true;
		this.estimativa = 0;
	}
	
	public Vertice(int id){
		this.visitar = true;
		this.estimativa = 0;
		this.id = id;
	}
	
	public Vertice(int id, double longitude, double latitude){
		this.visitar = true;
		this.estimativa = 0;
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
}
