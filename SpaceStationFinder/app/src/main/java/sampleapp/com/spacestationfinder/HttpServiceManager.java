package sampleapp.com.spacestationfinder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sukumar on 1/18/18.
 */

public class HttpServiceManager {

    public static final String BASE_URL = "http://api.open-notify.org/";

    public static final int DEFAULT_TIMEOUT = 15;

    private static Retrofit retrofit = null;

    private static HttpServiceApi httpServiceApi = null;

    public static HttpServiceApi getInstance() {
        if (retrofit == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.cache(null);

            retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            httpServiceApi = retrofit.create(HttpServiceApi.class);
        }
        return httpServiceApi;
    }
}
