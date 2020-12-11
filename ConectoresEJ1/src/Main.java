import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception {
        JDBC j = new JDBC();
        j.abrirConexion("ad", "localhost", "java", "");
        j.altaAlumno("test", "test", 1, 20);
        j.cerrarConexion();
    }
}

class JDBC {
    private Connection conexion;
    
    public void abrirConexion(String bd, String servidor , String usuario, String password) {
        try {
            String url = String.format("jdbc:mariadb://%s:3306/%s",servidor,bd);
            this.conexion = DriverManager.getConnection(url,usuario,password);
        //  Establecemos la conexión con la BD
            if (this.conexion !=null) System.out.println ("Conectado a la base de datos "+bd+" en "+servidor);
            else System.out.println ("No se ha conectado a la base de datos "+bd+" en "+servidor);
        }catch (SQLException e) {
            System.out.println("SQLException: " +e.getLocalizedMessage());
            System.out.println("SQLState: " +e.getSQLState());
            System.out.println("Código error: " +e.getErrorCode());
        }
    }

    public void cerrarConexion (){
        try {
            this.conexion.close();
        }catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: "+e.getLocalizedMessage());
        }
    }

    public void altaAlumno(String nombre,String apellidos,int altura,int aula){
        try {
            Statement statement = this.conexion.createStatement();
            statement.execute(String.format("insert into alumnos(nombre,apellidos,altura,aula) values('%s','%s',%d,%d);",nombre,apellidos,altura,aula));
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}