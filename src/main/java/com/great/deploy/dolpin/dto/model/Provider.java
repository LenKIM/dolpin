package com.great.deploy.dolpin.dto.model;

import java.util.Arrays;

public enum Provider {



    FACEBOOK, TWITTER, SYSTEM, NONE;

    public static boolean isContain(Provider value) {
        return Arrays.asList(Provider.values()).contains(value);
    }
}
