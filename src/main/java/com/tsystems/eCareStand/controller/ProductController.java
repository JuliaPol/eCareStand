package com.tsystems.eCareStand.controller;

import com.tsystems.eCareStand.bean.AllProduct;
import com.tsystems.eCareStand.model.Option;
import com.tsystems.eCareStand.model.Rate;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("product")
@SessionScoped
public class ProductController implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    private AllProduct topRate;

    private List<Rate> topRateList;

    private List<Option> topOptionList;

    public List<Rate> getTopRateList() {
        topRateList = topRate.getTopRates();
        return topRateList;
    }

    public List<Option> getTopOptionList() {
        topOptionList= topRate.getTopOptions();
        return topOptionList;
    }
}
