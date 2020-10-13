import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String home = System.getProperty("user.home"), sep = System.getProperty("file.separator");
        Operador o = new Operador();
        File f = new File(home+sep+"a.txt");
        o.menu(f,'D');
    }
}

class Operador {
    String home = System.getProperty("user.home"), sep = System.getProperty("file.separator");

    public void menu(File f, char action) {
        switch (action) {
            case 'n':
                contar(f);
                break;

            case 'A':
                escribir(ordenarAscendenteCase(f,true));
                break;

            case 'D':
                escribir(ordenarDescendenteCase(f));
                break;
            
            case 'a':
                escribir(ordenarAscendenteNoCase(f));
                break;

            case 'd':
                escribir(ordenarDescendenteNoCase(f));
                break;

            default:
                System.err.println("Opción no valida");
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
    
    private ArrayList<String> ordenarAscendenteCase(File f,boolean sense){
        ArrayList<String> lineas = new ArrayList<String>();
        try(Scanner sc = new Scanner(f)){
            while(sc.hasNextLine()){
                lineas.add(sc.nextLine());
            }
            if(sense){
                Collections.sort(lineas);
            }else{
                Collections.sort(lineas,String.CASE_INSENSITIVE_ORDER);
            }
            
        }catch(IOException e){
            System.err.println(e.getLocalizedMessage());
        }
        return lineas;
    }

    public ArrayList<String> ordenarDescendenteCase(File f){
        ArrayList<String> lineas = ordenarAscendenteCase(f,true);
        Collections.reverse(lineas);
        return lineas;
    }

    public ArrayList<String> ordenarAscendenteNoCase(File f) {
        ArrayList<String> lineas = ordenarAscendenteCase(f, false);
        return lineas;
    }
    
    public ArrayList<String> ordenarDescendenteNoCase(File f){
        ArrayList<String> lineas = ordenarAscendenteNoCase(f);
        Collections.reverse(lineas);
        return lineas;
    }

    public void escribir(ArrayList<String> lista){
        try(PrintWriter writer = new PrintWriter(new FileWriter(home + sep + "alfabetico.txt", true))){
            for (int i = 0; i < lista.size(); i++) {
                writer.append(lista.get(i));
                writer.append("\n");
            }
        }catch(IOException e){
            System.err.println(e.getLocalizedMessage());
        }
    }
}
