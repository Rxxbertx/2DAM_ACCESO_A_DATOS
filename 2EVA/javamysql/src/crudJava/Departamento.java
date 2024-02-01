package crudJava;

public class Departamento implements ICrud{


	
	public int dept_no;
	public String dnombre;
	public String loc;
	
	public Departamento() {
		// TODO Auto-generated constructor stub
	}
	
	public Departamento( int numero, String nombre, String localidad) {
		
		dept_no =numero;
		dnombre = nombre;
		loc = localidad;
		
	};

	@Override
	public void Create() {

		
		
		
		
		
	}

	@Override
	public void Read() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
