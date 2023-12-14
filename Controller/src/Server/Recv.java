package Server;

import Logic.BootRepository;
import com.rabbitmq.client.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv implements AutoCloseable  {
    Connection connection;
    Channel channel;
    public Recv() throws IOException, TimeoutException {
        String addedSchiff = "AddedSchiff";
        String removedSchiff = "RemovedSchiff";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("stud-vm-8892.fh-muenster.de");
        factory.setUsername("PG3-T1");
        factory.setPort(5672);
        factory.setPassword("nuII9e");
        factory.setVirtualHost("PG3-T1");
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
        //TODO: change to exchange
        channel.queueDeclare(addedSchiff, false, false, false, null);
        DeliverCallback deliverCallback1 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            BootRepository repository = new BootRepository();
            try {
                repository.addNewBootToList(getIdFromMessage(message));
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        };
        channel.basicConsume(addedSchiff, true, deliverCallback1, consumerTag -> { });
        //TODO: change to exchange
        channel.queueDeclare(removedSchiff, false, false, false, null);
        DeliverCallback deliverCallback2 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            BootRepository repository = new BootRepository();
            try {
                repository.deleteBootFromList(getIdFromMessage(message));
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            } catch (SAXException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
        channel.basicConsume(removedSchiff, true, deliverCallback2, consumerTag -> { });
    }

    @Override
    public void close() throws Exception {
        channel.close();
        connection.close();
    }

    private String getIdFromMessage(String message){
        StringBuilder id = new StringBuilder();
        int position = message.toLowerCase().indexOf("id");
        if (position==-1){
            return "";
        }
        while (position<message.length()){
            char temp = message.charAt(position);
            if(temp>=48&&temp<=57){
                id.append(temp);
            }else if(!id.isEmpty()){
                break;
            }
            position++;
        }
        return id.toString();
    }
    public static void main(String[] args) throws IOException, TimeoutException {
        Recv recv = new Recv();
    }
}
