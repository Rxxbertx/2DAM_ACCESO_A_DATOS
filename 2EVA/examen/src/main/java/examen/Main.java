package examen;

import jakarta.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		var emf = Persistence.createEntityManagerFactory("examen");
		emf.close();

	}

}
