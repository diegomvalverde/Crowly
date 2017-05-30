package connectors;

import library.Cuerpo;
import library.IConstants;
import library.VideoResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class MSClient implements IConstants {
    private ArrayList<Cuerpo> Cuerpos;
    private ArrayList<VideoResponse> VideosPendientes;
    private int Hora = 0;

    public MSClient() {
        Cuerpos = new ArrayList<>();
        VideosPendientes = new ArrayList<>();
    }

    public ArrayList<Cuerpo> getCuerpos() {
        return Cuerpos;
    }

    public ArrayList<VideoResponse> getVideosPendientes() {
        return VideosPendientes;
    }

    public void procesarVideo(String pURLVideo) {
        // puedo tener el URL hardcoded y aqui armo el PayLoad
        String payload = POST_BODY.replace("@@URL@@", pURLVideo);
        VideoResponse videoResp = HttpRequestor.post(MCS_URL, payload, MCS_IDKEY);
        if (videoResp != null) {
            VideosPendientes.add(videoResp);
        }
    }

    public void procesarRespuestaVideo(VideoResponse videoResponse) {
        VideoResponse tmp = HttpRequestor.get(videoResponse, MCS_IDKEY);
//        System.out.println(tmp.getContent());
        cargarCuerpos(tmp);
    }

    private void cargarCuerpos(VideoResponse videoResponse) {
        // aqui proceso el Json creando los objetos library.Cuerpo que vienen
        // en el Json y los meto uno a uno en la lista de Cuerpos
        JSONParser parser = new JSONParser();
        try
        {
            Object obj = parser.parse(videoResponse.getContent()); // Creo un objeto con el Json (string) parseado.
            JSONObject jsonObject = (JSONObject) obj;   // Casteo el objeto a un Objeto Json
            String msg = (String) jsonObject.get("processingResult"); // Al objeto Json le pido la llave "processing..."
            obj = parser.parse(msg); // Parseo el string (valor) que me deio la llave anterior.
            jsonObject = (JSONObject) obj; // Lo Casteo
            JSONArray tmp = (JSONArray) jsonObject.get("fragments"); // Le pido al Objeto Json "fragments"
            tmp.remove(0);  // Le remuevo la posicion 0 pues este no es un objeto que se mueve
            for (Object Movimiento: tmp) // Recorre la lista de movimientos que me da "fragments"
            {
                jsonObject = (JSONObject) Movimiento; // Casteo Movimiento a un Json object para pedir los eventos
                if(jsonObject.containsKey("events")) // Le digo que si tiene la llave "events" entre al if
                {

                    JSONArray array = (JSONArray) jsonObject.get("events"); // Pido la llave "events" y csteo a array

                    array = (JSONArray) array.get(0); // Pido el sub cero del array que devuelve events (aqui empieza el mivimiento)
                    jsonObject = (JSONObject) array.get(0); // Pido el sub cero de array (primr cuado donde detecta movimiento)
                    array = (JSONArray) jsonObject.get("locations"); // Le pido locations
                    jsonObject = (JSONObject) array.get(0); // Y tomo el sub cero (x y y)
                    // los siguientes if son para determinar la hora del video que anliza
                    if(Hora < 3) {
                        Cuerpos.add(new Cuerpo((double) jsonObject.get("x"), (double) jsonObject.get("y"), 0));
                    }else if(Hora > 3 && Hora < 6 )
                    {
                        Cuerpos.add(new Cuerpo((double) jsonObject.get("x"), (double) jsonObject.get("y"), 1));

                    }else {
                        Cuerpos.add(new Cuerpo((double) jsonObject.get("x"), (double) jsonObject.get("y"), 2));
                    }
//                    System.out.println(jsonObject.get("x") + " | " + jsonObject.get("y"));
                }

            }

        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        System.out.println(Hora);
        Hora++;
    }
}
