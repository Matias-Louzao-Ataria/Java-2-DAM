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
        String home = System.getProperty("user.home"),sep = System.getProperty("file.separator");
        File f = new File(home+sep+"alumnos.dat");
         
        int elect = 2;
        Operaciones o = new Operaciones();
        switch(elect){
        case 1:
            o.entrada(f);
            break;

        case 2:
            o.mostrarAlumnos(f);
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
    public void darDeAlta(File f,String nombre,String fecha,int id) {
        FileOutputStream out = null;
        DataOutputStream output = null;
        try {
            out = new FileOutputStream(f,true);
            output = new DataOutputStream(out);

            if(comprobarId(f, id)){
                output.writeUTF(nombre);
                output.writeUTF(fecha);
                output.writeInt(id); 
                System.out.println("Alumno agregado correctamente!");
            }else{
                System.out.println("Id de alumno repetida!");
            }

        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }finally{
            try {
                output.close();
                out.close();
            } catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
    }

    public void entrada(File f){
        String nombre,fecha;
        int id;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el nombre del alumno:");
        nombre = sc.nextLine();
        System.out.println("Introduce la fecha de nacimiento del alumno: (dd-MM-yyyy)");
        fecha = sc.nextLine();
        System.out.println("Introduce la id del alumno:");
        id = this.pedirEntero(sc);
        sc.close();
        darDeAlta(f, nombre, fecha, id);
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
        int idActual = 0;
        try{
            FileInputStream in = new FileInputStream(f);
            DataInputStream input = new DataInputStream(in);

            if(input.available() <= 0){
                return true;
            }

            while(idActual == 0 && input.available() > 1){
                input.readUTF();
                input.readUTF();
                idActual = input.readInt();
                System.out.println(idActual);
                if(id == idActual){
                    return false;
                }else{
                    idActual = 0;
                }
            }
            
            input.close();
            in.close();
        }catch(IOException e){
            System.err.println(e.getLocalizedMessage());
        }
        return true;
    }

    public void mostrarAlumnos(File f){
        FileInputStream in = null;
        DataInputStream input = null;
        try{
            in = new FileInputStream(f);
            input = new DataInputStream(in);

            while(true){
                System.out.printf("Nombre: %s, fecha de nacimiento: %s, id %d\n",input.readUTF(),input.readUTF(),input.readInt());
            }

        }catch(IOException e){
            System.out.println("Final del archivo!");
        }finally{
            try {
                input.close();
                in.close();
            } catch (IOException e) {
                System.err.println(e.getLocalizedMessage());                
            }
        }
    }
}
