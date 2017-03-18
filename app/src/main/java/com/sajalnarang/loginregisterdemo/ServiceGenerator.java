package com.sajalnarang.loginregisterdemo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sajalnarang on 14/3/17.
 */

public class ServiceGenerator {
    private static final String BASE_URL = "http://www.findaloo.org";
    private static OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit;
    public static <S> S createService(Class<S> serviceClass) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        retrofit = retrofitBuilder.client(clientBuilder.addInterceptor(httpLoggingInterceptor).build()).build();
        return retrofit.create(serviceClass);
    }
}
