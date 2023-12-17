package Server;

import Frontend.GUI;
import Logic.BootRepository;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.stream.Collectors;


public class BootHandler implements HttpHandler {
    private GUI gui;

    public BootHandler(GUI gui){
        this.gui = gui;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        InputStream is = exchange.getRequestBody();
        InputStream requestBody = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
        String requestBodyString = reader.lines().collect(Collectors.joining());

        if(requestBodyString.toLowerCase().contains("add")){
            try {
                handlePostRequest(requestBodyString,exchange);
                if(gui!=null) gui.refresh();
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        }else if(requestBodyString.toLowerCase().contains("delete")){
            try {
                handleDeleteRequest(requestBodyString,exchange);
                if(gui!=null) gui.refresh();
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        }else {
            sendResponse(exchange, "", 405);
        }
    }

    private void handlePostRequest(String requestBodyString, HttpExchange exchange) throws IOException, ParserConfigurationException, TransformerException, SAXException {

        String id = getIdFromBody(requestBodyString);

        try {
            BootRepository repository = new BootRepository();
            repository.addNewBootToList(id);
            Server.log.info("Boat successfully added.");
            System.out.println("Boat successfully added.");
            sendResponse(exchange, "Boot erfolgreich hinzugefügt", 201);

        } catch (Exception e) {
            Server.log.severe(e.getMessage());
            sendResponse(exchange, e.getMessage(), 400);
        }
    }

    private void handleDeleteRequest(String requestBodyString,HttpExchange exchange) throws IOException, ParserConfigurationException, TransformerException, SAXException {

        String id = getIdFromBody(requestBodyString);

        try {
            BootRepository repository = new BootRepository();
            repository.deleteBootFromList(id);
            Server.log.info("Boat successfully deleted.");
            System.out.println("Boat successfully deleted.");
            sendResponse(exchange, "Boot erfolgreich gelöscht", 200);

        } catch (Exception e) {
            Server.log.severe(e.getMessage());
            sendResponse(exchange, e.getMessage(), 500);
        }
    }

    private static void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {

        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }


    private static String getIdFromBody(String body){
        StringBuilder id = new StringBuilder();
        int position = body.toLowerCase().indexOf("id");
        while (position<body.length()){
            char temp = body.charAt(position);
            if(temp>=48&&temp<=57){
                id.append(temp);
            }else if(!id.isEmpty()){
                break;
            }
            position++;
        }
        return id.toString();
    }
}
