import java.io.*;

public class Main{
    public static void main(String[] args){
        String home = System.getProperty("user.home"), sep = System.getProperty("file.separator");
        Tratador t = new Tratador();
        File f = new File(home+sep+"a.txt");
        //t.dividirCaracteres(f,4);
        t.dividirLineas(f, 4);
        //File[] fs = { new File(home + sep + "generado.txt"), new File(home + sep + "guardado.txt"),new File(home + sep + "Enlaces.txt") };
        //File[] fs = {new File(home+sep+"a.txt"),new File(home+sep+"b.txt"),new File(home+sep+"c.txt")};
        //t.unir(fs);
    }
}

class Tratador{
    private String home = System.getProperty("user.home"), sep = System.getProperty("file.separator");
    
    public void dividirCaracteres(File f,int caracteres){//Este es más fácil con un Buffer.
        int mod = 1;
        try(FileReader reader = new FileReader(f)){
            int x = 0;
            FileWriter writer = new FileWriter(home+sep+"Fichero"+mod+".txt",true);
            while(x != -1){
                for(int i = 0;i < caracteres && x != -1;i++){
                    x = reader.read();
                    if(x != -1){
                        writer.append((char)x);
                    }
                }
                writer.close();
                if(x != -1){
                    mod++;
                    writer = new FileWriter(home+sep+"Fichero"+mod+".txt",true);
                }
            }
        }catch(IOException ex){
            System.err.println(ex.getLocalizedMessage());
        }
    }

    public void dividirLineas(File f,int lineas){//Este es más fácil con un Scanner.
        int mod = 1;
        try(FileReader reader = new FileReader(f)){
            int x = 0;
            FileWriter writer = new FileWriter(home+sep+"Fichero"+mod+".txt",true);
            while(x != -1){
                for(int i = 0;i < lineas;){
                    x = reader.read();
                    if(x == '\n'){
                        i++;
                        writer.append((char)x);
                    }else if(x == -1){
                        i++;
                    }else{
                        writer.append((char)x);
                    }
                }
                if(x != -1){
                    mod++;
                    writer.close();
                    writer = new FileWriter(home+sep+"Fichero"+mod+".txt",true);
                }
            }
            writer.close();
        }catch(IOException ex){
            System.err.println(ex.getLocalizedMessage());
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