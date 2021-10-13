package com.ardctraining.core.feedback.service;

import com.ardctraining.core.model.CustomerFeedBackModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;

public interface CustomerFeedBackService {

    List<CustomerFeedBackModel> findByCustomerAndNegativeStatus(CustomerModel customer);

    void save(CustomerModel customer,CustomerFeedBackModel feedBack);
}
