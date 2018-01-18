package sampleapp.com.spacestationfinder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class SpaceStationPassActivity extends AppCompatActivity implements SpaceStationPassContract.View, LocationListener {

    EditText mLatitudeText;

    EditText mLongitudeText;

    EditText mAltitudeText;

    EditText mPassesText;

    CheckBox mLocationCheck;

    Button mLoadButton;

    RecyclerView recyclerView;

    SpaceStationPassContract.Presenter presenter;

    IssPassesAdapter mAdapter;

    private LocationManager locationManager;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new SpaceStationPresenter(this);

        mContext = this;

        //TODO : check why butterknife is not working
        mLatitudeText = (EditText) findViewById(R.id.latitude_text);
        mLongitudeText = (EditText) findViewById(R.id.longitude_text);
        mAltitudeText = (EditText) findViewById(R.id.altitude_text);
        mPassesText = (EditText) findViewById(R.id.passes_number);
        mLocationCheck = (CheckBox) findViewById(R.id.device_location);
        mLoadButton = (Button) findViewById(R.id.load_button);

        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoadClick();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.iss_passes_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        setDefaultValuesOfViews();

        mLocationCheck.setOnClickListener(locationClickListner);
    }


    private void setDefaultValuesOfViews() {

        //By default points to NewYork location
        mLatitudeText.setText("40.71");
        mLongitudeText.setText("-74.00");
        mLocationCheck.setChecked(false);
    }

    View.OnClickListener locationClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                showMessage("Location Permission are not granted." +
                        "Please enable location permission in settings > apps");
                mLocationCheck.setChecked(false);
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ((LocationListener) mContext));

            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
            }

            try {
                network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
            }

            if (!gps_enabled && !network_enabled) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
            showMessage("Waiting on GPS location .........");
        }
    };

    @Override
    public void onLocationChanged(Location location) {
        if (mLocationCheck.isChecked()) {
            setLatitude(location.getLatitude());
            setLongitude(location.getLongitude());
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }


    @Override
    protected void onResume() {
        super.onResume();

        askForGrantingPermssions();

        onLoadClick();
    }

    private void askForGrantingPermssions() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ((LocationListener) mContext));
        } else {
            showMessage("Location Permission are not granted." +
                    "Please enable location permission in settings > apps");
        }
    }

    public String getLatitude() {
        return mLatitudeText.getText().toString();
    }

    public String getLongitude() {
        return mLongitudeText.getText().toString();
    }

    public String getAltitude() {
        return mAltitudeText.getText().toString();
    }

    public String getPassesNumber() {
        return mPassesText.getText().toString();
    }

    public void setLatitude(double latitude) {
        mLatitudeText.setText(String.valueOf(latitude));
    }

    public void setLongitude(double longitude) {
        mLongitudeText.setText(String.valueOf(longitude));
    }

    public boolean getLocationCheckStatus() {
        return mLocationCheck.isSelected();
    }

    public void onLoadClick() {
        presenter.getIssPasses();
    }

    public void setPresenter(SpaceStationPassContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void showErrorMessage(String errorMessage) {
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
    }

    public void showMessage(String errorMessage) {
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
    }

    public void updateRecyclerView(List<PassesPojo.SinglePassPojo> passesList) {
        if (recyclerView.getAdapter() == null) {
            mAdapter = new IssPassesAdapter(passesList);
            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setIssPasseslist(passesList);
        }
    }
}

