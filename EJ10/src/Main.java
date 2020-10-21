import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Menu m = new Menu();
        m.menu();
    }
}

class Menu{ 

    private Operaciones o = new Operaciones();

    public Menu(){
        
    }
    
    public void menu(){
        int select = 0,id = 0;
        String dni = "";
        Scanner sc = new Scanner(System.in);
        while(select != 7){
            System.out.println("1.- Añadir persona.\n2.- Añadir departamento.\n3.- Consultar personas.\n4.- Consultar departamentos.\n5.- Borrar persona.\n6.- Borrar departamento.\n7.- Salir.");
            select = o.pedirEntero(sc);
            switch (select) {
                case 1:
                    entradaDeDatos(new Persona(),sc);
                    break;

                case 2:
                    entradaDeDatos(new Depart(),sc);
                    break;
                
                case 3:
                    System.out.println("Introduzca el DNI de la persona a mostrar:");
                    dni = sc.nextLine();
                    o.borrarPersona(dni,true);
                    break;
                
                case 4:
                    System.out.println("Introduzca el id del departamento a mostrar:");
                    id = o.pedirEntero(sc);
                    o.borrarDepart(id,true);
                    break;
                
                case 5:
                    System.out.println("Introduzca el DNI de la persona a eliminar:");
                    dni = sc.nextLine();
                    o.borrarPersona(dni,false);
                    break;

                case 6:
                    System.out.println("Introduzca el id del departamento a eliminar:");
                    id = o.pedirEntero(sc);
                    o.borrarDepart(id,false);
                    break;
                    
                case 7:
                    System.out.println("Chao!");
                    break;
            
                default:
                    break;
            }
        }
        sc.close();
        o.guardar();
    }

    public boolean entradaDeDatos(Object obj,Scanner sc){
        String objetivo = "",s2 = "",s3 = "";
        String nombre = "",apellidos = "";
        int edad = 0,numempleados = 0;
        if(obj.getClass().equals(Persona.class)){
            objetivo = "la persona";
            s2 = "los apellidos";
            s3 = "la edad";
        }else if(obj.getClass().equals(Depart.class)){
            objetivo = "el departamento";
            s2 = "la empersa";
            s3 = "el id";
        }else{  
            return false;
        }

        System.out.println("Introduce el nombre de "+objetivo+":");
        nombre = sc.nextLine();
        System.out.println("Introduce "+s2+" de "+objetivo+":");
        apellidos = sc.nextLine();
        System.out.println("Intruduce "+s3+" de "+objetivo+":");
        edad = o.pedirEntero(sc);
        if(obj.getClass().equals(Depart.class)){
            System.out.println("Introduce el numero de empleados del departamento:");
            numempleados = o.pedirEntero(sc);
            if(o.comprobarID(edad)){
                o.añadirDepartamento(nombre, apellidos, numempleados, edad);
            }else{
                System.out.println("ID repetida!");
            }
        }else{
            System.out.println("Introduce el DNI de la persona:");
            s3 = sc.nextLine();
            if(o.comprobarDNI(s3)){
                o.añadirPersona(nombre, apellidos, s3, edad);
            }else{
                System.out.println("DNI repetido!");
            }
        }
        return true;
    }

}

class Operaciones{
    private String home = System.getProperty("user.home"), sep = System.getProperty("file.separator");
    private File f = new File(home + sep + "guardado.dat");
    private ArrayList<Persona> personas = new ArrayList<Persona>();
    private ArrayList<Depart> departamentos = new ArrayList<Depart>();
    
    /**
     *Se comprueba si existe archivo del que leer, si no existe se crea para poder escribirlo y si existe se lee. 
     */
    public Operaciones(){
        try{
            if(f.exists() && f.length() > 0){
                try(FileInputStream in = new FileInputStream(f);ObjectInputStream input = new ObjectInputStream(in);){
                    int x = input.readInt();
                    for(int i = 0;i < x;i++){
                        Depart d = (Depart)input.readObject();
                        if(comprobarID(d.getId())){
                            departamentos.add(d);
                        }
                    }
                    int y = input.readInt();
                    for(int j = 0;j < y;j++){
                        Persona p = (Persona)input.readObject();
                        if(comprobarDNI(p.getDni())){
                            personas.add(p);
                        }
                    }
                    f.delete();
                }catch(IOException | ClassNotFoundException | NumberFormatException e){
                    //System.err.println(e.getLocalizedMessage());
                    System.out.println();
                }
            }else{ 
                f.createNewFile(); 
            } 
        }catch(IOException e){
         System.err.println(e.getLocalizedMessage());
        }
        
    }

    /**
     * Comprueba que no existan DNI's repetidos.
     * @param dni DNI a comprobar.
     * @return Devuelve false si el DNI está duplicado y true si no.
     */
    public boolean comprobarDNI(String dni) {
        for (Persona persona : personas) {
            if (persona.getDni().equals(dni)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba que no existan ID's repetidos.
     * @param id ID a comprobar.
     * @return Devuelve false si el ID está duplicado y true si no.
     */
    public boolean comprobarID(int id) {
        for (Depart depart : departamentos) {
            if(depart.getId() == id){
                return false;
            }
        }
        return true;
    }

    public boolean añadirPersona(String nombre, String apellidos,String dni ,int edad) {
        personas.add(new Persona(nombre, apellidos,dni,edad));
        return true;
    }

    public boolean añadirDepartamento(String nombre,String empresa,int numempleados,int id) {
        departamentos.add(new Depart(nombre,empresa,id,numempleados));
        return true;
    }

    /**
     * Borra personas o muestra personas.
     * @param dni DNI de la persona con la cual se desea trabajar.
     * @param echo Si es true se muestra por pantalla si no se borra.
     * @return devuelve true si se ha borrado de forma exitosa.
     */
    public boolean borrarPersona(String dni,boolean echo){
        for (Persona persona : personas) {
            if(persona.getDni().equals(dni)){
                if(echo){
                    System.out.println("Nombre:"+persona.getNombre()+" Apellidos:"+persona.getApellidos()+" DNI:"+persona.getDni()+" Edad:"+ persona.getEdad() + " años.");
                }else{
                    personas.remove(persona);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Borra o muestra departamentos.
     * @param id ID del departamento con el cual se desea trabajar.
     * @param echo Si es true se muestra por pantalla si no se borra.
     * @return devuelve true si se ha borrado de forma exitosa.
     */
    public boolean borrarDepart(int id,boolean echo){
        for (Depart depart : departamentos){
            if(depart.getId() == id){
                if (echo) {
                    System.out.println("Nombre:"+depart.getNombre() + " Empresa:" + depart.getEmpresa() + " ID:"+depart.getId()+" Numero de empleados:"+depart.getNumempleados() + " empleados.");
                } else {
                    departamentos.remove(depart);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Guarda en un archivo los objetos.
     */
    public boolean guardar() {
        try(FileOutputStream out = new FileOutputStream(f, true);ObjectOutputStream output = new ObjectOutputStream(out);){
            output.writeInt(departamentos.size());
            for (Depart depart : departamentos) {
                output.writeObject(depart);
            }

            output.writeInt(personas.size());
            for (Persona persona : personas) {
                output.writeObject(persona);
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Proporciona una forma segura de pedir un entero sin preocuparse por las excepciones.
     * @param sc Scanner con el cual se leen los datos del teclado.
     * @return Devuelve 0 se no se ha introducido un dato valido, en caso contrario devuelve el valor.
     */
    public int pedirEntero(Scanner sc) {
        int res = 0;
        try {
            res = sc.nextInt();
            sc.nextLine();
            if (res < 0) {
                res = 0;
            }
        } catch (NumberFormatException e) {
            System.out.println("Introduce un número válido.");
            res = 0;
            sc.reset();
        }
        return res;
    }
}
