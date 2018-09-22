package com.example.mokaposdemo.network;

import com.example.mokaposdemo.BuildConfig;
import com.example.mokaposdemo.models.Article;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import static com.example.mokaposdemo.utils.Utils.log;

public class RestClient {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static RestClient mRestClient;
    private final Dispatcher dispatcher;
    private final Retrofit retrofit;

    private RestClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.connectTimeout(30, TimeUnit.SECONDS);

        dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(1);
        builder.dispatcher(dispatcher);

        builder.retryOnConnectionFailure(true);
        builder.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();

                Request.Builder reqBuild = request.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json");
                request = reqBuild.build();

                return chain.proceed(request);
            }
        });

        if (BuildConfig.isPrintLogs) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.interceptors().add(interceptor);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RestClient with() {
        if (mRestClient == null) {
            mRestClient = new RestClient();
        }
        return mRestClient;
    }

    public void cancelAll() {
        log("cancelAll");
        dispatcher.cancelAll();
    }

    public ApiService getClient() {
        return retrofit.create(ApiService.class);
    }

    public interface ApiService {

        @GET("photos")
        Call<List<Article>> getItems();
    }
}

