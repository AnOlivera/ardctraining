package com.ardctraining.facades.customer;

import com.ardctraining.core.model.CustomerFeedBackModel;
import com.ardctraining.facades.customer.data.CustomerFeedBackData;
import de.hybris.platform.commercefacades.customer.CustomerFacade;

import java.util.List;

public interface ArdctrainingCustomerFacade extends CustomerFacade {

    List<CustomerFeedBackModel> getCustomerFeedBack();

    void save(CustomerFeedBackData feedBack);
}
