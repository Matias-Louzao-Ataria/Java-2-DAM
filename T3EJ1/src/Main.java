import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonReader;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.net.ssl.HttpsURLConnection;

public class Main {
    public static void main(String[] args) throws Exception {
        Jsonn json = new Jsonn();
        //JsonValue j = json.leeJSON("http://api.openweathermap.org/data/2.5/find?lat=42.232819&lon=-8.72264&cnt=20&APPID=8f8dccaf02657071004202f05c1fdce0");
        System.out.println(json.leeJsonLocalidad( "Vigo"));
        System.out.println(json.leeJsonLatLong( 42.23, -8.72));
        /*json.escribeJSON(json.leeJsonLocalidad( "Vigo"),new File("A.json"));
        json.escribeJSON(json.leeJsonLatLong( 42.23, -8.72),new File("B.json"));*/
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

    public JsonObject leeJsonLocalidad(String localidad){
        return leeJSON("http://api.openweathermap.org/data/2.5/weather?q="+localidad+",es&lang=es&APPID=8f8dccaf02657071004202f05c1fdce0").asJsonObject();
    }

    public JsonObject leeJsonLatLong(double lat, double lon){
        return leeJSON("http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&APPID=8f8dccaf02657071004202f05c1fdce0").asJsonObject();
    }
    
    public JsonObject encontrarLocalidadPorNombre(JsonValue root,String localidad){//No hacía falta
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
    }
}