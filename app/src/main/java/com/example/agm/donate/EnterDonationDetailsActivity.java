package com.example.agm.donate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utils.AppSharedPreferences;
import com.utils.MyConstants;
import com.utils.ServiceHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EnterDonationDetailsActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    private RadioButton radioBreakfast,radioLunch,radioDinner;
    private EditText edtQuantity,edtAddress;
    private String foodType = "dinner",quantity = "",address = "",lat = "",lng = "",userid= "";
    //private HashMap<String ,String> postParams;
    private String requestParams;
    AppSharedPreferences appSharedPreferences;
    private FirebaseAuth mAuth;
    private DatabaseReference mCustomerDatabase;

    private String userID;

    Double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations_details_entry);
        //initView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appSharedPreferences = new AppSharedPreferences(getApplicationContext());
        userid = appSharedPreferences.getStringPreferences(MyConstants.PREF_KEY_ID);

        radioBreakfast = (RadioButton)findViewById(R.id.radio_breakfast);
        radioLunch = (RadioButton)findViewById(R.id.radio_lunch);
        radioDinner = (RadioButton)findViewById(R.id.radio_dinner);

        edtQuantity = (EditText)findViewById(R.id.edt_quantity);
        edtAddress = (EditText)findViewById(R.id.edt_address);

        mGoogleApiClient = new GoogleApiClient
                .Builder( this)
                .enableAutoManage( this, 0, this )
                .addApi( Places.GEO_DATA_API )
                .addApi( Places.PLACE_DETECTION_API )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .build();

        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.mipma);
        ab.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.txt_select_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPlacePicker();
            }
        });

        radioBreakfast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodType = "breakfast";
                }
            }
        });
        radioLunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    foodType = "lunch";
                }
            }
        });
        radioDinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodType = "dinner";
                }
            }
        });

        findViewById(R.id.btn_donate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDonation();
            }
        });

    }
    String user1;
    private void submitDonation() {
        quantity = edtQuantity.getText().toString().trim();
        address = edtAddress.getText().toString().trim();

        if (isValidationSuccess()){
            doSubmitDonationTask();
        }
    }

    private void doSubmitDonationTask() {
        /*postParams = new HashMap<>();
        postParams.put("userid",userid);
        postParams.put("foodtype",foodType);
        postParams.put("quantity",quantity);
        postParams.put("latitude",lat);
        postParams.put("longitude",lng);
        postParams.put("address", address);*/
        /*{ "donorMobile": "9944775657", "donationStatus": "open",
                "foodType":"lunch", "quantity":"10",
                "latitude":"102.30", "longitude":"233.dd", "address":"Some text" }*/
        mAuth = FirebaseAuth.getInstance();




        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
            String key=ref.push().getKey();
            //the push() command is already creating unique key
            UserA user2= new UserA();
            user2.setFoodtype(foodType);
            user2.setAdd(address);
            user2.setDonN("open");
            user2.setLat(lat);
            user2.setLon(lng);

            ref.child(key).setValue(user2);
            DatabaseReference ref2=FirebaseDatabase.getInstance().getReference().child("user1");
            DatabaseReference users=ref2.child("users");
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                        Map<String, Object> map1 = (Map<String, Object>) dataSnapshot.getValue();
                        if(map1.get("name")!=null){
                            user1 = map1.get("users").toString();

                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            ref2.addValueEventListener(listener);

            Map map = new HashMap();
            if(user1!=null){user1=user1+" "+key;}else {user1=key;}
            map.put("users",user1);
            ref2.updateChildren(map);

            /*
            Map<String, String> map = new HashMap<>();
            Log.w("myApp", String.valueOf(latitude));
            map.put("address", address);

            map.put("foodType", foodType);

            map.put("quantity", quantity);
            map.put("latitude", String.valueOf(latitude));
            Log.w("myApp", String.valueOf(latitude));
            map.put("longitude", String.valueOf(longitude));
            map.put("donationStatus", "open");
            ref.setValue(map);



            Log.e("Params","--->>> ");
            new doSubmitDonationAsyncTask().execute();
        */}
        /*JSONObject object = new JSONObject();
        try {
            //object.put("consumerName", appSharedPreferences.getStringPreferences(MyConstants.PREF_KEY_NAME));
            //object.put("isVolunteer", String.valueOf(appSharedPreferences.getStringPreferences(MyConstants.PREF_KEY_IS_VOLUNTEER)));
            //object.put("deviceId", String.valueOf(appSharedPreferences.getStringPreferences(MyConstants.PREF_KEY_DEVICE_ID)));
            object.put("address", address);
            object.put("donorMobile", appSharedPreferences.getStringPreferences(MyConstants.PREF_KEY_MOBILE));
            object.put("foodType", foodType);

            object.put("quantity", quantity);
            object.put("latitude", latitude);
            object.put("longitude", longitude);
            object.put("donationStatus", "open");
            //object.put("deviceToken", "TestDeviceToken");
            requestParams = object.toString();
            Log.e("Params","--->>> "+requestParams);
            new doSubmitDonationAsyncTask().execute();
        } catch (Exception ex) {
            displayToast(getString(R.string.unable_to_connect));
        }*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        if( mGoogleApiClient != null )
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            //mAdapter.setGoogleApiClient( null );
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    private void displayPlacePicker() {
        if( mGoogleApiClient == null || !mGoogleApiClient.isConnected() )
            return;

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult( builder.build(EnterDonationDetailsActivity.this), PLACE_PICKER_REQUEST );
           //startActivityForResult(,PLACE_PICKER_REQUEST);
            //startActivityForResult(getApplicationContext(this),PLACE_PICKER_REQUEST);
        } catch ( GooglePlayServicesRepairableException e) {
            Log.d("PlacesAPI Demo", "GooglePlayServicesRepairableException thrown");
        } catch ( GooglePlayServicesNotAvailableException e ) {
            Log.d( "PlacesAPI Demo", "GooglePlayServicesNotAvailableException thrown" );
        }
    }

    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        if( requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK ) {
            Place place =   PlacePicker.getPlace(data,this);
            latitude = place.getLatLng().latitude;
            longitude = place.getLatLng().longitude;
            displayPlace( PlacePicker.getPlace( data, this ) );
        }
    }

    private void displayPlace( Place place ) {
        if( place == null )
            return;

        //DisplayLog.displayNormalLog("MainActivity","Place",place);

        String content = "";
        //if( !TextUtils.isEmpty(place.getName()) ) {
        //    content += "Name: " + place.getName() + "\n";
        //}
        if( !TextUtils.isEmpty(place.getAddress()) ) {
            content += place.getAddress();
        }
        //if( !TextUtils.isEmpty( place.getPhoneNumber() ) ) {
        //    content += "Phone: " + place.getPhoneNumber();
        //}

        if( !TextUtils.isEmpty( String.valueOf(place.getLatLng()) ) ) {
            Log.e("MainActivity", "Latlong: "+String.valueOf(place.getLatLng()));
            LatLng mLatLng = place.getLatLng();
            //isplayLog.displayNormalLog("MainActivity", "Latlong Points", mLatLng.latitude+" - "+mLatLng.longitude);
            lat = String.valueOf(mLatLng.latitude);
            lng = String.valueOf(mLatLng.longitude);

        }
        Log.e("Main ac","address: "+content);

        if (content != null && !content.equals("")){
            edtAddress.setText(content);
        }else {
            edtAddress.setText("");
        }
        //mTextView.setText( content );
       // medtAddress.setText(content);
    }

    private boolean isValidationSuccess(){
        boolean isSuccess = true;
        if (quantity.equals("")){
            displayToast("Please enter the quantity");
            isSuccess = false;
        }else if (address.equals("") || address.length() < 5){
            displayToast("Please enter the correct address");
            isSuccess = false;
        }else if (lat.equals("") || lng.equals("")){
            displayToast("Please select the location");
            isSuccess = false;
        }
        return isSuccess;
    }

    private void displayToast(String toastMsg) {
        Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class doSubmitDonationAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(EnterDonationDetailsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler serviceHandler = new ServiceHandler();


            // Making a request to url and getting response
            //String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            String sUrl = MyConstants.URL_ROOT+"donate/create";

            String jsonStr = serviceHandler.performPostCall(sUrl, requestParams);

            Log.e("Response: ", "--->>> " + jsonStr);

            if (jsonStr != null) try {
                JSONObject jsonObj = new JSONObject(jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            Intent intent =new Intent(getApplicationContext(),ThankYou.class);
            startActivity(intent);
            intent.putExtra(MyConstants.FROM_ACTIVITY,MyConstants.KEY_DONOR);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            finish();
        }

    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
