package examen;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Embeddable
public class Isbn implements Serializable {

	private int registration_group;
	private int registrant;
	private int publication;
	
	public Isbn(int registration_group, int registrant, int publication) {
		this.registration_group = registration_group;
		this.registrant = registrant;
		this.publication = publication;
	}

	public Isbn() {
		
	}

	public static Isbn Parse(String isbn) {
		var isbnParts = isbn.split("-");
		return new Isbn(Integer.parseInt(isbnParts[0]), 
				Integer.parseInt(isbnParts[1]), 
				Integer.parseInt(isbnParts[2]));
	}

	@Override
	public String toString() {
		return registration_group + "-" + registrant + "-" + publication;
	}
}
