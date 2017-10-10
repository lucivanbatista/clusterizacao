package model;

public class Point {

	public static int CORE_POINT = 1; // Ponto que possui dentro de um raio eps, minPoints suficientes
	public static int BORDER_POINT = 0; // Ponto que possui dentro de um raio eps, minPoints insuficientes, mas está dentro da borda de algum core point
	public static int OUTLIER = -1; // Não é core point e nem border point
	
	public int studentId; // ID do estudante requerido no enunciado do trabalho
	public int taxi_id; // ID do taxi
	public double longitude; // Latitude
	public double latitude; // Longitude
	public int cluster; // Número do cluster
	public boolean visitado; // DBSCAN sempre verifica os pontos não visitados, começaremos com false
	public int type; // Core point, border point ou outlier
	public int weekday; // Dia da semana (1 - Segunda, ..., 5 - Sexta)
	
	//Aqui é o seguinte, após termos usado o map-matching, esse idVertex irá representar sobre qual vértice o meu ponto do taxi estará
	public int idVertice;
	
	public Point(int taxi_id, double longitude, double latitude) {
		this.taxi_id = taxi_id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.visitado = false;
	}
}

	
