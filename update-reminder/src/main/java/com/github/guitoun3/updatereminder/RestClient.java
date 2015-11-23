package com.github.guitoun3.updatereminder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class RestClient {

    private static IApiMethods CLIENT = null;

    private RestClient() {}

    public static IApiMethods getInstance(String baseUrl) {

        if (CLIENT == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CLIENT = retrofit.create(IApiMethods.class);
        }

        return CLIENT;
    }
}
