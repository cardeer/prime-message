/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex2.P1;

import java.util.Scanner;
import javax.annotation.Resource;
import javax.jms.*;

/**
 *
 * @author siwakorn
 */
public class Consumer {
    @Resource(mappedName = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(mappedName = "jms/SimpleJMSTopic")
    private static Topic topic;
    
    public static void main(String[] args) throws JMSException {
        Connection connection = null;
        
        try {
            connection = connectionFactory.createConnection();
            Destination destination = (Destination) topic;
            
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(destination);
            
            ConsumerListener listener = new ConsumerListener();
            consumer.setMessageListener(listener);
            
            connection.start();
            
            while (true) {
                Scanner input = new Scanner(System.in);
                
                System.out.println("Press q to end: ");
                String cmd = input.nextLine();
                
                if (cmd.equals("q")) {
                    break;
                }
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
