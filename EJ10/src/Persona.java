import java.io.Serializable;

public class Persona implements Serializable {
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

    /**
     * Establece el valor de la variable nombre.
     * @param nombre Valor de nombre.
     * @throws IllegalArgumentException Si nombre es cadena vacia.
     */
    public void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null || nombre.length() <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.nombre = nombre;
        }
    }

    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece el valor de la variable apellidos.
     * @param apellidos Valor de apellidos.
     * @throws IllegalArgumentException Si apellidos es cadena vacia.
     */
    public void setApellidos(String apellidos) throws IllegalArgumentException {
        if (apellidos == null || apellidos.length() <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.apellidos = apellidos;
        }
    }

    public int getEdad() {
        return edad;
    }

    /**
     * Establece el valor de edad.
     * @param edad  Valor de edad.
     * @throws NumberFormatException Si edad es menos a 0.
     */
    public void setEdad(int edad) throws NumberFormatException{
        if(edad < 0){
            throw new NumberFormatException();
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

    /**
     * Establece el valor de DNI.
     * @param dni   Valor de DNI.
     * @throws IllegalArgumentException Si dni tiene menos de 9 caracteres.
     */
    public void setDni(String dni) throws IllegalArgumentException{
        if(dni.length() <= 9){
            throw new IllegalArgumentException();
        }else{
            this.dni = dni;
        }
    }
}
