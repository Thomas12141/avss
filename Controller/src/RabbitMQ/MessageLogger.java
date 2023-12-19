package RabbitMQ;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageLogger {
    private static final String filename = "RabbitMQLog.log";
    public static void LogMessage(String message) throws IOException {

        //öffne oder erstelle Datei
        try(FileWriter filewriter = new FileWriter(filename, true)){
            String formattedMessage = FormatMessage(message);
            filewriter.write(formattedMessage + "\n");
            filewriter.flush();
            System.out.println("New message saved in " + filename + ".");

        } catch (IOException exception){
            System.out.println("An error occurred while writing to " + filename + ": " + exception.getMessage());
        }
    }

    public static String FormatMessage(String message){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        return "[" + formattedDateTime + "] " + message;
    }
}
