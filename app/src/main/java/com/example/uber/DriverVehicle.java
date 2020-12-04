
package com.example.uber;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverVehicle extends Activity {
    private RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView2;
    LinearLayoutManager mLayoutManager2;
    public static TextView driver_id1;
    public static TextView vehicle_id1;
    private static TextView mShowName;
    private static TextView mVehicleName;
    Object key;
    List<TextView> allEds = new ArrayList<TextView>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences mPrefs = getSharedPreferences("IDvalue",0);
        Integer str = mPrefs.getInt("DriverIDValue", 0);

        setContentView(R.layout.vehicle_show_tab);
        //mDatabase = FirebaseDatabase.getInstance().getReference("History");

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.vehicle_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mShowName = (TextView) findViewById(R.id.show_name);
//        mVehicleName = (TextView) findViewById(R.id.vehicleid);
    }

    ValueEventListener MembersRef = FirebaseDatabase.getInstance()
            .getReference("vehiclefordrivers")
            .orderByChild("driverid")
            .equalTo("10")
            .addValueEventListener(
                    new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            for (DataSnapshot child : dataSnapshot.getChildren())
                            {
                                Map<String, Object> valuesMap = (HashMap<String, Object>) child.getValue();

                                // Get push id value.
                                key = valuesMap.get("vehicleid");
                                firebaseUserSearch2();

                                // HERE WHAT CORRESPONDS TO JOIN

                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    }
            );

    private void firebaseUserSearch2() {
        Toast.makeText(DriverVehicle.this, "Started Search", Toast.LENGTH_LONG).show();

        //final Query firebaseSearchQuery = FirebaseDatabase.getInstance().getReference("drivers").orderByChild("mobile").equalTo(searchText);

        final Query firebaseSearchQuery4 = FirebaseDatabase.getInstance().getReference("vehicle")
                .child((String) key);

        FirebaseRecyclerAdapter<Vehicle, DriversViewHolder> firebaseRecyclerAdapter4 = new FirebaseRecyclerAdapter<Vehicle, DriverVehicle.DriversViewHolder>(
                Vehicle.class,
                R.layout.vehicle_select_tab,
                DriversViewHolder.class,
                firebaseSearchQuery4

        ) {
            @Override
            protected void populateViewHolder(DriversViewHolder viewHolder, Vehicle model, int position) {
                viewHolder.setDetails(model.getName2());

            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter4);
    }


    public static class DriversViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public DriversViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        private void setDetails(String userName) {
            TextView drivernameofthevehicle = (TextView) mView.findViewById(R.id.driveridselect);
            //user_name.setText("Enter Password for " + userName);

            drivernameofthevehicle.setText(userName);
        }
    }



}


