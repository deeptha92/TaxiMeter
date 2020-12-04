package com.example.uber;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DriverHistory extends Activity {


    private static TextView mDriver_id_history, mtripdateandtime;
    private RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences mPrefs2 = getSharedPreferences("DriverTotalEarn",0);
        Integer str2 = mPrefs2.getInt("DriverTatalEarn", 0);

        SharedPreferences mPrefs = getSharedPreferences("IDvalue",0);
        Integer str = mPrefs.getInt("DriverIDValue", 0);

        setContentView(R.layout.popup_history);
        //mDatabase = FirebaseDatabase.getInstance().getReference("History");

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.history_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        firebaseUserSearch(String.valueOf(str));
    }

    private void firebaseUserSearch(String s) {

//        final Query firebaseSearchQuery = FirebaseDatabase.getInstance().getReference("History").child("1");
        final Query firebaseSearchQuery2 = FirebaseDatabase.getInstance().getReference("History").orderByChild("driverid").equalTo(s);


        FirebaseRecyclerAdapter<History, HistoryViewHolder> firebaseRecyclerAdapter2 = new FirebaseRecyclerAdapter<History, HistoryViewHolder>(
                History.class,
                R.layout.history_list_layout,
                HistoryViewHolder.class,
                firebaseSearchQuery2
        ) {
            @Override
            protected void populateViewHolder(HistoryViewHolder viewHolder, History model, int position) {
                //viewHolder.setDetails(String.valueOf(model.getDriverid()));
                viewHolder.setDetails(model.getDriverid(), model.getTripstartdate(), model.getStartaddress(), model.getEndaddress(), model.getTripid(), model.getTriptotalcost(), model.getTripcommission(), model.getTripstarttime(), model.getTripendtime());
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter2);

        Toast.makeText(DriverHistory.this, "Started setAdapter", Toast.LENGTH_LONG).show();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        @SuppressLint("SetTextI18n")
        public void setDetails(String driverid, String tripstartdate, String tripstartaddress, String tripendaddress, String tripid, String triptotalcost, String tripcommission, String tripstarttime, String tripendtime) {
            TextView user_name5 = (TextView) mView.findViewById(R.id.trip_date);
            user_name5.setText(tripstartdate);

            TextView user_name = (TextView) mView.findViewById(R.id.history_text);
            user_name.setText("From " + tripstartaddress + " To " + tripendaddress);

            TextView user_name2 = (TextView) mView.findViewById(R.id.history_time);
            user_name2.setText("( " + tripstarttime + " To " + tripendtime +" )");

            TextView user_name3 = (TextView) mView.findViewById(R.id.trip_id);
            user_name3.setText("Trip ID " + tripid);

            TextView user_name6 = (TextView) mView.findViewById(R.id.trip_total_cost);
            user_name6.setText("Rs " + triptotalcost);

            TextView user_name7 = (TextView) mView.findViewById(R.id.driver_id);
            user_name7.setText("Driver ID " + driverid);

        }
    }
}
