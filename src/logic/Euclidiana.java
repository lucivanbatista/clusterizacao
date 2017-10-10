package logic;

import model.Point;

public class Euclidiana {

	// C�lculo da Dist�ncia Euclidiana
	public double dEuclidiana(Point inicio, Point destino){
//			raiz de (px - qx)^2 + (py - qy)^2
//		double x = Math.pow((inicio.longitude - destino.longitude), 2);
//		double y = Math.pow((inicio.latitude - destino.latitude), 2);
//		return Math.sqrt(x + y);
		return Math.sqrt(Math.pow((inicio.longitude - destino.longitude), 2) + Math.pow((inicio.latitude - destino.latitude), 2));
	}
}
