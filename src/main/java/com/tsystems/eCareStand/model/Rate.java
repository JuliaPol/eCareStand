package com.tsystems.eCareStand.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {
    private Long id;

    private String name;

    private Integer cost;

    private String description;

    private Integer calls;

    private Integer sms;

    private Integer internet;

    private String image;

    private List<Option> optionList;

    public void update(Rate rate) {
        this.id = rate.id;
        this.name = rate.name;
        this.cost = rate.cost;
        this.description = rate.description;
        this.calls = rate.calls;
        this.sms = rate.sms;
        this.internet = rate.internet;
        this.optionList = rate.optionList;
        this.image = rate.image;
    }

}
