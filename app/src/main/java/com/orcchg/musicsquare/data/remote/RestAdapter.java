package com.orcchg.musicsquare.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orcchg.musicsquare.data.model.Musician;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Node class for API requests.
 */
public interface RestAdapter {

    String ENDPOINT = "http://download.cdn.yandex.net/";

    public static class Creator {
        public static RestAdapter create() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RestAdapter.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(RestAdapter.class);
        }
    }

    @GET("/mobilization-2016/{path}")
    Observable<List<Musician>> getMusicians(@Path("path") String path);
}
