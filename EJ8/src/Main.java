import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    /*Es mejor leer como un buffer, como se demuestra por el delta de tiempo calculado durante la ejecución del programa
    ya que al acceder al archivo se lee una cantidad determinada de caracteres al mismo tiempo y no solo uno.*/
    public static void main(String[] args) throws Exception {
        String home = System.getProperty("user.home"),sep = System.getProperty("file.separator");
        File in = new File("cc.jpg"),out = new File(home+sep+"copia.jpg");
        interpreter interpretador = new interpreter();
        int[] tamaños = {100,1000,10000};
        long t1 = System.currentTimeMillis();
        interpretador.copiaSinBuffer(in,out);
        System.err.println("Tiempo de ejecución: "+(System.currentTimeMillis()-t1)+"ms");
        for (int i = 0; i < tamaños.length; i++) {
            t1 = System.currentTimeMillis();
            interpretador.copiaConBuffer(in, new File(home+sep+"copia"+(i+2)+".jpg"),tamaños[i]);
            System.err.println("Tiempo de ejecución: "+(System.currentTimeMillis()-t1)+"ms");
        }
    }
}

class interpreter{
    public void copiaSinBuffer(File in, File out){
        try(FileInputStream input = new FileInputStream(in);FileOutputStream output = new FileOutputStream(out)){
            int i;
            while((i = input.read()) != -1){
                output.write(i);
            }
        }catch(IOException e){            
            System.err.println(e.getLocalizedMessage());
        }
    }

    public void copiaConBuffer(File in, File out,int tamaño){
        try(FileInputStream input = new FileInputStream(in);FileOutputStream output = new FileOutputStream(out)){
            int i;
            byte[] buffer = new byte[tamaño];
            while((i = input.read(buffer))!= -1){
                output.write(buffer,0,i);
            }
        }catch(IOException e){
            System.err.println(e.getLocalizedMessage());
        }
    }
}