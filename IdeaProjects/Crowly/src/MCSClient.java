import java.io.IOException;
import java.util.ArrayList;

public class MCSClient implements IConstants{
    String Key;
    ArrayList<Cuerpos> Cuerpos;

    MCSClient()
    {

    }

    void procesar(String URLVideo) throws IOException, InterruptedException

    {
        String payload = POST_BODY.replace("@@URL",URLVideo);
        String ResponseResult = HTTPRequest.post(MCS_URL, payload);
//        analizar(jSonResult);
        int posStart = ResponseResult.indexOf(IConstants.LOCATION_RESULT_URL_KEY);


    }

    private void analizar(String response)
    {
        // Aqui poroceso el Json de mierda y lo meyto al array de cuerpos

    }

    ArrayList<Cuerpos> getCuerpos()
    {

        return Cuerpos;
    }
}
