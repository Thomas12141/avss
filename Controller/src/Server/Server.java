package Server;

import Frontend.GUI;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;
import java.util.logging.*;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;


public class Server {
    private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final static int port = 5000;

    private static GUI gui;

    private static Recv recv;

    public static void main(String[] args) throws IOException, InterruptedException {
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
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable(){

            @Override
            public void run() {

                try {
                    gui = new GUI();
                    countDownLatch.countDown();
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
        countDownLatch.await();
        new Thread(new Runnable(){

            @Override
            public void run() {

                try {
                    recv = new Recv(gui);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (TimeoutException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        HttpHandler BootHandler = new BootHandler(gui);
        httpServer.createContext("/",BootHandler);
        httpServer.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
        httpServer.start();
    }
}
