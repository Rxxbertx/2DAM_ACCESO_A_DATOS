import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


	public class Departamento {


	    private int dept_no;

	    private String dnombre;
	    
	    private String loc;
	    
	    
	     public Departamento() {
			// TODO Auto-generated constructor stub
		}


		public Departamento(int dept_no, String dnombre, String loc) {
			super();
			this.dept_no = dept_no;
			this.dnombre = dnombre;
			this.loc = loc;
		}


		public int getDept_no() {
			return dept_no;
		}


		public void setDept_no(int dept_no) {
			this.dept_no = dept_no;
		}


		public String getDnombre() {
			return dnombre;
		}


		public void setDnombre(String dnombre) {
			this.dnombre = dnombre;
		}


		public String getLoc() {
			return loc;
		}


		public void setLoc(String loc) {
			this.loc = loc;
		}
	     
	     

	    // Agrega otras propiedades de la entidad y sus métodos getter/setter según sea necesario+
		
		
		
		
	}

