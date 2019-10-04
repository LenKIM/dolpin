package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.dto.NewCelebrityResponse;
import com.great.deploy.dolpin.dto.model.Response;

public class NewCelebrityResponseSwagger extends Response<NewCelebrityResponse> {
    public NewCelebrityResponseSwagger(int code, String msg, NewCelebrityResponse data) {
        super(code, msg, data);
    }
}
