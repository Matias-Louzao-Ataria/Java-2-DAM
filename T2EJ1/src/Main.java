import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {
    public static void main(String[] args) throws Exception {
        Dom d = new Dom();
        Document arbol = d.crearArbol();
        ArrayList<String> titulos = d.mostrarTitulos(arbol);
        for (String string : titulos) {
            System.out.println(string);
            System.out.println(d.mostrarAutores(arbol, string));
        }
    }
}

class Dom{
    private String ruta = "peliculas.xml";

    public Document crearArbol(){
        Document doc = null;
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(ruta);
        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        return doc;
    }

    public ArrayList<String> mostrarTitulos(Document doc){
        NodeList peliculas = doc.getFirstChild().getChildNodes(),datosActuales;
        Node peliculaActual;
        ArrayList<String> res = new ArrayList<String>();
        for(int i = 0;i < peliculas.getLength();i++){
            peliculaActual = peliculas.item(i);
            datosActuales = peliculaActual.getChildNodes();
            for(int j = 0;j < datosActuales.getLength();j++){
                if(datosActuales.item(j).getNodeType() == Node.ELEMENT_NODE && datosActuales.item(j).getNodeName().equals("titulo")){
                    res.add(datosActuales.item(j).getFirstChild().getNodeValue());
                }
            }
        }
        return res;
    }

    public String mostrarAutores(Document doc,String titulo) {
        NodeList algo = doc.getElementsByTagName("pelicula"),datosPelicula,datosDirector;
        boolean coincide = false;
        String res = "";
        for (int i = 0; i < algo.getLength();i++) {
            datosPelicula = algo.item(i).getChildNodes();
            for (int j = 0; j < datosPelicula.getLength(); j++) {
                if (!coincide) {
                    if (datosPelicula.item(j).getNodeType() == Node.ELEMENT_NODE && datosPelicula.item(j).getNodeName().equals("titulo")) {
                        if (datosPelicula.item(j).getFirstChild().getNodeValue().equals(titulo)) {
                            coincide = true;
                        }
                    }
                } else {
                    if (datosPelicula.item(j).getNodeType() == Node.ELEMENT_NODE && datosPelicula.item(j).getNodeName().equals("director")) {
                        datosDirector = datosPelicula.item(j).getChildNodes();
                        for (int h = 0; h < datosDirector.getLength(); h++) {
                            if (datosDirector.item(h).getNodeType() == Node.ELEMENT_NODE) {
                                res += datosDirector.item(h).getFirstChild().getNodeValue() + " ";
                            }
                        }
                    }
                }
            }
            coincide = false;//Aquí se cambia de película
        }
        return res; 
    }

}
