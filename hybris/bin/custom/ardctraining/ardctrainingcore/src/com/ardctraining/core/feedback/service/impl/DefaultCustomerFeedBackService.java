package com.ardctraining.core.feedback.service.impl;


import com.ardctraining.core.attributehandlers.FeedBackStatusAttributeHandler;
import com.ardctraining.core.feedback.dao.CustomerFeedBackDao;
import com.ardctraining.core.feedback.service.CustomerFeedBackService;
import com.ardctraining.core.model.CustomerFeedBackEmailProcessModel;
import com.ardctraining.core.model.CustomerFeedBackModel;
import de.hybris.platform.commerceservices.i18n.CommerceCommonI18NService;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.site.BaseSiteService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultCustomerFeedBackService implements CustomerFeedBackService {

    private CustomerFeedBackDao customerFeedBackDao;
    private  TimeService timeService;
    private BusinessProcessService businessProcessService;
    private BaseSiteService baseSiteService;
    private CommerceCommonI18NService commerceCommonI18NService;
    private ModelService modelService;


    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }




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

        final Set<String> feedbacks = getCustomerFeedBack(Collections.singletonList(feedBack));
        final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss.S");
        final Date nowMail = getTimeService().getCurrentTime();
        final CustomerFeedBackEmailProcessModel process = getBusinessProcessService().createProcess(
                new StringBuilder()
                .append("customerFeedBackEmailProcess")
                .append("-")
                .append(dateFormat.format(nowMail))
                        .toString(),"customerFeedBackEmailProcess");
        process.setLanguage(getCommerceCommonI18NService().getCurrentLanguage());
        process.setCustomerFeedBacks(feedbacks);
        process.setSite(getBaseSiteService().getBaseSiteForUID("electronics"));

        modelService.save(process);

        getBusinessProcessService().startProcess(process);
    }

    private Set<String> getCustomerFeedBack(final List<CustomerFeedBackModel> feedback){
        return feedback
                .stream()
                .map((CustomerFeedBackModel feed) ->
                    new StringBuilder()
                            .append(Objects.isNull(feed.getCustomer()) ? StringUtils.EMPTY : feed.getCustomer().getUid())
                            .append("|")
                            .append(feed.getSubject())
                            .append("|")
                            .append(feed.getMessage())
                            .append("|")
                            .append(feed.getSubmittedDate())
                            .toString()
                ).collect(Collectors.toSet());
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

    public BusinessProcessService getBusinessProcessService() {
        return businessProcessService;
    }

    public void setBusinessProcessService(BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    public BaseSiteService getBaseSiteService() {
        return baseSiteService;
    }

    public void setBaseSiteService(BaseSiteService baseSiteService) {
        this.baseSiteService = baseSiteService;
    }

    public CommerceCommonI18NService getCommerceCommonI18NService() {
        return commerceCommonI18NService;
    }

    public void setCommerceCommonI18NService(CommerceCommonI18NService commerceCommonI18NService) {
        this.commerceCommonI18NService = commerceCommonI18NService;
    }
}
