package com.ardctraining.core.product.dao;

import com.ardctraining.core.model.CustomProductLabelModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.Date;
import java.util.List;

public interface CustomProductLabelDao {

    List<CustomProductLabelModel> findByCustomerAndProduct(CustomerModel customer, ProductModel product);

    List<CustomProductLabelModel> findExpired(Date now);

    List<CustomProductLabelModel> findByCustomerAndProductAndNullCustomer(CustomerModel customer,ProductModel product);

    List<CustomProductLabelModel> findByProduct( ProductModel product);
}
