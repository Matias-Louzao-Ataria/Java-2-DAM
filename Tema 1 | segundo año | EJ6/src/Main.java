import java.io.*;

public class Main{
    public static void main(String[] args){
        String home = System.getProperty("user.home"), sep = System.getProperty("file.separator");
        Tratador t = new Tratador();
        File f = new File(home+sep+"a.txt");
        t.dividir(f,5,20);
        //File[] fs = { new File(home + sep + "generado.txt"), new File(home + sep + "guardado.txt"),new File(home + sep + "Enlaces.txt") };
        //File[] fs = {new File(home+sep+"a.txt"),new File(home+sep+"b.txt"),new File(home+sep+"c.txt")};
        //t.unir(fs);
    }
}

class Tratador{
    
    private String home = System.getProperty("user.home"), sep = System.getProperty("file.separator");
    
    public void dividir(File f,int lineas,int caracteres){//FIXME Genera un fichero de más en blanco siempre a menos que tenga el último if y  escribe el -1.
        String mod = "1";
        try(FileReader reader = new FileReader(f)){
            int x = 0;
            FileWriter writer = new FileWriter(new File(home+sep+"Fichero"+mod+".txt"),true);
            while(x != -1){
                for(int i = 0;i < lineas;i++){
                    for(int j = 0;j < caracteres;j++){
                        x = reader.read();
                        if((char)x == '\n' || x == -1){//

                        }else{
                            writer.append((char) x);
                        }
                    }
                    writer.append("\n");
                }
                mod = Integer.toString((Integer.parseInt(mod)+1));
                writer.close();
                if(x != -1){//
                    File f2 = new File(home+sep+"Fichero"+mod+".txt");
                    writer = new FileWriter(f2,true);
                }
                
            }
            writer.close();
        }catch(IOException e){
            System.err.println(e.getLocalizedMessage());
        }
    }

    public void unir(File[] fs){
        for(int i = 0;i < fs.length;i++){
            try(FileReader reader = new FileReader(fs[i])){
                FileWriter writer = new FileWriter(home+sep+"unido.txt",true);
                int j = 0;
                while((j = reader.read()) != -1){
                    writer.append((char)j);
                }
                writer.close();
            }catch(IOException e){
                System.err.println(e.getLocalizedMessage());
            }
        }
        // File f = new File(home+sep+"unido.txt");
        
        // for(int i = 0;i < fs.length;i++){
        //     try(Scanner sc = new Scanner(fs[i])){
        //         while(sc.hasNextLine()){
        //             try(PrintWriter esc = new PrintWriter(new FileWriter(f,true))){
        //                 esc.append(sc.nextLine());
        //                 esc.append("\n");
        //             }catch(Exception e){
        //                 System.out.println("Error de acceso al fichero!");
        //             }
        //         }
        //     }catch(Exception e){
        //         System.out.println("Error de acceso al fichero!");
        //     }
        //}
    }
}