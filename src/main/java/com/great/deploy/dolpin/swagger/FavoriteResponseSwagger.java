package com.great.deploy.dolpin.swagger;

import com.great.deploy.dolpin.dto.FavoriteResponse;
import com.great.deploy.dolpin.dto.Response;
import io.swagger.annotations.ApiModel;

@ApiModel
public class FavoriteResponseSwagger extends Response<FavoriteResponse> {
    public FavoriteResponseSwagger(int code, String msg, FavoriteResponse data) {
        super(code, msg, data);
    }
}
