import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.Element;

public class Main {
    public static void main(String[] args) throws Exception {
        Dom d = new Dom();
        Document arbol = d.crearArbol("peliculas.xml");
        /*int autores = 2;
        String peliculas = "";
        ArrayList<String> generos = d.enumeraGeneros(arbol);
        ArrayList<String> titulos = d.mostrarTitulos(arbol);

        for (String string : titulos) {
            System.out.println(string);
            System.out.println(d.mostrarDirectores(arbol, string));
            System.out.println(d.conseguirGenero(d.buscarPelicula(arbol, string)));
            System.out.println();
        }
        peliculas = d.peliculasDependiendoDeNDirectores(arbol, autores);
        System.out.println("Películas con " + autores + " director(es):\n" + peliculas);

        System.out.printf("Existen: %d generos y son:\n", generos.size());
        for (String genero : generos) {
            System.out.println(genero);
        }

        try {
            d.añadirAtributo(arbol, "Dune", "prueba", "prueba");
            d.eliminarAtributo(arbol, "Dune", "genero");
            d.añadirPelícula(arbol, "Depredador", "acción", "en", 1987, "Jhon Tiernan", "Algo Prueba");
            d.modificarNombreDirector(arbol, "Larry", "Wachowski", "Lana");
            Document a = d.añadirDirector("Dune", "Alfredo Landa");
            d.grabaDOM(arbol, new FileOutputStream(new File("a.xml")));
            d.borrarPelicula(arbol, "Dune");
            d.grabaDOM(a, new FileOutputStream(new File("b.xml")));
            d.grabaDOM(d.compañia(), new FileOutputStream(new File("compañia.xml")));
        } catch (IllegalArgumentException e) {
            System.err.println("Introduzca solo un valor!");
        }*/
        d.modificarNombreDirector(arbol, "Larry", "Wachowski", "Lanna");
        d.grabaDOM(arbol, new FileOutputStream(new File("a.xml")));
    }
}

class Dom {

    public Document crearArbol(String ruta) {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(ruta);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return doc;
    }

    public ArrayList<String> mostrarTitulos(Document doc) {
        NodeList peliculas = doc.getFirstChild().getChildNodes(), datosActuales;
        Node peliculaActual;
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < peliculas.getLength(); i++) {
            peliculaActual = peliculas.item(i);
            datosActuales = peliculaActual.getChildNodes();
            for (int j = 0; j < datosActuales.getLength(); j++) {
                if (datosActuales.item(j).getNodeName().equals("titulo")) {
                    res.add(datosActuales.item(j).getFirstChild().getNodeValue());
                }
            }
        }
        return res;
    }

    public String mostrarDirectores(Document doc, String titulo) {
        NodeList datosPelicula, datosDirector;
        String res = "";
        Node pelicula = buscarPelicula(doc, titulo);
        datosPelicula = pelicula.getChildNodes();
        for (int i = 0; i < datosPelicula.getLength(); i++) {
            Node dato = datosPelicula.item(i);
            if (dato.getNodeName().equals("director")) {
                datosDirector = dato.getChildNodes();
                for (int j = 0; j < datosDirector.getLength(); j++) {
                    Node datoDirector = datosDirector.item(j);
                    if (datoDirector.getNodeType() == Node.ELEMENT_NODE) {
                        res += datoDirector.getFirstChild().getNodeValue() + " ";
                    }
                }
            }
        }
        return res;
        /*
         * for (int i = 0; i < peliculas.getLength();i++) { datosPelicula =
         * peliculas.item(i).getChildNodes(); for (int j = 0; j <
         * datosPelicula.getLength(); j++) { if (!coincide) { if
         * (datosPelicula.item(j).getNodeType() == Node.ELEMENT_NODE &&
         * datosPelicula.item(j).getNodeName().equals("titulo")) { if
         * (datosPelicula.item(j).getFirstChild().getNodeValue().equals(titulo)) {
         * coincide = true; res +=
         * ((Element)peliculas.item(i)).getAttribute("genero")+"\n"; } } } else { if
         * (datosPelicula.item(j).getNodeType() == Node.ELEMENT_NODE &&
         * datosPelicula.item(j).getNodeName().equals("director")) { datosDirector =
         * datosPelicula.item(j).getChildNodes(); for (int h = 0; h <
         * datosDirector.getLength(); h++) { if (datosDirector.item(h).getNodeType() ==
         * Node.ELEMENT_NODE) { res +=
         * datosDirector.item(h).getFirstChild().getNodeValue() + " "; } } } } }
         * coincide = false; }
         */
    }

    public String peliculasDependiendoDeNDirectores(Document doc, int numDirectores) {
        Node filmoteca = doc.getFirstChild();
        NodeList peliculas = filmoteca.getChildNodes();
        int cont = 0;
        String res = "", titulo = "";

        for (int i = 0; i < peliculas.getLength(); i++) {
            Node pelicula = peliculas.item(i);
            if (pelicula.getNodeType() == Node.ELEMENT_NODE) {
                NodeList datosPelicula = pelicula.getChildNodes();
                for (int j = 0; j < datosPelicula.getLength(); j++) {
                    Node dato = datosPelicula.item(j);
                    if (dato.getNodeName().equals("director")) {
                        cont++;
                    }
                    if (dato.getNodeName().equals("titulo")) {
                        titulo = dato.getFirstChild().getNodeValue();
                    }
                }
            }
            if (cont >= numDirectores) {
                res += titulo + "\n";
            }
            titulo = "";
            cont = 0;
        }
        return res;
    }

    public ArrayList<String> enumeraGeneros(Document doc) {
        ArrayList<String> generos = new ArrayList<String>();
        String genero = "";
        Node filmoteca = doc.getFirstChild();
        NodeList peliculas = filmoteca.getChildNodes();

        for (int i = 0; i < peliculas.getLength(); i++) {
            Node pelicula = peliculas.item(i);
            if (pelicula.getNodeType() == Node.ELEMENT_NODE) {
                genero = conseguirGenero(pelicula);
                if (!generos.contains(genero)) {
                    generos.add(genero);
                }
            }
        }
        return generos;
    }

    public String conseguirGenero(Node pelicula) {
        return ((Element) pelicula).getAttribute("genero");
    }

    public void añadirAtributo(Document doc, String titulo, String attrib, String valor) {
        tratarAtributos(doc, titulo, attrib, false, valor);
    }

    public void eliminarAtributo(Document doc, String titulo, String attrib) {
        tratarAtributos(doc, titulo, attrib, true, null);
    }

    private void tratarAtributos(Document doc, String titulo, String attrib, boolean eliminar, String valor) {
        Node pelicula = buscarPelicula(doc, titulo);
        NodeList datosPelicula = pelicula.getChildNodes();
        for (int i = 0; i < datosPelicula.getLength(); i++) {
            Node dato = datosPelicula.item(i);
            if (dato.getNodeName().equals("titulo")) {
                if (dato.getFirstChild().getNodeValue().equals(titulo)) {
                    if (((Element) pelicula).hasAttribute(attrib)) {
                        if (eliminar) {
                            ((Element) pelicula).removeAttribute(attrib);
                        }
                    } else {
                        if (!eliminar) {
                            ((Element) pelicula).setAttribute(attrib, valor);
                        }
                    }
                }
            }
        }
        /*
         * for (int i = 0; i < peliculas.getLength(); i++) { Node pelicula =
         * peliculas.item(i); if (pelicula.getNodeType() == Node.ELEMENT_NODE) {
         * NodeList datosPelicula = pelicula.getChildNodes(); for (int j = 0; j <
         * datosPelicula.getLength(); j++) { Node dato = datosPelicula.item(j); if
         * (dato.getNodeType() == Node.ELEMENT_NODE &&
         * dato.getNodeName().equals("titulo")) { if
         * (dato.getFirstChild().getNodeValue().equals(titulo)) { if (((Element)
         * pelicula).hasAttribute(nombre)) { if(eliminar){ ((Element)
         * pelicula).removeAttribute(nombre); } } else { if(!eliminar){ ((Element)
         * pelicula).setAttribute(nombre, valor[0]); } } } } } } }
         */
    }

    public void añadirPelícula(Document doc, String titulo, String genero, String idioma, int añoSalida,
            String... directores) {
        Node filmoteca = doc.getFirstChild();
        Text text = doc.createTextNode("\n");

        Element pelicula = doc.createElement("pelicula");
        filmoteca.appendChild(pelicula);
        pelicula.setAttribute("año", String.valueOf(añoSalida));
        pelicula.setAttribute("genero", genero);
        pelicula.setAttribute("idioma", idioma);
        pelicula.appendChild(text);

        Element tituloNode = doc.createElement("titulo");
        Text tituloText = doc.createTextNode(titulo);
        pelicula.appendChild(tituloNode);
        tituloNode.appendChild(tituloText);
        tituloNode.appendChild(text);

        Element directorNode = doc.createElement("director");
        directorNode.appendChild(text);

        for (int i = 0; i < directores.length; i++) {
            Element nombreDirectorNode = doc.createElement("nombre");
            Text nombreDirectorText = doc.createTextNode(directores[i].substring(0, directores[i].indexOf("\s")));
            nombreDirectorNode.appendChild(nombreDirectorText);
            nombreDirectorNode.appendChild(text);

            Element apellidoDirectorNode = doc.createElement("apellido");
            Text apellidoDText = doc
                    .createTextNode(directores[i].substring(directores[i].indexOf("\s"), directores[i].length()));
            apellidoDirectorNode.appendChild(apellidoDText);
            apellidoDirectorNode.appendChild(text);

            directorNode.appendChild(nombreDirectorNode);
            directorNode.appendChild(apellidoDirectorNode);
        }

        pelicula.appendChild(directorNode);
    }

    public Node buscarPelicula(Document doc, String titulo) {
        Node filmoteca = doc.getFirstChild();
        NodeList peliculas = filmoteca.getChildNodes();
        for (int i = 0; i < peliculas.getLength(); i++) {
            Node pelicula = peliculas.item(i);
            NodeList datosPelicula = pelicula.getChildNodes();
            for (int j = 0; j < datosPelicula.getLength(); j++) {
                Node dato = datosPelicula.item(j);
                if (dato.getNodeName().equals("titulo")) {
                    if (dato.getFirstChild().getNodeValue().equals(titulo)) {
                        return pelicula;
                    }
                }
            }
        }
        return null;
    }

    public void modificarNombreDirector(Document doc, String nombreAntiguo, String apellido, String nombreNuevo) {
        NodeList directores = doc.getElementsByTagName("director");
        Node nodoNombre = null,nodoApellido = null;
        for(int i = 0;i < directores.getLength();i++){
            Node directorActual = directores.item(i);
            NodeList datosDirector = directorActual.getChildNodes();
            nodoNombre = null;
            nodoApellido = null;
            for(int j = 0;j < datosDirector.getLength();j++){
                if(datosDirector.item(j).getNodeName().equals("nombre")){
                    nodoNombre = datosDirector.item(j);
                }

                if(datosDirector.item(j).getNodeName().equals("apellido")){
                    nodoApellido = datosDirector.item(j);
                }

            }
            if(nodoNombre != null && nodoApellido != null && nodoNombre.getFirstChild().getNodeValue().equals(nombreAntiguo) && nodoApellido.getFirstChild().getNodeValue().equals(apellido)){
                nodoNombre.getFirstChild().setNodeValue(nombreNuevo);
            }
        }
        /*NodeList directores = doc.getElementsByTagName("director");
        for (int i = 0; i < directores.getLength(); i++) {
            NodeList datosDirector = directores.item(i).getChildNodes();
            for (int j = 1; j < datosDirector.getLength(); j += 3) {
                if (datosDirector.item(j).getNodeName().equals("nombre")) {
                    if (datosDirector.item(j).getFirstChild().getNodeValue().equals(nombreAntiguo)
                            && datosDirector.item(j + 2).getFirstChild().getNodeValue().equals(apellido)) {
                        datosDirector.item(j).getFirstChild().setNodeValue(nombreNuevo);
                    }
                }
            }
        }*/
        /*
         * Node pelicula = buscarPelicula(doc, titulo); System.out.println(pelicula ==
         * null); NodeList datosPelicula = pelicula.getChildNodes();
         * 
         * for(int i = 0;i < datosPelicula.getLength();i++){ Node dato =
         * datosPelicula.item(i); if(modificar.equals("nombre") ||
         * modificar.equals("apellido")){ NodeList datosDirector = dato.getChildNodes();
         * for(int j = 0;j < datosDirector.getLength();j++){ Node datoDirector =
         * datosDirector.item(j); if(datoDirector.getNodeType() == Node.ELEMENT_NODE &&
         * datoDirector.getNodeName().equals(modificar)){
         * if(datoDirector.getFirstChild().getNodeValue().equals(valorAntiguo)){
         * datoDirector.getFirstChild().setNodeValue(valor); } } } }else{ if
         * (dato.getNodeType() == Node.ELEMENT_NODE &&
         * dato.getNodeName().equals(modificar)) {
         * if(dato.getFirstChild().getNodeValue().equals(valorAntiguo)){
         * dato.getFirstChild().setNodeValue(valor); } } } }
         */
    }

    /*
     * public void eliminarAtributo(Document doc, String titulo, String nombre) {
     * Node filmoteca = doc.getFirstChild(); NodeList peliculas =
     * filmoteca.getChildNodes();
     * 
     * for (int i = 0; i < peliculas.getLength(); i++) { Node pelicula =
     * peliculas.item(i); if (pelicula.getNodeType() == Node.ELEMENT_NODE) {
     * NodeList datosPelicula = pelicula.getChildNodes(); for (int j = 0; j <
     * datosPelicula.getLength(); j++) { Node dato = datosPelicula.item(j); if
     * (dato.getNodeType() == Node.ELEMENT_NODE &&
     * dato.getNodeName().equals("titulo")) { if
     * (dato.getFirstChild().getNodeValue().equals(titulo)) { if (((Element)
     * pelicula).hasAttribute(nombre)) {
     * ((Element)pelicula).removeAttribute(nombre); } } } } } } }
     */

    public void borrarPelicula(Document doc, String titulo) {
        Node pelicula = buscarPelicula(doc, titulo);
        doc.getFirstChild().removeChild(pelicula);
    }

    public Document añadirDirector(String titulo, String director) {
        Document doc = crearArbol("peliculas.xml");
        Node pelicula = buscarPelicula(doc, titulo);
        Text text = doc.createTextNode("\n");
        Element directorNode = doc.createElement("director");
        directorNode.appendChild(text);

        Element nombreDirectorNode = doc.createElement("nombre");
        Text nombreDirectorText = doc.createTextNode(director.substring(0, director.indexOf("\s")));
        nombreDirectorNode.appendChild(nombreDirectorText);
        nombreDirectorNode.appendChild(text);

        Element apellidoDirectorNode = doc.createElement("apellido");
        Text apellidoDText = doc.createTextNode(director.substring(director.indexOf("\s"), director.length()));
        apellidoDirectorNode.appendChild(apellidoDText);
        apellidoDirectorNode.appendChild(text);

        directorNode.appendChild(nombreDirectorNode);
        directorNode.appendChild(apellidoDirectorNode);
        pelicula.appendChild(directorNode);

        return (Document) pelicula.getParentNode().getParentNode();
    }

    public Document compañia() throws ParserConfigurationException {
        File f = new File("compañia.xml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        doc.setXmlStandalone(false);
        
        Text text = doc.createTextNode("\n");

        Element compañia = doc.createElement("compañia");
        compañia.appendChild(text);
        Element empleado = doc.createElement("empleado");
        empleado.setAttribute("id", "1");
        compañia.appendChild(empleado);
        empleado.appendChild(text);

        Element nombre = doc.createElement("nombre");
        empleado.appendChild(nombre);
        Text nombreText = doc.createTextNode("Juan");
        nombre.appendChild(nombreText);
        nombre.appendChild(text);

        Element apellidos = doc.createElement("apellidos");
        empleado.appendChild(apellidos);
        Text apeText = doc.createTextNode("López Pérez");
        apellidos.appendChild(apeText);
        apellidos.appendChild(text);

        Element apodo = doc.createElement("apodo");
        empleado.appendChild(apodo);
        Text apodoText = doc.createTextNode("Juanín");
        apodo.appendChild(apodoText);
        apodo.appendChild(text);

        Element salario = doc.createElement("salario");
        empleado.appendChild(salario);
        Text salarioText = doc.createTextNode("1000");
        salario.appendChild(salarioText);
        salario.appendChild(text);

        doc.appendChild(compañia);

        return doc;
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
