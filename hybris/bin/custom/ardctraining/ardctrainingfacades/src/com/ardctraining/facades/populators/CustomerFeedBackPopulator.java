package com.ardctraining.facades.populators;

import com.ardctraining.core.model.CustomerFeedBackModel;
import com.ardctraining.facades.customer.data.CustomerFeedBackData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.Locale;

public class CustomerFeedBackPopulator implements Populator<CustomerFeedBackModel, CustomerFeedBackData> {

    @Override
    public void populate(final CustomerFeedBackModel source,final CustomerFeedBackData target) throws ConversionException {
        target.setSubject(source.getSubject());
        target.setMessage(source.getMessage());
    }
}
