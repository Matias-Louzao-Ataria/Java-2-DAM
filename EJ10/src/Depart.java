import java.io.Serializable;

public class Depart implements Serializable{// TODO implementar serializable
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id < 0){
            id = 0;
        }else{
            this.id = id;
        }
    }

    public int getNumempleados() {
        return numempleados;
    }

    public void setNumempleados(int numempleados) {
        if (numempleados < 0) {
            numempleados = 0;
        } else {
            this.numempleados = numempleados;
        }
    }

    public void verEmpleados(){
        System.out.println(this.nombre+" tiene "+this.numempleados+" empleados!");
    }

    /*private void writeObject(ObjectOutputStream out){

    }

    private void readObject(ObjectInputStream in){

    }

    private void readObjectNoData() throws ObjectStreamException{

    }*/

}
