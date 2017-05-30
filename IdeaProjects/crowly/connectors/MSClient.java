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
            Object obj = parser.parse(videoResponse.getContent());

            JSONObject jsonObject = (JSONObject) obj;
//            System.out.println(jsonObject);

            // loop array
            String msg = (String) jsonObject.get("processingResult");
            obj = parser.parse(msg);
            jsonObject = (JSONObject) obj;

            JSONArray tmp = (JSONArray) jsonObject.get("fragments");
            tmp.remove(0);
//            System.out.println(tmp.toJSONString());
            for (Object Movimiento: tmp)
            {

                jsonObject = (JSONObject) Movimiento;
//                System.out.println(jsonObject.toJSONString());
                if(jsonObject.containsKey("events"))
                {

                    JSONArray array = (JSONArray) jsonObject.get("events");

                    array = (JSONArray) array.get(0);
                    jsonObject = (JSONObject) array.get(0);
                    array = (JSONArray) jsonObject.get("locations");
                    jsonObject = (JSONObject) array.get(0);
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
        Hora++;
    }
}
