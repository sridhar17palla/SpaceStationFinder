package sampleapp.com.spacestationfinder;

import android.util.Log;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sukumar on 1/17/18.
 */

public class SpaceStationPresenter implements SpaceStationPassContract.Presenter {

    public static final String TAG = SpaceStationPresenter.class.toString();

    HttpServiceApi mAPIService = null;

    SpaceStationPassContract.View view;

    public SpaceStationPresenter(SpaceStationPassContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        mAPIService = HttpServiceManager.getInstance();
    }


    public void getIssPasses() {
        String latitude = view.getLatitude();
        String longitude = view.getLongitude();
        String altitude = view.getAltitude();
        String passesNumber = view.getPassesNumber();

        if (latitude == null || latitude.isEmpty()) {

            //TODO: Need context here to fetch from resources.
            view.showErrorMessage("Latitude is Null");
            return;
        }

        if (longitude == null || longitude.isEmpty()) {
            //TODO: Need context here to fetch from resources.
            view.showErrorMessage("Longitude is Null");
            return;
        }


        if (altitude == null || altitude.isEmpty()) {
            altitude = "1"; //Assuming default value
        }

        if (passesNumber == null || passesNumber.isEmpty()) {
            passesNumber= "5"; //Assuming default value
        }

        mAPIService.getISSPasses(latitude, longitude, altitude, passesNumber).enqueue(new Callback<PassesPojo>() {
            @Override
            public void onResponse(Call<PassesPojo> call, Response<PassesPojo> response) {

                if (response.isSuccessful()) {
                    view.updateRecyclerView(Arrays.asList(response.body().getPassesList()));
                    Log.i(TAG, "get submitted to API." + response.body().toString());
                }
                view.showErrorMessage(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<PassesPojo> call, Throwable t) {
                Log.e(TAG, "Unable to submit get to API.");
                view.showErrorMessage("onFailure " + t.getMessage());
            }
        });
    }
}
