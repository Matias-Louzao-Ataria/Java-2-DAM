import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(args.length > 0){
            File f = new File(args[0]);
            Buscador b = new Buscador();
            b.buscar(f, args[1]);
        }else{
            System.out.println("Archivo donde se busca, cadena a buscar.");
        }
    }
}

class Buscador{
    public void buscar(File f,String str){
        int cont = 0;
        String actual = "";
        try(Scanner sc = new Scanner(f)){
            while(sc.hasNextLine()){
                actual = sc.nextLine();
                cont++;
                if(actual.contains(str)){
                    System.out.println(cont+".- "+actual+".");
                }
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}