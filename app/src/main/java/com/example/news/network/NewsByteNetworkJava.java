package com.example.news.network;


import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class NewsByteNetworkJava {

    public static String BASE_URL = "http://api.mediastack.com/v1/";

    private static final String API_KEY = "60edb176f7611beaa6f74c76472203cd";





    private static Interceptor requestInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            HttpUrl url = chain.request().url().newBuilder().addQueryParameter("access_key", API_KEY).build();
            Request request = chain.request().newBuilder().url(url).build();
            return chain.proceed(request);
        }
    };


 private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build();



   public static Moshi moshi =  new Moshi.Builder().add( new KotlinJsonAdapterFactory())
            .build();
    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi));


    public static Retrofit retrofit = builder.build();

   public interface TaskService {
       @GET("news")
       Call<NetworkNewsContainer> getNews(@Query("access_key") String accessKey,   @Query("sources") String sources, @Query("languages") String language,
                                          @Query("keywords") String search, @Query("sort") String sort);
   }

      public static <S> S CreateService(Class<S> serviceClass){
            return retrofit.create(serviceClass);
        }

 }




