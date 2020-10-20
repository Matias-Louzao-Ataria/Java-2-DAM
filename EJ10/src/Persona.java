import java.io.Serializable;

public class Persona implements Serializable {//TODO implementar serializable
    private String nombre,apellidos,dni;
    private int edad;

    public Persona(){

    }

    public Persona(String nombre,String apellidos,String dni,int edad) {
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setEdad(edad);
        this.setDni(dni);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if(edad < 0){
            this.edad = 0;
        }else{
            this.edad = edad;
        }
    }

    public void caminar(){
        System.out.println(this.nombre+" está caminando!");
    }

    public void comer(){
        System.out.println(this.nombre+" está comiendo!");
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if(dni.length() <= 0){
            dni = "12345678A";
        }else{
            this.dni = dni;
        }
    }

    /*private void writeObject(ObjectOutputStream out) {

    }

    private void readObject(ObjectInputStream in) {

    }*/
}
