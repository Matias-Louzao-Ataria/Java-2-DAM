import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception {
        JDBC j = new JDBC();
        /*j.abrirConexion("ad", "localhost", "java", "");
        //j.altaAlumno("test", "test", 1, 20);
        //j.modificarAsignatura(10, "nombre", "apellidos", 12, 21);
        //j.borrarAlumno("test", "test", 1, 20);
        //j.altaAsignatura(9,"test");
        //j.modificarAsignatura(9, "a");
        //j.borrarAsignatura(9,"test");
        j.aulasConAlumnos();
        j.alumnosAprobados();
        j.asignaturasSinAlumnos();
        j.cerrarConexion();*/
        int[] veces = {1};//,10,  100,1000, 10000, 100000, 1000000, 10000000};
        for (int i = 0; i < veces.length; i++) {
            System.out.println("Se ha tardado "+j.ejecutarVeces(veces[i])+" ms en ejecutar "+veces[i]+" veces.");
        }
    }
}

class JDBC {
    private Connection conexion;
    
    public void abrirConexion(String bd, String servidor , String usuario, String password) {
        try {
            String url = String.format("jdbc:mariadb://%s:3306/%s?useServerPrepStmts=true",servidor,bd);
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

    public int ejecutarUpdate(String str){
        Statement statement = null;
        try {
            statement = this.conexion.createStatement();
            return statement.executeUpdate(str);
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }finally{
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
        return -1;
    }

    public ResultSet ejecutarQuery(String str){
        Statement statement = null;
        try {
            statement = this.conexion.createStatement();
            return statement.executeQuery(str);
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }finally{
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
        return null;
    }

    public int altaAlumno(String nombre,String apellidos,int altura,int aula){
        return ejecutarUpdate(String.format("insert into alumnos(nombre,apellidos,altura,aula) values('%s','%s',%d,%d);",nombre,apellidos,altura,aula));
    }

    public int borrarAlumno(String nombre,String apellidos,int altura,int aula){
        return ejecutarUpdate(String.format("delete from alumnos where nombre = '%s' && apellidos = '%s' && altura = %d && aula = %d;",nombre,apellidos,altura,aula));
    }

    public int altaAsignatura(int cod,String nombre){
        return ejecutarUpdate(String.format("insert into asignaturas(cod,nombre) values(%d,'%s');",cod,nombre));
    }

    public int borrarAsignatura(int cod,String nombre){
        return ejecutarUpdate(String.format("delete from asignaturas where cod = %d && nombre = '%s';",cod,nombre));
    }

    /**
     * Modifica alumno en base al código.
     * @param cod Código del alumno a modificar.
     * @param nombre Nuevo nombre;
     * @param apellidos Nuevos apellidos.
     * @param altura Nueva altura.
     * @param aula Nueva aula.
     */
    public int modificarAlumno(int cod,String nombre,String apellidos,int altura,int aula){
        return ejecutarUpdate(String.format("update alumnos set nombre = '%s',apellidos = '%s',altura = %d,aula = %d where codigo = %d;",nombre,apellidos,altura,aula,cod));
    }

    public int modificarAsignatura(int cod,String nombre){
        return ejecutarUpdate(String.format("update asignaturas set nombre = '%s' where cod = %d;",nombre,cod));
    }

    public void aulasConAlumnos(){
            ResultSet rs = ejecutarQuery("select nombreAula from aulas where numero in (select aula from alumnos);");
            try {
                while (rs.next()) {
                    System.out.println("NombreAula: " + rs.getString("nombreAula"));
                }
            } catch (SQLException e) {
                System.err.println(e.getLocalizedMessage());
            }
    }

    public void alumnosAprobados(){
        ResultSet rs = ejecutarQuery("select alumnos.nombre,asignaturas.nombre,notas.nota from alumnos join notas on alumnos.codigo = notas.alumno join asignaturas on notas.asignatura = asignaturas.cod where notas.nota >= 5;");
        try{    
            while(rs.next()){
                System.out.print("Nombre: "+rs.getString("alumnos.nombre")+"|");
                System.out.print("Asignatura: "+rs.getString("asignaturas.nombre")+"|");
                System.out.print("Nota: "+rs.getString("notas.nota"));
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public void asignaturasSinAlumnos(){
        ResultSet rs = ejecutarQuery("select nombre from asignaturas where cod not in(select asignatura from notas);");
        try{    
            while(rs.next()){
                System.out.println("Asignatura: "+rs.getString("asignaturas.nombre"));
            }
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public void alumnosPorPatronDeNombreYAlturaEJ6(String patron,int altura){
        ResultSet result = this.ejecutarQuery(String.format("select * from alumnos where nombre like '%s' && altura > %d",patron,altura));
        try{
            while(result.next()){
                System.out.println(result.getString("nombre")+" "+result.getInt("altura"));
            }
        }catch(SQLException e){
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void alumnosPorPatronSentenciaPreparadaEJ6(String patron,int altura){
        try{
            PreparedStatement statement = this.conexion.prepareStatement("select * from alumnos where nombre like ? && altura > ?");
            statement.setString(1, patron);
            statement.setInt(2, altura);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                System.out.println(result.getString("nombre")+" "+result.getInt("altura"));
            }
        }catch(SQLException e){
            System.err.println(e.getLocalizedMessage());
        }
    }

    public int createColumnEJ9(String table,String field,String dataType,String properties){
        /*String query = String.format("CREATE TABLE %s");
        for(int i = 0;i < field.length;i++){
            if(i != 0 && i != field.length-1){
                query += ",";
            }
            query += String.format(" (%s %s %s);",table,field[i],dataType[i],properties[i]);
        }*/
        String query = String.format("alter table %s add column %s %s %s;",table,field,dataType,properties);
        return this.ejecutarUpdate(query);

    }

    public long ejecutarVeces(int veces){
        long time = System.currentTimeMillis();
        for (int i = 0; i < veces; i++) {
            this.abrirConexion("ad", "localhost", "java", "");
            this.altaAlumno("test", "test", 1, 20);
            this.modificarAsignatura(9, "nombre");
            this.borrarAlumno("test", "test", 1, 20);
            this.altaAsignatura(10,"test");
            this.modificarAsignatura(10, "a");
            this.borrarAsignatura(10,"a");
            this.aulasConAlumnos();
            this.alumnosAprobados();
            this.asignaturasSinAlumnos();
            this.alumnosPorPatronDeNombreYAlturaEJ6("%a%", 100);
            this.alumnosPorPatronSentenciaPreparadaEJ6("%a%", 100);
            this.createColumnEJ9("alumnos","test","int","not null");
            this.cerrarConexion();
        }
        return System.currentTimeMillis()-time;
    }

}