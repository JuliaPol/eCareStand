package com.tsystems.eCareStand.ejb;

import javax.ejb.Stateless;

@Stateless
public class EasyEjb {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
