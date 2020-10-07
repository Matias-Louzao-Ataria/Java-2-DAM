import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

public class App {
    public static void main(String[] args) {
        String home = System.getProperty("user.home"), sep = System.getProperty("file.separator");
        Operador o = new Operador();
        File f = new File(home+sep+"a.txt");
        o.menu(f,"n");
    }
}

class Operador {
    String home = System.getProperty("user.home"), sep = System.getProperty("file.separator");

    public void menu(File f, String action) {
        switch (Character.toLowerCase(action.charAt(0))) {
            case 'n':
                contar(f);//Hecho
                break;

            case 'a':
                ordenarAscendente(f);
                break;

            case 'd':

                break;

            default:
                break;
        }
    }

    private void contar(File f) {
        try(FileReader reader = new FileReader(f);) {
            int x = 0,lineas = 0,palabras = 0;
            while((x = reader.read()) != -1){
                if((""+((char)x)).contains("\t") || (""+((char)x)).contains(" ")){
                    palabras++;
                }else if((""+((char)x)).contains("\n")){
                    lineas++;
                    palabras++;
                }
            }
            System.out.println("El archivo tiene: "+lineas+" lineas y "+palabras+" palabras.");
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        
    }
    
    private void ordenarAscendente(File f){
        
    }
}
