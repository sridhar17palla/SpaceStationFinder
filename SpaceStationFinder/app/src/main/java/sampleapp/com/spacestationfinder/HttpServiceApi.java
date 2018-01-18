package sampleapp.com.spacestationfinder;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sukumar on 1/18/18.
 */


public interface HttpServiceApi {

    @GET("iss-pass.json?")
    Call<PassesPojo> getISSPasses(@Query("lat") String latitude,
                                  @Query("lon") String longitude,
                                  @Query("alt") String altitude,
                                  @Query("n") String passNumber);


}
