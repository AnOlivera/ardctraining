package com.ardctraining.core.feedback.dao.impl;

import com.ardctraining.core.feedback.dao.CustomerFeedBackDao;
import com.ardctraining.core.model.CustomerFeedBackModel;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DefaultCustomerFeedBackDao implements CustomerFeedBackDao {

    private  FlexibleSearchService flexibleSearchService;
    private static final Logger LOG = Logger.getLogger(DefaultCustomerFeedBackDao.class);

    private static final String SELECT =
            "SELECT {" + ItemModel.PK + "} " +
            "FROM   {" + CustomerFeedBackModel._TYPECODE + "} ";
    private static final String FIND_BY_CUSTOMER_AND_STATUS_NEGATIVE =
              SELECT +
            "WHERE {" + CustomerFeedBackModel.CUSTOMER + "} = ?customer AND"+
            "{" + CustomerFeedBackModel.READ + "} = 0";


    @Override
    public List<CustomerFeedBackModel> findByCustomerAndNegativeStatus(CustomerModel customer) {
        FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_BY_CUSTOMER_AND_STATUS_NEGATIVE);
        query.addQueryParameter("customer",customer);
        return findResult(query);
    }
    private List<CustomerFeedBackModel> findResult(FlexibleSearchQuery query){
        final SearchResult<CustomerFeedBackModel> result = getFlexibleSearchService().search(query);

        if(Objects.nonNull(result) && CollectionUtils.isNotEmpty(result.getResult())){
            return result.getResult();
        }

        LOG.warn("UNABLE TO FIND RESULTS FOR query");

        return Collections.emptyList();
    }



    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}
