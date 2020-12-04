package com.example.uber;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private static MainActivity mInstance;
    private Button mDriver, mLogout;
    public Switch mAvailability;
    public EditText mCus_email, mCus_mobile;
    SharedPreferences sp;
    private DrawerLayout drawer;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mInstance = this;



//
//        if (!isConnect()){
//            new AlertDialog.Builder(this)
//                    .setTitle("Internet Connection alert")
//                    .setMessage("Please Check your internet connection")
//                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                        }
//                    })
//                    .show();
//        }else{
//            Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
//        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        mDriver = (Button) findViewById(R.id.driver);
        mLogout = (Button) findViewById(R.id.logout);
        mAvailability = (Switch) findViewById(R.id.switch1);
        mCus_email = (EditText) findViewById(R.id.cus_email);
        mCus_mobile = (EditText) findViewById(R.id.cus_mobile);

        mCus_mobile.setVisibility(View.INVISIBLE);
        mCus_email.setVisibility(View.INVISIBLE);
        mDriver.setEnabled(false);

//        mCus_phone_no = (EditText) findViewById(R.id.cus_mobile);
//        mCus_email = (EditText) findViewById(R.id.cus_email);

        SharedPreferences mPrefs = getSharedPreferences("IDvalue",0);
        Integer str = mPrefs.getInt("DriverIDValue", 0);
        String drivername = mPrefs.getString("drivername", "123");
        String drivermonile = mPrefs.getString("drivermobile", "456");
        final TextView driver_id = (TextView) findViewById(R.id.driver_id_main);
        final TextView driver_name = (TextView) findViewById(R.id.driver_name);
        final TextView driver_mobile = (TextView) findViewById(R.id.driver_mobile);

        driver_id.setText(""+ str);
        driver_name.setText(""+ drivername);
        driver_mobile.setText(""+ drivermonile);


        startForegroundService();
        mDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkConnection();

                //Check Location Permission already granted or not
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Location permission is already granted", Toast.LENGTH_SHORT).show();


                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert
                            .setTitle("NEXTAXI")
                            .setMessage("Are you Sure you want to start a trip?")
                            .setIcon(android.R.drawable.ic_dialog_alert)

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intent = new Intent(MainActivity.this, DriverMapActivity.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();

                } else {
                    // Request Location Permission
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                }


                EditText mCus_phone = (EditText) findViewById(R.id.cus_mobile);
                EditText mCus_email = (EditText) findViewById(R.id.cus_email);

                String cus_phone = mCus_phone.getText().toString().trim();
                String cus_email = mCus_email.getText().toString().trim();


                sp = getSharedPreferences("Customervalue", 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("CustomermobileValue", cus_phone);
                editor.putString("CustomeremailValue", cus_email);
                editor.commit();

            }
        });


        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alert2 = new AlertDialog.Builder(MainActivity.this);
                alert2
                        .setTitle("NEXTAXI")
                        .setMessage("Are you sure you want to LOGOUT?")
                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                SharedPreferences sp=getSharedPreferences("IDvalue",MODE_PRIVATE);
                                SharedPreferences.Editor e=sp.edit();
                                e.clear();
                                e.commit();

                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(MainActivity.this, DriverLoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();

            }
        });

        mAvailability.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if(mAvailability.isChecked())
                {
                    mDriver.setEnabled(true);
                    mLogout.setEnabled(false);
                    mCus_email.setVisibility(View.VISIBLE);
                    mCus_mobile.setVisibility(View.VISIBLE);
                }
                else {
                    mDriver.setEnabled(false);
                    mLogout.setEnabled(true);
                    mCus_email.setVisibility(View.INVISIBLE);
                    mCus_mobile.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private void startForegroundService() {

        startService (new Intent(MainActivity.this, onAppKilled.class));
    }

//    private boolean isConnect(){
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        return networkInfo != null && networkInfo.isConnected();
//    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:

                // Check Location permission is granted or not
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Location  permission granted", Toast.LENGTH_SHORT).show();


                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert
                            .setTitle("NEXTAXI")
                            .setMessage("Are you Sure you want to start a trip?")
                            .setIcon(android.R.drawable.ic_dialog_alert)

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intent = new Intent(MainActivity.this, DriverMapActivity.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();

                } else {
                    Toast.makeText(MainActivity.this, "Location  permission denied", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_logout:
                final AlertDialog.Builder alert2 = new AlertDialog.Builder(MainActivity.this);
                alert2
                        .setTitle("NEXTAXI")
                        .setMessage("Are you sure you want to LOGOUT?")
                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                SharedPreferences sp=getSharedPreferences("IDvalue",MODE_PRIVATE);
                                SharedPreferences.Editor e=sp.edit();
                                e.clear();
                                e.commit();

                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(MainActivity.this, DriverLoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
                break;
            case R.id.nav_history:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoryFragment()).commit();
                Intent intent = new Intent(MainActivity.this, DriverHistory.class);
                startActivity(intent);
                break;
//            case R.id.nav_settings:
//                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoryFragment()).commit();
//                Intent intent2 = new Intent(MainActivity.this, DriverVehicle.class);
//                startActivity(intent2);
//                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "onResume");
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }
}
