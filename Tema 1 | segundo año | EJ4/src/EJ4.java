import java.util.*;
import java.io.*;

public class EJ4 {
    public static void main(String[] args) {
        String home = System.getProperty("user.home"),sep = System.getProperty("file.separator");
        File f = new File(home+sep+"a.txt");
        Contador c = new Contador();
        System.out.println("El caracter más usado es: \""+c.contar(f)+"\"");
    }
}

class Contador{
    public char contar(File f){
        int mayor = -1,actual = 0;
        ArrayList<Character> caracteresComprobados = new ArrayList<Character>();
        ArrayList<Integer> vecesAparecidas = new ArrayList<Integer>();
        ArrayList<Character> caracteresDevolver = new ArrayList<Character>();
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                for (int i = 0; i < s.length(); i++) {
                    char caracter = s.charAt(i);
                    if (caracteresComprobados.contains(s.charAt(i))) {
                        vecesAparecidas.set(caracteresComprobados.indexOf(caracter),vecesAparecidas.get(caracteresComprobados.indexOf(caracter)) + 1);
                    } else {
                        caracteresComprobados.add(s.charAt(i));
                        vecesAparecidas.add(1);
                    }
                }
            }
            
            //Añadir la capacidad de detectar si hay varios caracteres que sean los más utilizados y devolverlos.

            for (int i = 0; i < vecesAparecidas.size(); i++) {
                actual = vecesAparecidas.get(i);
                if (actual > mayor) {
                    mayor = actual;
                }
            }

           return caracteresComprobados.get(vecesAparecidas.indexOf(mayor));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 'c';
    }
}
