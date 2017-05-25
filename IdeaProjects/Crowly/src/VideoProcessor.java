import java.io.IOException;
import java.util.ArrayList;

public class VideoProcessor implements IConstants {
    private MCSClient McSClient;
    private ArrayList<Cuerpos> CuerposTodosLosVideos;

    public VideoProcessor() {
        McSClient = new MCSClient();
    }

    void analyze() {
        try {
            for (String urlVideo : LISTA_VIDEOS) {
                McSClient.procesar(urlVideo);
                Thread.sleep(ESPERA_ENTRE_LLAMADAS);
            }
            CuerposTodosLosVideos = McSClient.getCuerpos();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }
}
