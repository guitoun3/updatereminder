package com.github.guitoun3.updatereminder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class RestClient {

    private static UpdateReminder.ApiMethods CLIENT = null;

    private RestClient() {}

    public static UpdateReminder.ApiMethods getInstance(String baseUrl) {

        if (CLIENT == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CLIENT = retrofit.create(UpdateReminder.ApiMethods.class);
        }

        return CLIENT;
    }
}
