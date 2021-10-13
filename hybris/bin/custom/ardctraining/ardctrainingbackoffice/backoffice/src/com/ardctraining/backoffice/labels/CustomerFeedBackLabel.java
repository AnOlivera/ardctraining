package com.ardctraining.backoffice.labels;

import com.ardctraining.core.model.CustomerFeedBackModel;
import com.hybris.cockpitng.labels.LabelProvider;

import java.util.Objects;

public class CustomerFeedBackLabel implements LabelProvider<CustomerFeedBackModel> {

    private static final String ALL_WILDCARD = "*";

    @Override
    public String getLabel(CustomerFeedBackModel model) {
        final String customer = Objects.nonNull(model.getCustomer()) ? model.getCustomer().getUid() : ALL_WILDCARD;

        return new StringBuilder()
                .append(customer)
                .append(" - ")
                .append(model.getSubject())
                .toString();
    }

    @Override
    public String getDescription(CustomerFeedBackModel model) {
        return getLabel(model);
    }

    @Override
    public String getIconPath(CustomerFeedBackModel model) {
        return null;
    }
}
