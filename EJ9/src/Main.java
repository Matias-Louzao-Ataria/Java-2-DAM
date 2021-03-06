import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        /*
         String home = System.getProperty("user.home"), sep =
         System.getProperty("file.separator"); File f = new File(home + sep +
         "alumnos.dat");
         
         if(!f.exists()){ f.createNewFile(); }
         
         FileOutputStream out = new FileOutputStream(f,true); DataOutputStream output
         = new DataOutputStream(out); output.writeUTF("|"); output.writeInt(2);
         output.writeInt(3); output.writeDouble(4.2); output.close(); out.close();
         FileInputStream in = new FileInputStream(f); DataInputStream input = new
         DataInputStream(in); System.out.println(input.readUTF());
         System.out.println(input.readInt()); System.out.println(input.readInt());
         System.out.println(input.readDouble()); input.close(); in.close();
         */

        /*Scanner sc = new Scanner(System.in);
        Operaciones o = new Operaciones(sc);
        o.menu();
        sc.close();*/
        Operaciones o = new Operaciones();
        for(int i = 1;i <= 5;i++){
            if(o.comprobarId(i)){
                o.darDeAlta(i,"a"+i,i+2);
            }
        }
        System.out.println(o.consultarAlumno(1).getNombre());
        o.modificarAlumno(1, "g", 13111111);
        System.out.println(o.consultarAlumno(2).getNombre());
        //o.guardar();
        System.out.println(o.consultarAlumno(1).getNombre());
        o.borrarAlumno(2);
        System.out.println(o.consultarAlumno(3).getNombre());
    }
}

class Alumno {

    private String nombre;
    private int id;
    private int fecha;

    public Alumno() {
        this(0, "a", 0);
    }

    public Alumno(int fecha, String nombre, int id) {
        this.setNombre(nombre);
        this.setFecha(fecha);
        this.setId(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null || nombre.length() <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.nombre = nombre;
        }
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) throws IllegalArgumentException{
        if(fecha < 0){
            throw new IllegalArgumentException();
        }else{
            this.fecha = fecha;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws IllegalArgumentException{
        if(id < 0){
            throw new IllegalArgumentException();
        }else{
            this.id = id;
        }
    }

}

class Operaciones {
    //private ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
    private String home = System.getProperty("user.home"), sep = System.getProperty("file.separator");
    private File f = new File(home + sep + "alumnos.dat.temp");
    private File fverdeadero = new File(home + sep + "alumnos.dat");
    //private Scanner sc;

    public Operaciones(){
        //copiarConBuffered();
        if (f.exists()) {
            if (fverdeadero.exists()) {
                
            } else {
                try {
                    fverdeadero.createNewFile();
                } catch (IOException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        } else {
            try {
                f.createNewFile();
                copiarConBuffered();
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    public void copiarConBuffered(){
        if(f.exists()){
            if(fverdeadero.exists()){
                try (FileInputStream in = new FileInputStream(fverdeadero);
                        FileOutputStream out = new FileOutputStream(f);
                        BufferedInputStream input = new BufferedInputStream(in);
                        BufferedOutputStream output = new BufferedOutputStream(out)) {
                    int i;
                    while ((i = input.read()) != -1) {
                        output.write(i);
                    }
                } catch (IOException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }else{
                try {
                    fverdeadero.createNewFile();
                } catch (IOException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }else{
            try {
                f.createNewFile();
                copiarConBuffered();
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    /*public Operaciones(Scanner sc) {
        this.sc = sc;
        try(FileInputStream in = new FileInputStream(f);DataInputStream input = new DataInputStream(in);){
            if (f.exists() && f.length() > 0) {
                try {
                    while (true) {
                        Alumno actual = new Alumno(input.readInt(), input.readUTF(), input.readInt());
                        alumnos.add(actual);
                    }
                } catch (NumberFormatException | IOException e) {
                    System.out.println(e.getLocalizedMessage());
                }
                f.delete();
            } else {
                f.createNewFile();
            }
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
            System.err.println("Archivo alumnos.dat no formateado correctamente.");
        }
    }*/

    /*public void menu(){
        int id = 0,elect = 0;
        while (elect != 5) {
            System.out.println("1.- Dar de alta.\n2.- Consultar alumno.\n3.- Modificar alumno.\n4.- Borar alumno.\n5.- Salir.\n");
            elect = pedirEntero();    
            switch (elect) {
                case 1:
                    entradaDeDatos();
                    break;

                case 2:
                    System.out.println("Introduce la id del alumno a consultar:");
                    id = pedirEntero();
                    consultarAlumno(id);
                    break;

                case 3:
                    System.out.println("Introduce la id del alumno a modificar:");
                    id = pedirEntero();
                    modificarAlumno(id);
                    break;

                case 4:
                    System.out.println("Introduce la id del alumno a eliminar:");
                    id = pedirEntero();
                    borrarAlumno(id);
                    break;

                case 5:
                    System.out.println("Chao!");
                    break;

                default:
                    System.out.println("Acción no reconocida!");
            }
        }
        guardar();
    }*/

    /*public void consultarAlumno(int id) {
        boolean echo = false;
        for (Alumno alumno : alumnos) {
            if (alumno.getId() == id) {
                String fecha = String.valueOf(alumno.getFecha());
                if (fecha.length() > 7) {
                    System.out.printf("Nombre: %s, Fecha de nacimiento: %s/%s/%s, ID: %d.\n", alumno.getNombre(),
                            fecha.substring(0, 2), fecha.substring(2, 4), fecha.substring(4, fecha.length()),
                            alumno.getId());

                } else {
                    System.out.printf("Nombre: %s, Fecha de nacimiento: %s/%s/%s, ID: %d.\n", alumno.getNombre(),
                            fecha.substring(0, 1), fecha.substring(1, 3), fecha.substring(3, fecha.length()),
                            alumno.getId());

                }
                echo = true;
            }
        }
        if (!echo) {
            System.out.println("No se encuentra el alumno!");
        }
    }*/

    public Alumno consultarAlumno(int id){
        try(FileInputStream in = new FileInputStream(fverdeadero);DataInputStream input = new DataInputStream(in)){
            int idActual;
            while(true){
                idActual = input.readInt();
                if(idActual == id){
                    return new Alumno(idActual,input.readUTF(),input.readInt());
                }else{
                    input.readUTF();
                    input.readInt();
                }
            }
        }catch(EOFException e){
            
        }
        catch(IOException e){
            System.err.println(e.getLocalizedMessage());
        }
        return null;
    }

    /*public void modificarAlumno(int id) {
        boolean mod = false;
        int pos = 0;
        for (Alumno alumno : alumnos) {
            if (alumno.getId() == id) {
                pos = alumnos.indexOf(alumno);
                mod = true;
            }
        }

        if (mod) {
            entradaDeDatos(pos,id);
        } else {
            System.out.println("No se encuentra el alumno!");
        }

    }*/

    public boolean modificarAlumno(int id,String nombre,int fecha){
        //Alumno actual = consultarAlumno(id);
        try(FileInputStream in = new FileInputStream(fverdeadero);DataInputStream input = new DataInputStream(in);FileOutputStream out = new FileOutputStream(f);DataOutputStream output = new DataOutputStream(out)){
            int idActual;
            while(true){
                idActual = input.readInt();
                if(idActual == id){
                    output.writeInt(idActual);
                    output.writeUTF(nombre);
                    output.writeInt(fecha);
                    input.readUTF();
                    input.readInt();
                }else{
                    output.writeInt(idActual);
                    output.writeUTF(input.readUTF());
                    output.writeInt(input.readInt());
                }
            }
        }catch(EOFException e){

        }
        catch(IOException e){
            System.err.println(e.getLocalizedMessage());
            return false;
        }
        guardar();
        return true;
    }

    /*public void borrarAlumno(int id) {
        boolean echo = false;
        for(int i = alumnos.size(); i > 0;i--){
            if(alumnos.get(i).getId() == id){
                alumnos.remove(alumnos.get(i));
                echo = true;
            }
        }

        if (!echo) {
            System.out.println("No se encuentra el alumno!");
        } else {
            System.out.println("Se ha eliminado correctamente el almuno!");
        }
    }*/
    
    public boolean borrarAlumno(int id){
        //Alumno actual = consultarAlumno(id);
        try(FileInputStream in = new FileInputStream(fverdeadero);DataInputStream input = new DataInputStream(in);FileOutputStream out = new FileOutputStream(f);DataOutputStream output = new DataOutputStream(out)){
            int idActual = 0;
            while(true){
                idActual = input.readInt();
                if(idActual == id){
                    input.readUTF();
                    input.readInt();
                }else{
                    output.writeInt(idActual);
                    output.writeUTF(input.readUTF());
                    output.writeInt(input.readInt());
                }
            }
        }catch(EOFException e){

        }
        catch(IOException e){
            System.err.println(e.getLocalizedMessage());
            return false;
        }
        guardar();
        return true;
    }


    /*public void darDeAlta(int fecha, String nombre, int id, boolean echo) {
        alumnos.add(new Alumno(fecha, nombre, id));
        if(echo){
            System.out.println("Alumno agregado correctamente!");
        }
    }*/

    public boolean darDeAlta(int id, String nombre, int fecha){
        try(FileOutputStream out = new FileOutputStream(f,true);DataOutputStream output = new DataOutputStream(out)){
            output.writeInt(id);
            output.writeUTF(nombre);
            output.writeInt(fecha);
        }catch(IOException e){
            System.err.println(e.getLocalizedMessage());
            return false;
        }
        guardar();
        return true;
    }

    /*public void guardar() {
        try(FileOutputStream out = new FileOutputStream(f,true);DataOutputStream output = new DataOutputStream(out);){ 
            for (Alumno alumno : alumnos) {
                output.writeInt(alumno.getFecha());
                output.writeUTF(alumno.getNombre());
                output.writeInt(alumno.getId());
            }
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        
    }*/

    public boolean guardar(){
        if(f.exists()){
            if(fverdeadero.exists()){
                try (FileInputStream in = new FileInputStream(f);
                        DataInputStream input = new DataInputStream(in);
                        FileOutputStream out = new FileOutputStream(fverdeadero);
                        DataOutputStream output = new DataOutputStream(out)) {
                    while (true) {
                        output.writeInt(input.readInt());
                        output.writeUTF(input.readUTF());
                        output.writeInt(input.readInt());
                    }
                } catch (EOFException e) {

                } catch (IOException e) {
                    System.err.println(e.getLocalizedMessage());
                    return false;
                }
            }else{
                try {
                    fverdeadero.createNewFile();
                } catch (IOException e) {
                    System.err.println(e.getLocalizedMessage());
                    return false;
                }
            }
        }else{
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
                return false;
            }
        }
        return true;
    }

    /*public void entradaDeDatos(int pos,int id){
        String nombre = "";
        int fecha = 0;
        while(fecha == 0){
            System.out.println("Introduce el nombre del alumno a agregar:");
            nombre = sc.nextLine();
            System.out.println("Introduce la fecha de nacimiento del alumno a agregar (ddmmyyyy):");
            fecha = pedirEntero();
            if(String.valueOf(fecha).length() > 8 || String.valueOf(fecha).length() < 6){
                System.out.println("Fecha incorrecta!");
                fecha = 0;
            }else{
                alumnos.set(pos, new Alumno(fecha, nombre, id));
            }
        }
    }*/

    /*public void entradaDeDatos(){
        String nombre = "";
        int id = 0;
        int fecha = 0;
        while(id == 0 || fecha == 0){
            System.out.println("Introduce el nombre del alumno a agregar:");
            nombre = sc.nextLine();
            System.out.println("Introduce la fecha de nacimiento del alumno a agregar (ddmmyyyy):");
            fecha = pedirEntero();
            if(String.valueOf(fecha).length() > 8 || String.valueOf(fecha).length() < 6){
                System.out.println("Fecha incorrecta!");
                fecha = 0;
            }else{
                System.out.println("Introduce la id del alumno a agregar:");
                id = pedirEntero();
                if(comprobarId(id)){
                    darDeAlta(fecha, nombre, id, true);
                }else{
                    System.out.println("ID repetida!");
                    id = 0;
                }
            }
        }
    }*/

    /*public boolean comprobarId(int id){
        for (Alumno alumno : alumnos) {
            if(id == alumno.getId()){
                return false;
            }
        }
        return true;
    }*/
    

    public boolean comprobarId(int id){
        try(FileInputStream in = new FileInputStream(fverdeadero);DataInputStream input = new DataInputStream(in);){
            int idActual = 0;
            while(true){
                idActual = input.readInt();
                if(idActual == id){
                    return false;
                }
                input.readUTF();
                input.readInt();
            }
        }catch(EOFException e){

        }catch(IOException e){
            System.err.println(e.getLocalizedMessage());
            return false;
        }
        return true;
    }
    /*public int pedirEntero() {
        int res = 0;
        try {
            res = sc.nextInt();
            sc.nextLine();
            if (res < 0) {
                res = 0;
            }
        } catch (NumberFormatException | InputMismatchException e) {
            System.out.println("Introduce un número válido.");
            res = 0;
        }
        return res;
    }*/

    public File getF() {
        return f;
    }

    public void setF(File f) {
        this.f = f;
    }

    public File getFverdeadero() {
        return fverdeadero;
    }

    public void setFverdeadero(File fverdeadero) {
        this.fverdeadero = fverdeadero;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // public void darDeAlta(File f,String nombre,int fecha,int id) {
    //     FileOutputStream out = null;
    //     DataOutputStream output = null;
    //     try {
    //         out = new FileOutputStream(f,true);
    //         output = new DataOutputStream(out);

    //         if(comprobarId(f, id)){
    //             output.writeUTF(nombre);
    //             output.writeInt(fecha);
    //             output.writeInt(id); 
    //             System.out.println("Alumno agregado correctamente!");
    //         }else{
    //             System.out.println("Id de alumno repetida!");
    //         }

    //     } catch (IOException e) {
    //         System.err.println(e.getLocalizedMessage());
    //     }finally{
    //         try {
    //             output.close();
    //             out.close();
    //         } catch (IOException e) {
    //             System.err.println(e.getLocalizedMessage());
    //         }
    //     }
    // }

    // public void entrada(File f){
    //     String nombre;
    //     int id,fecha;
    //     Scanner sc = new Scanner(System.in);
    //     System.out.println("Introduce el nombre del alumno:");
    //     nombre = sc.nextLine();
    //     System.out.println("Introduce la fecha de nacimiento del alumno: (dd-MM-yyyy)");
    //     fecha = this.pedirEntero(sc);
    //     System.out.println("Introduce la id del alumno:");
    //     id = this.pedirEntero(sc);
    //     sc.close();
    //     darDeAlta(f, nombre, fecha, id);
    // }

    // public int pedirEntero(Scanner sc) {
    //     int res = 0;
    //     try {
    //         res = sc.nextInt();
    //         if (res < 0) {
    //             res = 0;
    //         }
    //     } catch (NumberFormatException e) {
    //         System.out.println("Introduce un número válido.");
    //     }
    //     return res;
    // }

    // public boolean comprobarId(File f,int id){
    //     int idActual = 0;
    //     FileInputStream in = null;
    //     DataInputStream input = null;
    //     try{
    //         in = new FileInputStream(f);
    //         input = new DataInputStream(in);

    //         if(input.available() <= 0){
    //             return true;
    //         }

    //         while(idActual == 0 && input.available() > 1){
    //             input.readUTF();
    //             input.readInt();
    //             idActual = input.readInt();
    //             System.out.println(idActual);
    //             if(id == idActual){
    //                 return false;
    //             }else{
    //                 idActual = 0;
    //             }
    //         }

    //     }catch(IOException e){
    //         System.err.println(e.getLocalizedMessage());
    //     }
    //     finally{
    //         try {
    //             input.close();
    //             in.close();
    //         } catch (IOException e) {
    //             System.err.println(e.getLocalizedMessage());
    //         }
    //     }
    //     return true;
    // }

    // public void mostrarAlumnos(File f){
    //     FileInputStream in = null;
    //     DataInputStream input = null;
    //     try{
    //         in = new FileInputStream(f);
    //         input = new DataInputStream(in);

    //         while(true){
    //             System.out.printf("Nombre: %s, fecha de nacimiento: %s, id %d\n",input.readUTF(),input.readUTF(),input.readInt());
    //         }

    //     }catch(IOException e){
    //         System.out.println("Final del archivo!");
    //     }finally{
    //         try {
    //             input.close();
    //             in.close();
    //         } catch (IOException e) {
    //             System.err.println(e.getLocalizedMessage());                
    //         }
    //     }
    // }
}
