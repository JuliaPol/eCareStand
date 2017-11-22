package com.tsystems.eCareStand.controller;

import com.tsystems.eCareStand.bean.AllProduct;
import com.tsystems.eCareStand.model.Option;
import com.tsystems.eCareStand.model.Rate;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named("product")
@ApplicationScoped
public class ProductController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private AllProduct topRate;

    private List<Rate> topRateList;
    private List<Rate> allRateList;
    private List<Option> allOptionList;

    public List<Rate> getTopRateList() {
        topRateList = topRate.getTopRates();
        return topRateList;
    }

    public List<Rate> getAllRateList() {
        allRateList = topRate.getAllRates();
        return allRateList;
    }

    public List<Option> getTopOptionList() {
        allOptionList= topRate.getTopOptions();
        return allOptionList;
    }
}
