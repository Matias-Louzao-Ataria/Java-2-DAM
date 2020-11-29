import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonReader;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.net.ssl.HttpsURLConnection;

//EJ1 desde linea:96 hasta linea: 99
//EJ2 desde linea:101 hasta linea: 104
//EJ3 desde linea:106 hasta linea: 109
//EJ5 desde linea:111 hasta linea: 115
//EJ6 desde linea:118 hasta linea: 126
//EJ7 desde linea:128 hasta linea: 136
//EJ8 desde linea:138 hasta linea: 156
//EJ9 desde linea:158 hasta linea: 167
//EJ13 desde linea:180 hasta linea: 192
//EJ14 desde linea: 196 hasta linea: 198
//EJ15 desde linea: 202 hasta linea: 215
//EJ17 desde linea:219 hasta linea: 226
public class Main {
    public static void main(String[] args) throws Exception {
        Jsonn json = new Jsonn();
        //JsonValue j = json.leeJSON("http://api.openweathermap.org/data/2.5/find?lat=42.232819&lon=-8.72264&cnt=20&APPID=8f8dccaf02657071004202f05c1fdce0");
        //System.out.println(json.leeJsonLocalidad( "Vigo"));
        //System.out.println(json.leeJsonLatLong( 42.23, -8.72));
        //System.out.println(json.leeJsonLatLongN(42.23, -8.72,3));
        /*json.escribeJSON(json.leeJsonLocalidad( "Vigo"),new File("A.json"));
        json.escribeJSON(json.leeJsonLatLong( 42.23, -8.72),new File("B.json"));*/
        /*System.out.println(json.nombreLocalidad(json.idLocalidad("Vigo")));
        System.out.println(json.nombreLocalidad(42.23, -8.72));
        System.out.println(json.datosLocalidad("Vigo"));
        json.preguntasInformatica();*/
        json.mostrarEventos("vigo",250,3);
    }
}


class Jsonn {
    
    public JsonValue leeJSON(String ruta) {
        JsonReader reader = null;
        JsonValue jsonV = null;
        try{
            if(ruta.toLowerCase().startsWith("http://")){
                URL url = new URL(ruta);
                InputStream is = url.openStream();
                reader = Json.createReader(is);
            }
            else if(ruta.toLowerCase().startsWith("https://")) {
                URL url = new URL(ruta);
                HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
                InputStream is = conn.getInputStream();
                reader = Json.createReader(is);
            }else{
                reader = Json.createReader(new FileReader(ruta));
            }
            jsonV = reader.read();
            /*JsonStructure jsonSt = reader.read();
            System.out.println(jsonSt.getValueType());
            JsonObject jsonObj = reader.readObject();
            System.out.println(jsonObj.getValueType());
            JsonArray jsonArr = reader.readArray();
            System.out.println(jsonArr.getValueType());
            */
        }catch(IOException e){
            System.out.println("Error procesando documento Json "+e.getLocalizedMessage());
        }
        if(reader != null)
        reader.close();

        return jsonV;
    }

    public
    void
     escribeJSON (JsonValue json, File f)throws FileNotFoundException{
        System.out.println("Guardando tipo: "+json.getValueType());
        PrintWriter pw = new PrintWriter(f);
        JsonWriter writer = Json.createWriter(pw);
        //writer.write((JsonStructure) json);
        if(json.getValueType() == JsonValue.ValueType.OBJECT)
            writer.writeObject((JsonObject)json);
        else if(json.getValueType() == JsonValue.ValueType.ARRAY)
            writer.writeArray((JsonArray)json);
        else
        System.out.println("No se soporta la escritura");
        writer.close();
    }
    //EJ1
    public JsonObject leeJsonLocalidad(String localidad){
        return leeJSON("http://api.openweathermap.org/data/2.5/weather?q="+localidad+",es&lang=es&APPID=8f8dccaf02657071004202f05c1fdce0").asJsonObject();
    }

    // EJ2
    public JsonObject leeJsonLatLong(double lat, double lon){
        return leeJSON("http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&APPID=8f8dccaf02657071004202f05c1fdce0").asJsonObject();
    }

    //EJ3
    public JsonObject leeJsonLatLongN(double lat, double lon,int num){
        return leeJSON("http://api.openweathermap.org/data/2.5/find?lat="+lat+"&lon="+lon+"&cnt="+num+"&APPID=8f8dccaf02657071004202f05c1fdce0").asJsonObject();
    }

    // EJ5
    public int idLocalidad(String localidad){
        JsonObject root = leeJsonLocalidad(localidad);
        return root.getInt("id");
    }

    //EJ6
    public String nombreLocalidad(int id){
        JsonObject root = leeJSON("http://api.openweathermap.org/data/2.5/weather?id="+id+"&lang=es&APPID=8f8dccaf02657071004202f05c1fdce0").asJsonObject();
        return root.getString("name");
    }

    public String nombreLocalidad(double lat,double lon) {
        JsonObject root = leeJsonLatLong(lat, lon);
        return root.getString("name");
    }

    //EJ7
    public double[] coordLocalidad(String localidad) {
        double[] res = new double[2];
        JsonObject root = leeJsonLocalidad(localidad);
        JsonObject coord = root.getJsonObject("coord");
        res[0] = coord.getJsonNumber("lat").doubleValue();
        res[1] = coord.getJsonNumber("lon").doubleValue();
        return res;
    }

    //EJ8
    public JsonObject datosLocalidad(String localidad){
        JsonObject root = leeJsonLocalidad(localidad);
        String fecha = unixTimeToString(root.getJsonNumber("dt").longValue());
        JsonObject main = root.getJsonObject("main");
        double temp = main.getJsonNumber("temp").doubleValue();
        int humedad = main.getJsonNumber("humidity").intValue();
        double nubes = root.getJsonObject("clouds").getInt("all")/100;
        double velocidadViento = root.getJsonObject("wind").getJsonNumber("speed").doubleValue();
        String pronostico = root.getJsonArray("weather").getJsonObject(0).getString("description");
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("fecha",fecha);
        builder.add("temp",temp);
        builder.add("humedad",humedad);
        builder.add("nubes",nubes);
        builder.add("velocidadViento",velocidadViento);
        builder.add("pronostico",pronostico);
        return builder.build();
    }
    //EJ9
    public JsonArray datosLocalidadesXCercanas(double lat,double lon,int num){
        JsonObject root = leeJsonLatLongN(lat, lon, num);
        JsonArray list = root.getJsonArray("list");
        JsonArrayBuilder resbuilder = Json.createArrayBuilder();
        for(int i = 0;i < list.size();i++){
            resbuilder.add(datosLocalidad(list.get(i).asJsonObject().getString("name")));
        }
        return resbuilder.build();
    }

    public String unixTimeToString(long unixTime ){
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Instant.ofEpochSecond(unixTime).atZone(ZoneId.of("GMT+1")).format(formatter);
    }


    //EJ13
    public void preguntasInformatica(){
        JsonObject root = leeJSON("https://opentdb.com/api.php?amount=20&category=18&difficulty=hard&type=multiple").asJsonObject();
        JsonArray results = root.getJsonArray("results");
        for(int i = 0;i < results.size();i++){
            JsonObject preguntaActual = results.get(i).asJsonObject();
            System.out.println(preguntaActual.getString("question"));
            System.out.println("*"+preguntaActual.getString("correct_answer"));
            JsonArray respuestasErroneas = preguntaActual.getJsonArray("incorrect_answers");
            for(int j = 0;j < respuestasErroneas.size();j++){
                System.out.println(respuestasErroneas.getString(j));
            }
        }
    }

    //EJ14
    public JsonValue eventosLocalidad(String localidad,int km,int numEventos){
        return leeJSON("http://api.eventful.com/json/events/search?q=music&l="+localidad+"&units=km&within="+km+"&page_size="+numEventos+"&app_key=c2tPtVFTrSk8xnQS");
    }

    //EJ15
    public void mostrarEventos(String localidad, int km, int numEventos){
        JsonValue root = eventosLocalidad(localidad,km,numEventos);
        JsonObject events = root.asJsonObject().getJsonObject("events");
        JsonArray eventos = events.getJsonArray("event");
        for(int i = 0;i < eventos.size();i++){
            JsonObject eventoActual = eventos.getJsonObject(i);
            System.out.println("Nombre: "+eventoActual.getString("title"));
            System.out.println("Descripción: "+eventoActual.getString("description"));
            System.out.println("Cuidad: "+eventoActual.getString("city_name"));
            System.out.println("Dirección: "+eventoActual.getString("venue_address"));
            System.out.print("tiempo: ");
            tiempoEnCiudadEventos(eventoActual.getString("city_name"));
            System.out.println();
        } 
    }

    //EJ17
    public void tiempoEnCiudadEventos(String localidad){
        JsonObject root = leeJsonLocalidad(localidad);
        JsonArray weather = root.getJsonArray("weather");
        for (int i = 0; i < weather.size(); i++) {
            JsonObject obj = weather.getJsonObject(i);
            System.out.println(obj.getString("description"));
        }
    }
    /*public JsonObject encontrarLocalidadPorNombre(JsonValue root,String localidad){//No hacía falta
        JsonObject rootobj = root.asJsonObject();
        JsonArray localidades = rootobj.getJsonArray("list");
        for(int i = 0;i < localidades.size();i++){
            if(localidades.getJsonObject(i).getString("name").equals(localidad)){
                return localidades.getJsonObject(i);
            }
        }
        return null;
    }

    public JsonObject encontrarLocalidadPorLatLong(JsonValue root,double lat,double lon){//No hacía falta
        JsonObject rootobj = root.asJsonObject();
        JsonArray localidades = rootobj.getJsonArray("list");
        for(int i = 0;i < localidades.size();i++){
            JsonObject actual = localidades.getJsonObject(i).getJsonObject("coord");
            if(actual.getJsonNumber("lat").doubleValue() == lat && actual.getJsonNumber("lon").doubleValue() == lon){
                return localidades.getJsonObject(i);
            }
        }
        return null;
    }*/
}