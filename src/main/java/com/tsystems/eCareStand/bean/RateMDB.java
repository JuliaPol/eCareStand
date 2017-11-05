package com.tsystems.eCareStand.bean;

import org.apache.log4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;
import java.io.IOException;

@MessageDriven(name = "RateMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/testQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })

public class RateMDB implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(RateMDB.class.toString());

    @EJB
    private AllProduct topRate;

    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message rcvMessage) {
        TextMessage msg = null;
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                String message = msg.getText();
                LOGGER.info("Received Message from queue: " + message);
                if (message.contains("rate changed")) {
                    topRate.updateRate(message.substring(message.lastIndexOf(" ")+1));
                } else {
                    topRate.updateTopRates();
                }
            } else {
                LOGGER.warn("Message of wrong type: " + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
