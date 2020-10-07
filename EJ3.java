import java.io.*;
import java.rmi.AccessException;
import java.util.Scanner;

public class EJ3{
    public static void main(String[] args) {
        Contador e = new Contador();
        String home = System.getProperty("user.home");
        String sep = System.getProperty("file.separator");
        File f = new File(home+sep+"EJ3.java");
        System.out.println(e.contar(f, 'c'));
    }
}

class Contador{
    public int contar(File f,char c){
        if(f.exists() && f.length() != 0){
            int cont = 0;
            try (Scanner sc = new Scanner(f)) {
                while(sc.hasNext()){
                    String a = sc.nextLine();
                    for(int i = 0;i < a.length();i++){
                        if(a.charAt(i) == c){
                            cont++;
                        }
                    }
                }
                return cont;
            } catch (AccessException e) {
                return -1;
            }
        }else{
            return -1;
        }
    }
}