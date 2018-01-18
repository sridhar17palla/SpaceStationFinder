package sampleapp.com.spacestationfinder;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by sukumar on 1/18/18.
 */

public class IssPassesAdapter extends RecyclerView.Adapter<IssPassesAdapter.IssPassesViewHolder> {


    private List<PassesPojo.SinglePassPojo> passesList;

    public class IssPassesViewHolder extends RecyclerView.ViewHolder {
        public TextView time, duration;

        public IssPassesViewHolder(View view) {
            super(view);
            time = (TextView) view.findViewById(R.id.timestamp_view);
            duration = (TextView) view.findViewById(R.id.duration_view);
        }
    }


    public IssPassesAdapter(List<PassesPojo.SinglePassPojo> passesList) {
        this.passesList = passesList;
    }

    public void setIssPasseslist(List<PassesPojo.SinglePassPojo> passesList){
        this.passesList = passesList;
        notifyDataSetChanged();
    }

    @Override
    public IssPassesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.iss_passes_list_row, parent, false);

        return new IssPassesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IssPassesViewHolder holder, int position) {
        PassesPojo.SinglePassPojo passesPojo = passesList.get(position);

        //TODO: Format time.
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(passesPojo.getRisetime()) * 1000L);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
        holder.time.setText(date);
        holder.duration.setText(passesPojo.getDuration());
    }

    @Override
    public int getItemCount() {
        return passesList.size();
    }
}
