
public interface IConstants {
    String POST_BODY = "HTTP/1.1 "+
            "Content-Type: application/json\n"+
            "Host: westus.api.cognitive.microsoft.com\n"+
            "Ocp-Apim-Subscription-Key: 799dca11f3ca4e67b75c9aa34848823b\n"+
            "{ \"url\":\"http://gdurl.com/5oZW\"}";
    String MCS_URL = "https://westus.api.cognitive.microsoft.com/video/v1.0/detectmotion?sensitivityLevel=low&frameSamplingValue=1";
    String[] LISTA_VIDEOS = {
            "http://gdurl.com/5oZW/download",
            "http://gdurl.com/5oZW/download",
            "http://gdurl.com/5oZW/download",
            "http://gdurl.com/5oZW/download",
    };
    int ESPERA_ENTRE_LLAMADAS = 25000;
    String LOCATION_RESULT_URL_KEY = "OPERATION-LOCATION";
    String LOCATION_RESULT_UR_KEY = "OPERATION-LOCATION";

}
