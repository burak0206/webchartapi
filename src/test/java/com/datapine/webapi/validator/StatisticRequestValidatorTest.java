package com.datapine.webapi.validator;


import com.datapine.webapi.model.StatisticRequestModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticRequestValidatorTest {

    @InjectMocks
    StatisticRequestValidator validator;

    @Mock
    private BindingResult bindingResult;

    @Test
    public void shouldBeFailWhenTimeUnitIsNull(){
        StatisticRequestModel model = new StatisticRequestModel();
        validator.validate(model, bindingResult);
        verify(bindingResult).rejectValue(anyString(),anyString());

    }

    @Test
    public void shouldBeFailWhenTimeUnitIsNotMinutesOrSeconds(){
        StatisticRequestModel model = new StatisticRequestModel();
        model.setTimeUnit("different");
        validator.validate(model, bindingResult);
        verify(bindingResult).rejectValue(anyString(),anyString());

    }

    @Test
    public void shouldBeOkWhenTimeUnitIsMinutes(){
        StatisticRequestModel model = new StatisticRequestModel();
        model.setTimeUnit("minutes");
        validator.validate(model, bindingResult);
        assertFalse(bindingResult.hasErrors());

    }



}