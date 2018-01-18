package sampleapp.com.spacestationfinder;

/**
 * Created by sukumar on 1/18/18.
 */

public class PassesPojo {

    private String message;


    private SinglePassPojo[] response;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SinglePassPojo[] getPassesList() {
        return response;
    }

    public class SinglePassPojo {

        private String duration;

        private String risetime;

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getRisetime() {
            return risetime;
        }

        public void setRisetime(String risetime) {
            this.risetime = risetime;
        }
    }

}
