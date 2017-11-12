package com.tsystems.eCareStand.bean;

import com.tsystems.eCareStand.controller.ProductController;
import com.tsystems.eCareStand.pusher.PushBean;
import org.apache.log4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;
import java.io.IOException;

@MessageDriven(name = "RateMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/testQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
//@DependsOn("allProduct")
public class RateMDB implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(RateMDB.class.toString());

    @Inject
    private AllProduct allProduct;

    @Inject
    private PushBean pushBean;

    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message rcvMessage) {
        TextMessage msg = null;
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                String message = msg.getText();
                String[] strings = message.split(":");
                String type = strings[0];
                message = strings[1];
                LOGGER.info("Received Message from queue: " + message);
                switch (type) {
                    case "edit" : {
                        String id = message.substring(message.lastIndexOf(" ") + 1);
                        if (message.contains("rate changed")) {
                            allProduct.updateRate(id);
                        } else {
                            allProduct.updateOption(id);
                        }
                        break;
                    }
//                    case "delete" : {
//                        break;
//                    }
//                    case "create" : {
//                        break;
//                    }
                    default: {
                        if (message.contains("rate")) {
                            allProduct.updateTopRates();
                        } else {
                            allProduct.updateTopOptions();
                        }
                        //LOGGER.warn("Message of wrong type: " + type);
                        break;
                    }
                }
                pushBean.clockAction();
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
