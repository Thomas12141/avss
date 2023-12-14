package Server;

import Frontend.GUI;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeoutException;
import java.util.logging.*;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import com.rabbitmq.client.*;


public class Server {
    private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final static int port = 5000;

    static GUI gui;

    static Recv recv;

    public static void main(String[] args) throws IOException{
        LogManager.getLogManager().reset();
        log.setLevel(Level.INFO);
        File serverLog = new File("ServerLog.log");
        if(!serverLog.exists()){
            boolean newFile = serverLog.createNewFile();
            if(!newFile){
                System.out.println("Cant create new logger file.");
            }
        }
        FileHandler fileHandler = new FileHandler("ServerLog.log", 1000000, 10, true);
        fileHandler.setLevel(Level.INFO);
        fileHandler.setFormatter(new SimpleFormatter());
        log.addHandler(fileHandler);

        new Thread(new Runnable(){

            @Override
            public void run() {

                try {
                    gui = new GUI();
                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SAXException e) {
                    throw new RuntimeException(e);
                }
                gui.setVisible(true);
            }
        }).start();
        new Thread(new Runnable(){

            @Override
            public void run() {

                try {
                    recv = new Recv();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (TimeoutException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        HttpHandler BootHandler = new BootHandler();
        httpServer.createContext("/",BootHandler);
        httpServer.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
        httpServer.start();
    }
}
