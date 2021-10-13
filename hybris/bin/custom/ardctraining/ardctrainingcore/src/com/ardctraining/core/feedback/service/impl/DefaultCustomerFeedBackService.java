package com.ardctraining.core.feedback.service.impl;


import com.ardctraining.core.attributehandlers.FeedBackStatusAttributeHandler;
import com.ardctraining.core.feedback.dao.CustomerFeedBackDao;
import com.ardctraining.core.feedback.service.CustomerFeedBackService;
import com.ardctraining.core.model.CustomerFeedBackModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DefaultCustomerFeedBackService implements CustomerFeedBackService {

    private CustomerFeedBackDao customerFeedBackDao;
    private  TimeService timeService;

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    private ModelService modelService;


    @Override
    public List<CustomerFeedBackModel> findByCustomerAndNegativeStatus(CustomerModel customer) {
        ServicesUtil.validateParameterNotNull(customer,"customer cannot be null");

        return getCustomerFeedBackDao().findByCustomerAndNegativeStatus(customer);
    }

    @Override
    public void save(CustomerModel customer,CustomerFeedBackModel feedBack) {
        ServicesUtil.validateParameterNotNull(customer,"customer cannot be null");

        Date now = getTimeService().getCurrentTime();

        feedBack.setCustomer(customer);
        feedBack.setSubmittedDate(now);
        modelService.save(feedBack);
    }

    public CustomerFeedBackDao getCustomerFeedBackDao() {
        return customerFeedBackDao;
    }

    public void setCustomerFeedBackDao(CustomerFeedBackDao customerFeedBackDao) {
        this.customerFeedBackDao = customerFeedBackDao;
    }

    public TimeService getTimeService() {
        return timeService;
    }

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

}
