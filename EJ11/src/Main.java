import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Operaciones o = new Operaciones();
        long t;
        int[] tamaños = {10,100,1000}; 
        
        for (int i = 0; i < tamaños.length; i++) {
            t = System.currentTimeMillis();
            o.copiarSinBuffered(tamaños[i],1+i);
            System.out.println((System.currentTimeMillis()-t)+"ms");
        }
        
        t = System.currentTimeMillis();
        o.copiarConBuffered(4);
        System.out.println((System.currentTimeMillis()-t)+"ms");
    }
}

class Operaciones{
    String home = System.getProperty("user.home"),sep = System.getProperty("file.separator");
    File f = new File(home+sep+"copia.txt");
    public Operaciones(){

    }

    public void copiarSinBuffered(int tamaño,int g){
        byte[] buffer = new byte[tamaño];
        try(FileInputStream in = new FileInputStream(f);FileOutputStream out = new FileOutputStream(new File(f.getAbsolutePath().replace(".txt", g+".txt")))){
            int i;
            while((i = in.read(buffer)) != -1){
                out.write(buffer,0,i);
            }
        }catch(IOException e){
            System.err.println(e.getLocalizedMessage());
        }
    }

    public void copiarConBuffered(int g){
        try(FileInputStream in = new FileInputStream(f);FileOutputStream out = new FileOutputStream(new File(f.getAbsolutePath().replace(".txt", g+".txt")));
        BufferedInputStream input = new BufferedInputStream(in);BufferedOutputStream output = new BufferedOutputStream(out)){
            int i;
            while((i = input.read()) != -1){
                output.write(i);
            }
        }catch(IOException e){
            System.out.println(e.getLocalizedMessage());
        }
    }
}
