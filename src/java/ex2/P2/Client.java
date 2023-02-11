/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex2.P1.P2;

import java.util.Scanner;
import javax.annotation.Resource;
import javax.jms.*;

/**
 *
 * @author siwakorn
 */
public class Client {
    @Resource(mappedName = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(mappedName = "jms/SimpleJMSQueue")
    private static Queue queue;
    
    public static void main(String[] args) throws JMSException {
        Connection connection = null;
        
        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession();
            
            Destination tempDestination = session.createTemporaryQueue();
            MessageConsumer consumer = session.createConsumer(tempDestination);
            ClientListener listener = new ClientListener();
            consumer.setMessageListener(listener);
            
            MessageProducer producer = session.createProducer(queue);
            
            connection.start();
            
            while (true) {
                Scanner input = new Scanner(System.in);
                
                System.out.println("Enter range (a, b) (q to exit): ");
                String range = input.nextLine();
                
                if (range.equals("q")) {
                    System.out.println("Program exited");
                    break;
                }
                
                TextMessage message = session.createTextMessage(range);
                message.setJMSReplyTo(tempDestination);
                message.setJMSCorrelationID("prime");
                
                producer.send(message);
            }
        }
        catch (JMSException exception) {
            System.out.println("JMS Error: " + exception.getMessage());
        }
        finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
