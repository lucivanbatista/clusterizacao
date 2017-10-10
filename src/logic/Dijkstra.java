package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import model.Aresta;
import model.Vertice;

public class Dijkstra {
	
	public List<Vertice> Conjunto_Vertices; //V[g]
	public List<Aresta> Conjunto_Arestas; 
	public Map<Integer, Double> distancia; //d[v] - Key = Vertice.id; Value = Distancia do vertice.id até o idInicio
	private static double infinity = 1000000;
	
	public Dijkstra(List<Vertice> vertices, List<Aresta> arestas) { 
		this.Conjunto_Vertices = vertices;
		this.Conjunto_Arestas = arestas;
		this.distancia = new HashMap<>();
	}
	
	public double dijkstra(Integer idInicio, Integer idDestino){ //Metodo principal; Inicio = root
		Vertice inicio = procurarVertice(idInicio); //*Nao sera instanciado futuramente e sim olhado no dao(provavelmente)
		inicio.estimativa = 0; //Como ele é o inicio, entao a estimativa da distancia é 0, ele é a raiz (root)
		
		inicializacao(inicio);

		PriorityQueue<Integer> fila = new PriorityQueue<>(); //Fila de Prioridade (Maior prioridade = menor custo)
		fila.add(inicio.id);
		
		while(!fila.isEmpty()){ //Enquanto a fila de prioridade não estiver vazia, faça
			Vertice head = procurarVertice(fila.poll()); // Ele irá remover o head da fila e irá armazenar no vértice head
			if(head.visitar == true){ // Se ainda não foi aberto*, então...
				head.visitar = false; // Será usado dessa vez e depois não será mais usado
				double headEstimativa = distancia.get(head.id); // Irei pegar a distancia do id do head e armazenar na estimativa
				
				for(Aresta a : procurarVertices(head.id)){ // Para cada aresta que possui o head, então irei...
					Vertice v = procurarVertice(a.verticeDestino); // pegar o outro vertice da ponta 2 da aresta e irei comparar a estimativa com o head (ponta 1)
					double vEstimativa = distancia.get(v.id); // Estimativa do vertice da ponta 2
					
					if(vEstimativa > (headEstimativa + a.custo)){ // Se a estimativa do vertice da ponta 2, for maior que a estimativa do vertice head (ponta 1)
						v.estimativa = headEstimativa + a.custo; //com o custo da aresta, então a estimativa da ponta 2, será a soma da ponta 1 + o custo da aresta;
						distancia.put(v.id, v.estimativa); // Atualizando na lista de distancia dos vertices
						fila.add(v.id); // Adicionando na fila de prioridades
					} 
				} // O while vai se repetir até todos os vértices possuirem uma estimativa
			}
		}
		
		return distancia.get(idDestino); // Será retornado a menor estimativa da distancia até o idDestino
	}
	
	public void inicializacao(Vertice inicio){ // Colocamos primeiramente na pior possibilidade possivel para cada vertice, ou seja, eles terao a distancia de infinito
		for(Vertice v : this.Conjunto_Vertices){
			if(v.id == inicio.id){
				this.distancia.put(inicio.id, 0.0);
			}else{
				this.distancia.put(v.id, infinity);
			}
		}		
	}
	
	public Vertice procurarVertice(Integer idVertice){ // Dentro dos vértices, ele irá procurar o vértice com esse id e irá retornar ele
		for(Vertice v : this.Conjunto_Vertices){
			if(v.id == idVertice){
				return v;
			}
		}
		return null;
	}
	
	public List<Aresta> procurarVertices(Integer idVertice){ // Dentro das arestas, ele irá procurar as arestas que possuem esse vértice
		List<Aresta> arestaComIdVertice = new ArrayList<>();// Lista de arestas que possuem esse vértice
		for(Aresta a : this.Conjunto_Arestas){
			if(a.verticeInicio == idVertice){
				arestaComIdVertice.add(a);
			}
		}
		return arestaComIdVertice;
	}
}
