package sampleapp.com.spacestationfinder;

import java.util.List;

/**
 * Created by sukumar on 1/17/18.
 */

public class SpaceStationPassContract {

    interface View {
        public void setPresenter(Presenter presenter);

        public String getLatitude();

        public String getLongitude();

        public String getAltitude();

        public String getPassesNumber();

        public void setLatitude(double latitude);

        public void setLongitude(double longitude);

        public boolean getLocationCheckStatus();

        public void onLoadClick();

        public void showErrorMessage(String errorMessage);

        public void updateRecyclerView(List<PassesPojo.SinglePassPojo> passesList);
    }

    public interface Presenter {

        public void getIssPasses();
    }

}
