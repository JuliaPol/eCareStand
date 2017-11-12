package com.tsystems.eCareStand.pusher;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ApplicationScoped
public class PushBean implements Serializable {
    @Inject
    @Push(channel = "clock")
    private PushContext push;

    public void clockAction() {
        push.send("Update in database");
    }
}
