/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex2.P1.P2;

import javax.jms.MessageListener;
import javax.jms.*;

/**
 *
 * @author siwakorn
 */
public class ClientListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                
                System.out.println("The result from the server is: " + textMessage.getText());
            }
        }
        catch (JMSException exception) {
            System.out.println("JMS Error: " + exception.getMessage());
        }
    }
}
