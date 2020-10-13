import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    /*Es mejor leer como un buffer, como se demuestra por el delta de tiempo calculado durante la ejecución del programa
    ya que al acceder al archivo se lee una cantidad determinada de caracteres al mismo tiempo y no solo uno.*/
    public static void main(String[] args) throws Exception {
        String home = System.getProperty("user.home"),sep = System.getProperty("file.separator");
        long t1 = System.currentTimeMillis(),t1f = 0,t2,t2f;
        File in = new File("cc.jpg"),out = new File(home+sep+"copia.jpg");
        interpreter interpretador = new interpreter();
        interpretador.copiaSinBuffer(in,out);
        t1f = System.currentTimeMillis();
        System.err.println("Tiempo de ejecución: "+(t1f-t1)+"ms");
        t2 = System.currentTimeMillis();
        interpretador.copiaConBuffer(in, new File(home+sep+"copia2.jpg"));
        t2f = System.currentTimeMillis();
        System.err.println("Tiempo de ejecución: " + (t2f - t2) + "ms");
    }
}

class interpreter{
    public void copiaSinBuffer(File in, File out){
        try(FileInputStream input = new FileInputStream(in)){
            FileOutputStream output = new FileOutputStream(out);
            int i;
            while((i = input.read()) != -1){
                output.write(i);
            }
            output.close();
        }catch(IOException e){
            System.err.println(e.getLocalizedMessage());
        }
    }

    public void copiaConBuffer(File in, File out){
        try(FileInputStream input = new FileInputStream(in)){
            FileOutputStream output = new FileOutputStream(out);
            int i;
            byte[] buffer = new byte[50];
            while((i = input.read(buffer))!= -1){
                output.write(buffer);
            }
            output.close();
        }catch(IOException e){
            System.err.println(e.getLocalizedMessage());
        }
    }
}