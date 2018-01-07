package com.datapine.webapi.validator;

import com.datapine.webapi.model.StatisticRequestModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by burakdagli on 6.01.2018.
 */
@Component
public class StatisticRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return StatisticRequestModel.class.isAssignableFrom(aClass);
    }



    @Override
    public void validate(Object o, Errors errors) {
        StatisticRequestModel model= (StatisticRequestModel) o;
        if(model.getTimeUnit() != null && !(model.getTimeUnit().equals("seconds") || model.getTimeUnit().equals("minutes")) )
        {
            errors.rejectValue("timeUnit","it should be minutes or seconds");
        }
    }
}
