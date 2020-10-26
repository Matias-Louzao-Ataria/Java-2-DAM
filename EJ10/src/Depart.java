import java.io.Serializable;

public class Depart implements Serializable{
    private String nombre,empresa;
    private int id,numempleados;

    public Depart(){

    }

    public Depart(String nombre,String empresa,int id,int numempleados){
        this.setNombre(nombre);
        this.setEmpresa(empresa);
        this.setId(id);
        this.setNumempleados(numempleados);
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el valor de la variable nombre.
     * @param nombre Valor de nombre.
     * @throws IllegalArgumentException Si nombre es cadena vacia.
     */
    public void setNombre(String nombre) throws IllegalArgumentException{
        if(nombre == null || nombre.length() <= 0){
            throw new IllegalArgumentException();
        }else{
            this.nombre = nombre;
        }
    }

    public String getEmpresa() {
        return empresa;
    }

    /**
     * Establece el valor de la variable empresa.
     * @param empresa Valor de empresa.
     * @throws IllegalArgumentException Si empresa es cadena vacia.
     */
    public void setEmpresa(String empresa) throws IllegalArgumentException {
        if (empresa == null || empresa.length() <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.empresa = empresa;
        }
    }

    public int getId() {
        return id;
    }

    /**
     * Establece el valor de la propiedad id.
     * @param id Valor de id.
     * @throws NumberFormatException Si id es negativa.
     */
    public void setId(int id) throws NumberFormatException{
        if(id < 0){
            throw new NumberFormatException();
        }else{
            this.id = id;
        }
    }

    public int getNumempleados() {
        return numempleados;
    }

    /**
     * Establece el número de empleados.
     * @param numempleados Número de empleados.
     * @throws NumberFormatException Si el número de empleados es negativo.
     */
    public void setNumempleados(int numempleados) throws NumberFormatException{
        if (numempleados < 0) {
            throw new NumberFormatException();
        } else {
            this.numempleados = numempleados;
        }
    }

    public void verEmpleados(){
        System.out.println(this.nombre+" tiene "+this.numempleados+" empleados!");
    }
}
