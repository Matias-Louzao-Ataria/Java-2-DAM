import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Persona implements Serializable {//TODO implementar serializable
    private String nombre,apellidos;
    private int edad;

    public Persona(){

    }

    public Persona(String nombre,String apellidos,int edad) {
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setEdad(edad);
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

    private void writeObject(ObjectOutputStream out) {

    }

    private void readObject(ObjectInputStream in) {

    }
}
