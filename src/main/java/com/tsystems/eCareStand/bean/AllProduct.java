package com.tsystems.eCareStand.bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.eCareStand.model.Option;
import com.tsystems.eCareStand.model.Rate;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless(name = "allProduct")
public class AllProduct implements Serializable {

    private static final Logger log = Logger.getLogger(AllProduct.class);

    private List<Rate> topRates;
    private List<Rate> allRates;
    private List<Option> allOptions;
    private ObjectMapper mapper;

    public AllProduct() {
        topRates = new ArrayList<>();
        allOptions = new ArrayList<>();
        mapper = new ObjectMapper();
    }

    @PostConstruct
    public void init() {
        updateTopRates();
        updateTopOptions();
        log.info("Application was successfully started and has got current top rate list.");
    }

    public void updateTopRates() {
        String jsonString = update("http://localhost:8080/tariffs/all");
        if (jsonString != null) {
            List<Rate> list = null;
            try {
                list = mapper.readValue(jsonString,
                        new TypeReference<List<Rate>>() {
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
            allRates = list;
            for (Rate rate : allRates) {
                log.info(rate.getName());
            }
            topRates = allRates.stream().skip(allRates.size()-3).collect(Collectors.toList());
            Collections.reverse(topRates);
        }
    }

    public void updateTopOptions() {
        String jsonString = update("http://localhost:8080/options/all");
        if (jsonString != null) {
            List<Option> list = null;
            try {
                list = mapper.readValue(jsonString,
                        new TypeReference<List<Option>>() {
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
            allOptions = list;
        }
    }

    public void updateRate(String id) throws IOException {
        Rate rate = allRates.stream().filter(o -> o.getId().equals(Long.parseLong(id))).findFirst().get();
        String jsonString = update("http://localhost:8080/tariffs/" + id);
        if (jsonString != null) {
            Rate rate1 = mapper.readValue(jsonString, Rate.class);
            rate.update(rate1);
        }
    }

    public void updateOption(String id) throws IOException {
        Option option = allOptions.stream().filter(o -> o.getId().equals(Long.parseLong(id))).findFirst().get();
        String jsonString = update("http://localhost:8080/options?id=" + id);
        if (jsonString != null) {
            Option option1 = mapper.readValue(jsonString, Option.class);
            option.update(option1);
        }
    }

    private String update(String url) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(url);
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
        log.info(response.getStatus());
        if (response.getStatus() == 200) {
            return response.readEntity(String.class);
        }
        response.close();
        return null;
    }

    public List<Rate> getTopRates() {
        return topRates;
    }

    public List<Option> getTopOptions() {
        return allOptions;
    }

    public List<Rate> getAllRates() {
        return allRates;
    }
}
