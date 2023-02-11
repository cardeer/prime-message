/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex2.P1.P2;

import java.util.ArrayList;
import javax.jms.*;

/**
 *
 * @author siwakorn
 */
public class ServerListener implements MessageListener {
    private Session session;
    private MessageProducer producer;
    
    public ServerListener(Session session) throws JMSException {
        try {
            this.session = session;
            this.producer = session.createProducer(null);
        }
        catch (JMSException exception) {
            System.out.println("JMS Error: " + exception.getMessage());
        }
    }
    
    @Override
    public void onMessage(Message message) {
        String correlationId = null;
        Destination replyDestination = null;
        
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                
                correlationId = message.getJMSCorrelationID();
                replyDestination = message.getJMSReplyTo();
                String body = textMessage.getText();
                
                Prime prime = new Prime(body);
                int count = prime.countPrimeNumbers();
                                
                TextMessage replyMessage = session.createTextMessage("The number of primes betwen " + prime.getMin() + " and " + prime.getMax() + " is " + count);
                replyMessage.setJMSCorrelationID(correlationId);
                
                producer.send(replyDestination, replyMessage);
            }
        }
        catch (JMSException exception) {
            System.out.println("JMS Error: " + exception.getMessage());
        }
        catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
            
            try {
                TextMessage replyMessage = session.createTextMessage("Pattern doesn't match. Plase try again.");
                replyMessage.setJMSCorrelationID(correlationId);
                
                producer.send(replyDestination, replyMessage);
            }
            catch (JMSException e) {}
        }
    }
    
}
