package com.tsystems.eCareStand.controller;

import com.tsystems.eCareStand.ejb.EasyEjb;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("easy")
@SessionScoped
public class EasyController implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Injected GreeterEJB client
     */
    @EJB
    private EasyEjb easyEjb;

    /**
     * Stores the response from the call to greeterEJB.sayHello(...)
     */
    private String message;

    /**
     * Invoke greeterEJB.sayHello(...) and store the message
     *
     * @param name The name of the person to be greeted
     */
    public void setName(String name) {
        message = easyEjb.sayHello(name);
    }

    /**
     * Get the greeting message, customized with the name of the person to be greeted.
     *
     * @return message. The greeting message.
     */
    public String getMessage() {
        return message;
    }
}
