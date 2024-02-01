package javamysqlTEST;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import crudJava.Departamento;
import crudJava.DepartamentoService;

class TestCrudBbdd {

	// public Departamento obj = new Departamento(2, "Marketing", "Burgos");

	@Test
	void testCreate() {


	// Crear un objeto Departamento para la prueba
	Departamento dept = new Departamento(2, "Marketing", "Burgos");

	// Llamar al método Create y almacenar el resultado
	boolean resultado = DepartamentoService.Find(dept);

	// Verificar si la creación fue exitosa
	assertTrue(resultado, "La creación del departamento ha fallado");
   		
	}

}
