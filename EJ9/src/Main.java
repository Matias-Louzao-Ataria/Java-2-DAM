import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        /*Realiza un programa que permita almacenar, de forma binaria, los datos de unos alumnos en el ficheroalumnos.dat (guarda los datos de forma individual, no como un objeto).
        Los datos de cada alumno serán:nombre como cadena de texto, fecha de nacimiento y código como enteros.
        El programa debe realizarlas siguientes tareas:
            Dar de alta nuevos alumnos.
            Consultar alumnos.
            Modificar alumnos.
            Borrar alumnos.
        El  programa  deberá  avisar  de  posibles  problemas  encontrados  como  puede  ser  el  intentar  borrar  un alumno que no exista.*/
        String home = System.getProperty("user.home"),sep = System.getProperty("file.separator");
        File f = new File(home+sep+"alumnos.dat");
        int elect = 1;
        Operaciones o = new Operaciones();
        switch(elect){
        case 1:
            o.darDeAlta(f);
            break;

        case 2:

            break;

        case 3:

            break;

        case 4:

            break;

        default:

        }
    }
}

class Operaciones {
    public void darDeAlta(File f) {
        try {
            FileOutputStream out = new FileOutputStream(f,true);
            DataOutputStream output = new DataOutputStream(out);
            Scanner sc = new Scanner(System.in);
            String nombre,fecha;
            int id;

            System.out.println("Introduce el nombre del alumno:");
            nombre = sc.nextLine();
            System.out.println("Introduce la fecha de nacimiento del alumno: (dd-MM-yyyy)");
            fecha = sc.nextLine();
            System.out.println("Introduce la id del alumno:");
            id = this.pedirEntero(sc);
            if(comprobarId(f, id)){
                output.writeUTF(nombre);
                output.writeUTF(fecha);
                output.writeInt(id); 
                output.writeUTF("%n");
            }else{
                System.out.println("Alumno ya registrado o id repetida!");
            }
            output.writeUTF("%n");

            sc.close();
            output.close();
            out.close();
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public int pedirEntero(Scanner sc) {
        int res = 0;
        try {
            res = sc.nextInt();
            if (res < 0) {
                res = 0;
            }
        } catch (NumberFormatException e) {
            System.out.println("Introduce un número válido.");
        }
        return res;
    }

    public boolean comprobarId(File f,int id){
        int i = 0;
        try{
            FileInputStream in = new FileInputStream(f);
            DataInputStream input = new DataInputStream(in);


            try{
                while (true && i != id) {
                    input.readUTF();
                    i = input.readInt();
                }
            }catch(EOFException e){
                input.close();
                in.close();
            }
        }catch(IOException e){
            System.out.println(e.getLocalizedMessage());
        }
        if(i == id){
            return false;
        }else{
            return true;
        }
    }
}
