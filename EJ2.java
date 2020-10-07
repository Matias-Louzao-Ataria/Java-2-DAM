import java.io.File;

public class Find{
    public static void main(String[] args) {
        String home = System.getProperty("user.home");
        String sep = System.getProperty("file.separator");
        File f = new File(home+sep+"Descargas");
        algo(f);
    }
    
    public static void algo(File f){
        try {
            File[] a = f.listFiles();
                for (int i = 0; i < a.length; i++) {
                    System.out.println(a[i].getAbsolutePath());
                    if(a[i].isDirectory()){
                        algo(a[i]);
                    }
                }
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            System.err.println(e.getCause());
        }
    }

}

