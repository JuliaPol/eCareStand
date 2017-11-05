package com.tsystems.eCareStand.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Option {
    private Long id;

    private String name;

    private Integer cost;

    private Integer costOfConnection;

    private String description;

    private List<String> compatibleOptions;

    private List<String> incompatibleOptions;

    private List<String> compatibleOptionsOf;

    public void update(Option option) {
        this.id = option.id;
        this.name = option.name;
        this.cost = option.cost;
        this.costOfConnection = option.costOfConnection;
        this.description = option.description;
        this.compatibleOptions = option.compatibleOptions;
        this.incompatibleOptions = option.incompatibleOptions;
        this.compatibleOptionsOf = option.compatibleOptionsOf;
    }
}
