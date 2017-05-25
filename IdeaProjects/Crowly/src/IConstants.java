public interface IConstants {
    String POST_BODY = "{ \"url\":\"@@URL@@\" }";

    String MCS_URL = "https://westus.api.cognitive.microsoft.com/video/v1.0/detectmotion?sensitivityLevel=low&frameSamplingValue=1";

    String[] LISTA_VIDEOS = {
            "http://gdurl.com/9KHx",
            "https://www.dropbox.com/s/nhc9xsxlnsn0bv8/WIN_20170503_15_26_09_Pro.mp4?dl=0"
    };

    String LOCATION_RESULT_URL_KEY = "Operation-Location";
    String LOCATION_RESULT_ACCESS_KEY = "apim-request-id";
    String CAMBIO_LINEA = "\n";

    int ESPERA_ENTRE_LLAMADAS = 60000;

    String MCS_IDKEY = "799dca11f3ca4e67b75c9aa34848823b";

}
