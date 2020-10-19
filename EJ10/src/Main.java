import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        Operaciones o = new Operaciones();
        
    }
}

class Menu{ 

    private Operaciones o = new Operaciones();

    public Menu(){
        
    }
    
    public void menu(){
        int select = 0;
        while(select != 7){
            System.out.println("1.- Añadir persona.\n.2.- Añadir departamento.\n3.- Consultar persona.\n4.- Consultar departamento.\n5.- Borrar persona.\n6.- Borrar departamento.\n7.- Salir.");
            switch (select) {
                case 1:
                    
                    break;

                case 2:

                    break;
                
                case 3:

                    break;
                
                case 4:
                    
                    break;
                
                case 5:
                    
                    break;

                case 6:

                    break;
                    
                case 7:
                    System.out.println("Chao!");
                    break;
            
                default:
                    break;
            }
        }

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
                    for(int i = 0;i < input.readInt();i++){
                        departamentos.add((Depart) input.readObject());
                    }
                    for(int j = 0;j < input.readInt();j++){
                        personas.add((Persona) input.readObject());
                    }
                    input.close();
                    in.close();
                }catch(IOException | ClassNotFoundException e){
                    System.err.println(e.getLocalizedMessage());
                }
            }else{ 
                f.createNewFile(); 
            } 
        }catch(IOException e){
         System.err.println(e.getLocalizedMessage());
        }
        
    }

    public boolean añadirPersona(String nombre, String apellidos, int edad) {
        personas.add(new Persona(nombre, apellidos, edad));
        return true;
    }

    public boolean añadirDepartamento(String nombre,String empresa,int numempleados,int id) {
        departamentos.add(new Depart(nombre,empresa,id,numempleados));
        return true;
    }

    public void mostarDepartamentos(){
        for (Depart depart : departamentos) {
            System.out.println(depart.getId());
        }
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
}
