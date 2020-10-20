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
        Operaciones o = new Operaciones();
        o.mostarDepartamentos();
        o.mostarPersonas();
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
            System.out.println("1.- Añadir persona.\n.2.- Añadir departamento.\n3.- Consultar personas.\n4.- Consultar departamentos.\n5.- Borrar persona.\n6.- Borrar departamento.\n7.- Salir.");
            switch (select) {
                case 1:
                    entradaDeDatos(new Persona(),sc);
                    break;

                case 2:
                    entradaDeDatos(new Depart(),sc);
                    break;
                
                case 3:
                    o.mostarPersonas();
                    break;
                
                case 4:
                    o.mostarDepartamentos();
                    break;
                
                case 5:
                    System.out.println("Introduzca el DNI de la persona a eliminar:");
                    dni = sc.nextLine();
                    o.borrarPersona(dni);
                    break;

                case 6:
                    System.out.println("Introduzca el id del departamento a eliminar:");
                    id = o.pedirEntero(sc);
                    o.borrarDepart(id);
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
            o.añadirDepartamento(nombre, apellidos, numempleados, edad);
        }else{
            System.out.println("Introduce el DNI de la persona:");
            s3 = sc.nextLine();
            o.añadirPersona(nombre, apellidos,s3,edad);
        }
        sc.close();
        return true;
    }
}

class Operaciones{
    private String home = System.getProperty("user.home"), sep = System.getProperty("file.separator");
    private File f = new File(home + sep + "guardado.dat");
    private ArrayList<Persona> personas = new ArrayList<Persona>();
    private ArrayList<Depart> departamentos = new ArrayList<Depart>();

    public Operaciones(){
        try{
            if(f.exists() && f.length() > 0){
                try{
                    FileInputStream in = new FileInputStream(f);
                    ObjectInputStream input = new ObjectInputStream(in);
                    int x = input.readInt();
                    //System.out.println(x);
                    for(int i = 0;i < x;i++){
                        departamentos.add((Depart) input.readObject());
                    }
                    int y = input.readInt();
                    //System.out.println(y);
                    for(int j = 0;j < y;j++){
                        personas.add((Persona) input.readObject());
                    }
                    input.close();
                    in.close();
                    f.delete();
                }catch(IOException | ClassNotFoundException | NumberFormatException e){
                    //System.err.println(e.getLocalizedMessage());
                }
            }else{ 
                f.createNewFile(); 
            } 
        }catch(IOException e){
         System.err.println(e.getLocalizedMessage());
        }
        
    }

    public boolean añadirPersona(String nombre, String apellidos,String dni ,int edad) {
        personas.add(new Persona(nombre, apellidos,dni,edad));
        return true;
    }

    public boolean añadirDepartamento(String nombre,String empresa,int numempleados,int id) {
        departamentos.add(new Depart(nombre,empresa,id,numempleados));
        return true;
    }

    public void mostarDepartamentos(){
        //System.out.println(departamentos.size());
        for (Depart depart : departamentos) {
            System.out.println(depart.getId());
        }
    }

    public void mostarPersonas(){
        //System.out.println(departamentos.size());
        for (Persona persona : personas) {
            System.out.println(persona.getEdad());
        }
    }

    public boolean borrarPersona(String dni){
        for (Persona persona : personas) {
            if(persona.getDni().equals(dni)){
                personas.remove(persona);
                return true;
            }
        }
        return false;
    }

    public boolean borrarDepart(int id){
        for (Depart depart : departamentos){
            if(depart.getId() == id){
                departamentos.remove(depart);
                return true;
            }
        }
        return false;
    }

    public boolean guardar() {
        try {
            FileOutputStream out = new FileOutputStream(f, true);
            ObjectOutputStream output = new ObjectOutputStream(out);
            output.writeInt(departamentos.size());
            for (Depart depart : departamentos) {
                output.writeObject(depart);
            }

            output.writeInt(personas.size());
            for (Persona persona : personas) {
                output.writeObject(persona);
            }

            output.close();
            out.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

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
