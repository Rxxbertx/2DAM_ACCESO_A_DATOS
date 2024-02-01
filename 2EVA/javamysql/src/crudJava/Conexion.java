package crudJava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	
	public Connection connection = null;

	
	private static Conexion Instance;
	
	
	private Conexion() {
		// TODO Auto-generated constructor stub
		
		try {
			Class.forName("");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Conexion ObtenerConexion() {
		
		if (Instance!=null) {
			return Instance;
		}else {
			Instance = new Conexion();
			return Instance;
		}
		
	}
	
	
	


    // Método para crear o recuperar la conexión MySqlConnection
    private Connection Connection()
    {
        if (connection != null) return connection;
        
        try {
			return connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/accesodata", "admin", "dam2t");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    
    }

    // Método para abrir una conexión a la base de datos
    public static Connection OpenConnection(Conexion instance)
    {
    	Connection connection = instance.Connection();

        if (connection == null)
        {

            return null;
        }

        try {
			if (!connection.isClosed())
			{

			    return connection;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
			if (connection.isClosed())
			{
			    try
			    {
			        connection.close();
			        connection = instance.Connection();
			       
			    }
			    catch (SQLException ex)
			    {
			        System.out.println(ex.getMessage());
			        return null;
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return connection;
    }

    // Método para cerrar una conexión a la base de datos
    public static void CloseConnection(Conexion instance)
    {
        Connection connection = instance.Connection();

        if (connection == null)
        {
        	System.out.println("No se puede cerrar la conexión, la conexión es nula");
            return;
        }
        else
        {
            try {
				if (!connection.isClosed())
				{
				    try
				    {
				        connection.close();
				        instance.connection = null;
				    	System.out.println("la conexión se cerro correctamente");
				    }
				    catch (SQLException ex)
				    {
				        System.out.println(ex.getMessage());
				        return;
				    }
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

	
}
