package Handler;

import Logic.BootRepository;
import Logic.NichtVerlieheneBoote;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.xml.sax.SAXException;
import com.google.gson.Gson;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.stream.Collectors;


public class BootHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();

        switch (requestMethod) {
            case "DELETE":
                try {
                    handleDeleteRequest(exchange);
                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(e);
                } catch (TransformerException e) {
                    throw new RuntimeException(e);
                } catch (SAXException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "POST":
                try {
                    handlePostRequest(exchange);
                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(e);
                } catch (TransformerException e) {
                    throw new RuntimeException(e);
                } catch (SAXException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                exchange.sendResponseHeaders(405, 0);
                break;
        }
    }

    //POST-Request: addNewBootToList
    private void handlePostRequest(HttpExchange exchange) throws IOException, ParserConfigurationException, TransformerException, SAXException {

        InputStream requestBody = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
        String requestBodyString = reader.lines().collect(Collectors.joining());

        try {
            //TODO
            //NichtVerlieheneBoote newBoot = fromJson(requestBodyString, NichtVerlieheneBoote.class);
            //String id = newBoot.getId();
            BootRepository repository = new BootRepository();
            repository.addNewBootToList(id);

            String response = "Boot erfolgreich hinzugefügt";
            sendResponse(exchange, response, 201);
        } catch (Exception e) {
            sendResponse(exchange, e.getMessage(), 400);
        }
    }

    //DELETE-Request: deleteBootFromList; ID in URL
    private void handleDeleteRequest(HttpExchange exchange) throws IOException, ParserConfigurationException, TransformerException, SAXException {

        String requestURI = exchange.getRequestURI().toString();
        String[] parts = requestURI.split("/");
        //Annahme: /xyz/boot/123 -> id = 123
        String id = parts[parts.length - 1];

        try {
            BootRepository repository = new BootRepository();
            repository.deleteBootFromList(id);

            sendResponse(exchange, "Boot erfolgreich gelöscht", 200);
        } catch (Exception e) {
            sendResponse(exchange, e.getMessage(), 500);
        }
    }

    private static void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {

        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private static String toJson(Object obj) {

        return null;
    }

    private <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("Json string is null or empty");
        }
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

}
