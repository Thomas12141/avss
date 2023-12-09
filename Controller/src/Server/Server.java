package Server;

import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.*;

public class Server {
    private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final static int port = 5000;
    public static void main(String[] args) throws IOException {

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
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        //TODO: Implement HTTTPHandler.
        //httpServer.createContext("/boot", OurHttpHandler);
        httpServer.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
        httpServer.start();
    }
}
