import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.Element;

public class Main {
    public static void main(String[] args) throws Exception {
        Dom d = new Dom();
        Document arbol = d.crearArbol();
        int autores = 1;
        String peliculas = "";
        ArrayList<String> generos = d.enumeraGeneros(arbol);
        ArrayList<String> titulos = d.mostrarTitulos(arbol);

        for (String string : titulos) {
            System.out.println(string);
            System.out.println(d.mostrarAutores(arbol, string));
            System.out.println();
        }
        peliculas = d.peliculasDirectores(d.crearArbol(), autores);
        System.out.println("Películas con "+autores+" director(es):\n"+peliculas);

        System.out.printf("Existen: %d generos y son:\n",generos.size());
        for (String genero : generos) {
            System.out.println(genero);
        }
        
        d.añadirAtributo(arbol, "Dune", "prueba", "prueba");
        
        d.grabaDOM(arbol, new FileOutputStream(new File("a.xml")));

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
        NodeList peliculas = doc.getElementsByTagName("pelicula"),datosPelicula,datosDirector;
        boolean coincide = false;
        String res = "";
        for (int i = 0; i < peliculas.getLength();i++) {
            datosPelicula = peliculas.item(i).getChildNodes();
            for (int j = 0; j < datosPelicula.getLength(); j++) {
                if (!coincide) {
                    if (datosPelicula.item(j).getNodeType() == Node.ELEMENT_NODE && datosPelicula.item(j).getNodeName().equals("titulo")) {
                        if (datosPelicula.item(j).getFirstChild().getNodeValue().equals(titulo)) {
                            coincide = true;
                            res += ((Element)peliculas.item(i)).getAttribute("genero")+"\n";
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
            coincide = false;
        }
        return res; 
    }

    public String peliculasDirectores(Document doc,int numDirectores){
        Node filmoteca = doc.getFirstChild();
        NodeList peliculas = filmoteca.getChildNodes();
        int cont = 0;
        String res = "",titulo = "";

        for (int i = 0; i < peliculas.getLength(); i++) {
            Node pelicula = peliculas.item(i);
            if(pelicula.getNodeType() == Node.ELEMENT_NODE){
                NodeList datosPelicula = pelicula.getChildNodes();
                for(int j = 0;j < datosPelicula.getLength();j++){
                    Node dato = datosPelicula.item(j);
                    if(dato.getNodeType() == Node.ELEMENT_NODE && dato.getNodeName() == "director"){
                        cont++;
                    }
                    if(dato.getNodeType() == Node.ELEMENT_NODE && dato.getNodeName() == "titulo"){
                        titulo = dato.getFirstChild().getNodeValue();
                    }
                }
            }
            if(cont >= numDirectores){
                res += titulo+"\n";
            }
            titulo = "";
            cont = 0;   
        }
        return res;
    }

    public ArrayList<String> enumeraGeneros(Document doc){
        ArrayList<String> generos = new ArrayList<String>();
        String genero = "";
        Node filmoteca = doc.getFirstChild();
        NodeList peliculas = filmoteca.getChildNodes();

        for(int i = 0;i < peliculas.getLength();i++){
            Node pelicula = peliculas.item(i);
            if(pelicula.getNodeType() == Node.ELEMENT_NODE){
                genero = ((Element)pelicula).getAttribute("genero");
                if(!generos.contains(genero)){
                    generos.add(genero);
                }
            }
        }
        return generos;
    }

    public void añadirAtributo(Document doc,String titulo,String nombre,String valor){
        Node filmoteca = doc.getFirstChild();
        NodeList peliculas = filmoteca.getChildNodes();

        for(int i = 0;i < peliculas.getLength();i++){
            Node pelicula = peliculas.item(i);
            if(pelicula.getNodeType() == Node.ELEMENT_NODE){
                NodeList datosPelicula = pelicula.getChildNodes();
                for(int j = 0;j < datosPelicula.getLength();j++){
                    Node dato = datosPelicula.item(j);
                    if(dato.getNodeType() == Node.ELEMENT_NODE && dato.getNodeName().equals("titulo")){
                        if(dato.getFirstChild().getNodeValue().equals(titulo)){
                            if (((Element) pelicula).hasAttribute(nombre)) {

                            } else {
                                ((Element) pelicula).setAttribute(nombre, valor);
                            }
                        }
                    }
                }
            }
        }
    }

    public void grabaDOM(Document document, FileOutputStream ficheroSalida) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementationLS ls = (DOMImplementationLS) registry.getDOMImplementation("XML 3.0 LS 3.0");

        // Creamos un destino vacio
        LSOutput output = ls.createLSOutput();
        output.setEncoding("UTF-8");

        // Establecemos el fujo de salida
        output.setByteStream(ficheroSalida);
        // output.setByteStream(System.out);

        // Permite estribir un documento DOM en XML
        LSSerializer serializer = ls.createLSSerializer();

        // Establecemos propiedades del serializador
        serializer.setNewLine("\r\n");
        ;
        serializer.getDomConfig().setParameter("format-pretty-print", true);

        // Escribimos el documento ya sea en un fichero o en una cadena
        serializer.write(document, output);
        // String xmlCad=serializer.writeToString(document);
    }

}
