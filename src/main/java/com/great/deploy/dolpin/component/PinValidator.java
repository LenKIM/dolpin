package com.great.deploy.dolpin.component;

import com.great.deploy.dolpin.dto.PinRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PinValidator {

    public void validate(PinRequest createPinRequest, Errors errors){
        if (createPinRequest.getEndDate().isBefore(createPinRequest.getStartDate())){
            errors.rejectValue("endDate", "wrongValue", "need to duble check");

        }
    }
}
