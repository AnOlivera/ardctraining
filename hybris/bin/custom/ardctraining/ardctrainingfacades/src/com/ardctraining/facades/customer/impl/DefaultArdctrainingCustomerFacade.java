package com.ardctraining.facades.customer.impl;

import com.ardctraining.core.feedback.service.CustomerFeedBackService;
import com.ardctraining.core.model.CustomerFeedBackModel;
import com.ardctraining.facades.customer.ArdctrainingCustomerFacade;
import com.ardctraining.facades.customer.data.CustomerFeedBackData;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DefaultArdctrainingCustomerFacade extends DefaultCustomerFacade implements ArdctrainingCustomerFacade {

    private CustomerFeedBackService customerFeedBackService;
    private Converter<CustomerFeedBackModel, CustomerFeedBackData> customerFeedBackConverter;
    private static final Logger LOG = Logger.getLogger(DefaultArdctrainingCustomerFacade.class);

    @Override
    public List<CustomerFeedBackModel> getCustomerFeedBack() {
        try{
            final UserModel user = getUserService().getCurrentUser();

            if(Objects.nonNull(user)  && user instanceof CustomerModel){
                final CustomerModel customer = (CustomerModel) user;
                final List<CustomerFeedBackModel> feedback = getCustomerFeedBackService().findByCustomerAndNegativeStatus(customer);
                return feedback;
            }else{
                LOG.error(String.format("Unable to get Customer FeedBack with current user %s",user.getUid()));
            }

        }catch(UnknownIdentifierException | AmbiguousIdentifierException ex) {
                LOG.error(String.format("There was an error %s ",ex));
        }
        return Collections.emptyList();
    }

    @Override
    public void save(CustomerFeedBackData feedBack) {
        try{
            final UserModel user = getUserService().getCurrentUser();

            if(Objects.nonNull(user) && user instanceof CustomerModel){
                   final CustomerModel customer = (CustomerModel) user;
                   CustomerFeedBackModel data = new CustomerFeedBackModel();
                    Byte nonRead = 0;
                   data.setSubject(feedBack.getSubject());
                   data.setMessage(feedBack.getMessage());
                   data.setDays(7);
                   data.setRead(nonRead);
                   getCustomerFeedBackService().save(customer,data);
            }else{
                LOG.error(String.format("Unable to save CustomerFeedBack with current user %s ",user.getUid()));
            }
        }catch(UnknownIdentifierException | AmbiguousIdentifierException ex){
            LOG.error(String.format("There was an error",ex));
        }
    }

    public CustomerFeedBackService getCustomerFeedBackService() {
        return customerFeedBackService;
    }

    public void setCustomerFeedBackService(CustomerFeedBackService customerFeedBackService) {
        this.customerFeedBackService = customerFeedBackService;
    }

    public Converter<CustomerFeedBackModel, CustomerFeedBackData> getCustomerFeedBackConverter() {
        return customerFeedBackConverter;
    }

    public void setCustomerFeedBackConverter(Converter<CustomerFeedBackModel, CustomerFeedBackData> customerFeedBackConverter) {
        this.customerFeedBackConverter = customerFeedBackConverter;
    }
}
