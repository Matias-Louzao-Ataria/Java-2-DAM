import java.io.*;

public class Main {
    public static void main(String[] args){
        String home = System.getProperty("user.home"),sep = System.getProperty("file.separator");
        File f = new File(home+sep+"a.txt");
        String cad = f.getAbsolutePath(),ext = cad.substring(cad.lastIndexOf('.'),cad.length());
        File f2 = new File(home+sep+"res"+ext);
        File f3 = new File(home+sep+"resbuffer"+ext);

        copiaSinBuffer(f, f2);
        copiaBuffer(f, f3,10);
        copiaBuffer(f, f3,100);
        copiaBuffer(f, f3,1000);

    }

    private static void copiaSinBuffer(File f, File f2) {
        try(FileInputStream in = new FileInputStream(f); DataInputStream input = new DataInputStream(in); FileOutputStream out = new FileOutputStream(f2); DataOutputStream output = new DataOutputStream(out)){
            int i;
            while((i = input.read()) != -1){
                output.write(i);
                System.out.println("Copiado: "+(f2.length()*100)/ f.length()+"%");
            }
        }catch(IOException e){
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static void copiaBuffer(File f, File f3,int sz) {
        try(FileInputStream in = new FileInputStream(f); DataInputStream input = new DataInputStream(in); FileOutputStream out = new FileOutputStream(f3); DataOutputStream output = new DataOutputStream(out)){
            byte[] buffer = new byte[sz];
            int i;
            while((i = input.read(buffer)) != -1){
                output.write(buffer,0,i);
                System.out.println("Copiado buffer "+sz+": "+(f3.length()*100)/ f.length()+"%");
            }
        } catch(IOException e){
            System.err.println(e.getLocalizedMessage());
        }
    }
}
