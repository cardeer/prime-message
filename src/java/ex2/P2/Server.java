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
public class Server {
    @Resource(mappedName = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(mappedName = "jms/SimpleJMSQueue")
    private static Queue queue;
    
    public static void main(String[] args) throws JMSException {
        Connection connection = null;
        
        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            ServerListener listener = new ServerListener(session);
            
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(listener);
            
            connection.start();
            
            while (true) {
                Scanner input = new Scanner(System.in);
                
                System.out.println("Press q to exit: ");
                String cmd = input.nextLine();
                
                if (cmd.equals("q")) {
                    System.out.println("Program exited.");
                    break;
                }
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
