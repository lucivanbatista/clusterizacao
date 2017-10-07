package model;

public class Point {

	public static int CORE_POINT = 1;
	public static int BORDER_POINT = 0;
	public static int OUTLIER = -1;
	
	public int studentId; // ID do estudante requerido no enunciado do trabalho
	public int taxi_id; // ID do taxi
	public double longitude; // Latitude
	public double latitude; // Longitude
	public int cluster; // Número do cluster
	public boolean visited; // DBSCAN sempre verifica os pontos não visitados, sempre começaremos com false
	//public int type; // ???
	public int weekday; // Dia da semana (1 - Segunda, ..., 5 - Sexta)
	
	
	public Point(int taxi_id, double longitude, double latitude) {
		this.taxi_id = taxi_id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.visited = false;
	}
}

//	/*Map Matching*/
//	public int idVertex;
