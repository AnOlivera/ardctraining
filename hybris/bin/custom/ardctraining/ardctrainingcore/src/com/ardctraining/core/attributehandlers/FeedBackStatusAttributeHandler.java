package com.ardctraining.core.attributehandlers;

import com.ardctraining.core.enums.FeedbackStatusEnum;
import com.ardctraining.core.model.CustomerFeedBackModel;
import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;
import de.hybris.platform.servicelayer.time.TimeService;

import java.util.Calendar;
import java.util.Date;

public class FeedBackStatusAttributeHandler implements DynamicAttributeHandler<FeedbackStatusEnum,CustomerFeedBackModel> {

    private TimeService timeService;

    @Override
    public FeedbackStatusEnum get(final CustomerFeedBackModel model) {
        Calendar calendar = Calendar.getInstance();
        Date dueDate;
        Date now = timeService.getCurrentTime();
        calendar.setTime(now);

        int days = model.getDays();

        calendar.add(Calendar.DAY_OF_YEAR,days);
        dueDate = calendar.getTime();


        if(dueDate.before(now)){
            if(model.getRead() == 1){
               return FeedbackStatusEnum.READ_PASTDUE;
            }else{
               return FeedbackStatusEnum.PASTDUE;
            }
        }
        else if(dueDate.after(now)){
            if(model.getRead() == 1) {
                return FeedbackStatusEnum.READ;
            }
        }
        return FeedbackStatusEnum.NOT_READ;
    }

    @Override
    public void set(final CustomerFeedBackModel model, FeedbackStatusEnum feedbackStatusEnum) {
        model.setStatus(feedbackStatusEnum);
    }

    public TimeService getTimeService() {
        return timeService;
    }

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }


}
