package com.ardctraining.core.feedback.dao;

import com.ardctraining.core.model.CustomerFeedBackModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;

public interface CustomerFeedBackDao {

    List<CustomerFeedBackModel> findByCustomerAndNegativeStatus(CustomerModel customer);


}
