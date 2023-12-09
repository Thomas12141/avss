package Server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
        while (true){
            ServerSocket serverSocket;
            try {
                serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                log.severe(e.getMessage());
            }
        }
    }
}
