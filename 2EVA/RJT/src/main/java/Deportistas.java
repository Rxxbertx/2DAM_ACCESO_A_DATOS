import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Deportistas implements Serializable{

	
	@EmbeddedId
	DeportistasID id;

	
	public DeportistasID getId() {
		return id;
	}
	public void setId(DeportistasID id) {
		this.id = id;
	}

	
	
}
