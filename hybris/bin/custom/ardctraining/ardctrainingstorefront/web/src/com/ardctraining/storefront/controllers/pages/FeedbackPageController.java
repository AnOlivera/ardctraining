package com.ardctraining.storefront.controllers.pages;


import com.ardctraining.core.feedback.service.CustomerFeedBackService;
import com.ardctraining.core.model.CustomerFeedBackModel;
import com.ardctraining.facades.customer.data.CustomerFeedBackData;
import com.ardctraining.facades.customer.impl.DefaultArdctrainingCustomerFacade;
import com.ardctraining.storefront.controllers.ControllerConstants;
import com.ardctraining.storefront.form.ArdctrainingCustomerFeedBackForm;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/feedback")
public class FeedbackPageController extends AbstractPageController {

    private static final String FEEDBACK_PAGE_LABEL = "feedback";
    private static final String FEEDBACK_FORM_ATTR = "feedbackForm";

    @Resource(name = "ardctrainingCustomerFacade")
    private DefaultArdctrainingCustomerFacade defaultArdctrainingCustomerFacade;

    @GetMapping
    public String getFeedBackView(final Model model) throws CMSItemNotFoundException{
        final ContentPageModel contentPageModel = getContentPageForLabelOrId(FEEDBACK_PAGE_LABEL);
        storeCmsPageInModel(model,contentPageModel);
        setUpMetaDataForContentPage(model,contentPageModel);
        List<CustomerFeedBackModel> feedback = defaultArdctrainingCustomerFacade.getCustomerFeedBack();

        model.addAttribute("feedback",feedback);
        model.addAttribute(FEEDBACK_FORM_ATTR);
        return ControllerConstants.Views.Pages.Feedback.FeedBackPage;
    }
    @PostMapping(value = "/save")
    public String submitFeedBack( ArdctrainingCustomerFeedBackForm feedbackform){
        CustomerFeedBackData data = new CustomerFeedBackData();

        data.setSubject(feedbackform.getSubject());
        data.setMessage(feedbackform.getMessage());

        defaultArdctrainingCustomerFacade.save(data);
        return "redirect:/feedback";
    }
}
