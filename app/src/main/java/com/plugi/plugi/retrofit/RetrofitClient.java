package com.plugi.plugi.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plugi.plugi.models.User;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitClient {


     static Retrofit retrofit;
    public static final String BASE_URL            = "http://plugi.mawaqaademos.com/api/Plugi/";
    public static final String BEARERTOKEN            = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6ImVtYWRAbWF3YXNxYWEuY29tIiwibmJmIjoxNjAyMzQyNDcyLCJleHAiOjE2MDI5NDcyNzIsImlhdCI6MTYwMjM0MjQ3MiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo1NDg4My8iLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjU0ODgzLyJ9.NXCKLavK1V16Jf333TMp_34orbHtuCZiUC9-geHuyms";
    public static Retrofit getRetrofitInstance(){

        if(retrofit == null){


            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create()) //important
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
    public static Retrofit retrofitWrite()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public static Retrofit retrofitBearerTokenWrite(User user)
    {
        /*
        TokenInterceptor tokenInterceptor = new TokenInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor).build();

         */
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor((okhttp3.Interceptor) chain -> {
            //rewrite the request to add bearer token
            okhttp3.Request newRequest=chain.request().newBuilder()
                    .header("Authorization","Bearer "+ user.getToken())
                    .build();

            return chain.proceed(newRequest);
        });
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public static Retrofit retrofitRead()
    {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    private static class TokenInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            //rewrite the request to add bearer token
            Request newRequest=chain.request().newBuilder()
                    .header("Authorization","Bearer "+ BEARERTOKEN)
                    .build();

            return chain.proceed(newRequest);
        }
    }
}
