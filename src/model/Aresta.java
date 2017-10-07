package model;

public class Aresta {

	public int idAresta;
	public int verticeInicio;
	public int verticeDestino;
	public double custo;
	
	
	public Aresta(int idAresta, int verticeInicio, int verticeDestino, double custo) {
		this.idAresta = idAresta;
		this.verticeInicio = verticeInicio;
		this.verticeDestino = verticeDestino;
		this.custo = custo;
	}
}