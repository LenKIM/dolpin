package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.dto.model.Response;

public class NewCelebrityResponseSwagger extends Response<Boolean> {
    public NewCelebrityResponseSwagger(int code, String msg, Boolean data) {
        super(code, msg, data);
    }
}
