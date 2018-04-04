package com.example.agm.donate;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class collect extends AppCompatActivity implements OnMapReadyCallback {
    TextView text;
    private GoogleMap mMap;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    private SupportMapFragment mapFragment;
    private DatabaseReference ref1;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        text=(TextView) findViewById(R.id.text);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    String users;
    String[] users1;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;

        ref1 = FirebaseDatabase.getInstance().getReference().child("user1");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                        Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                        if (map.get("users") != null) {
                            users = map.get("users").toString();
                            text.setText(users);
                            users1 = users.split("//s+");

                            for(String s:users1){
                                ref = FirebaseDatabase.getInstance().getReference().child("users");
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            UserA usser123 = postSnapshot.getValue(UserA.class);
                                            String add=usser123.getAdd();
                                            String lat=usser123.getLat();
                                            String lon=usser123.getLon();
                                            text.setText(lat+lon+add);
                                            LatLng latLng=new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
                                            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in loop"));
                                            System.out.print(add);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }

                }
    /* You could extract the values of object using the getter methods
     * and display it in your GUI.

     *  climate.getCity()
     *  climate.getTemperature()
     *  climate.getHumidity()
     *  climate.getPressure()
     **/
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
       /*
        * You may print the error message.
               **/
            }
        });

    }
}
