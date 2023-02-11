/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex2.P1;

import javax.annotation.Resource;
import java.util.*;
import javax.jms.*;

/**
 *
 * @author siwakorn
 */
public class Producer {
    @Resource(mappedName = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(mappedName = "jms/SimpleJMSTopic")
    private static Topic topic;
    
    public static void main(String[] args) throws JMSException {
        Connection connection = null;
        
        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            Scanner input = new Scanner(System.in);
            
            Destination destination = (Destination) topic;
            
            MessageProducer producer = session.createProducer(destination);
            
            while (true) {
                System.out.print("Enter score: ");
                String broadcast = input.nextLine();
                
                if (broadcast.equals("q")) {
                    TextMessage message = session.createTextMessage("q");
                    producer.send(message);
                    
                    System.out.println("Programe exited");
                    break;
                }
                
                TextMessage message = session.createTextMessage(broadcast);
                producer.send(message);
            }
        }
        catch (JMSException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    
}
