package com.example.egemendurmus.a1clean.customer_adress;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.egemendurmus.a1clean.MainActivity;
import com.example.egemendurmus.a1clean.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    JSONObject jsonObj;
public static  String places,sokak,notes,adres_tipi,ticket,userid,memberid,responseServer;
    boolean saveadress,primaryadres,isactive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);



        setContentView(R.layout.new_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "opensans.ttf");//yazı fontu


        EditText street= (EditText) findViewById(R.id.editText11);
        EditText not= (EditText) findViewById(R.id.editText12);








        Button geri = (Button) findViewById(R.id.button11);
        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });





       final TextView t53= (TextView) findViewById(R.id.textView53);




        sokak=street.getText().toString();
        notes=not.getText().toString();


      final Spinner  spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.dizi,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String[] some_array = getResources().getStringArray(R.array.dizi);

                adres_tipi= spinner.getSelectedItem().toString();

                t53.setText(adres_tipi);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    //google dan adres alma


        final Button place= (Button) findViewById(R.id.button10);
        place.setText(places);
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // place.setText(places);
                Intent i =new Intent(MapsActivity.this,Main2Activity.class);
                startActivity(i);

                Log.i("yeniurl", MainActivity.active_ticket+" "+MainActivity.user_id);


            }
        });








        //switch adres primary mi değil mi kontrol

        final RelativeLayout adres_layout= (RelativeLayout) findViewById(R.id.afterswitch);
        adres_layout.setVisibility(View.GONE);




        Switch mySwitch = (Switch) findViewById(R.id.switch1);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                if(isChecked){
                    adres_layout.setVisibility(View.VISIBLE);
                    saveadress=false;


                }else{
                    adres_layout.setVisibility(View.GONE);
                    saveadress=true;
                }



            }
        });


        Switch primarySwitch = (Switch) findViewById(R.id.switch2);
        primarySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                if(isChecked){

                    primaryadres=true;


                }else{
                    primaryadres=false;
                }



            }
        });




        //save this adres işlemi

        Button save_adres= (Button) findViewById(R.id.button15);
        save_adres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new save_Adres().execute();



            }
        });




    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            if (mMap != null) {


                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                    @Override
                    public void onMyLocationChange(Location arg0) {
                        // TODO Auto-generated method stub

                        mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("Konumum"));


                        String lat= String.valueOf(arg0.getLatitude());

                        String lng= String.valueOf(arg0.getLongitude());

                        Log.i("konum",lat+" "+lng);


                                LatLng konum = new LatLng(arg0.getLatitude(),arg0.getLongitude());




                        mMap.moveCamera(CameraUpdateFactory.newLatLng(konum));

                       //http://maps.googleapis.com/maps/api/geocode/json?latlng=40.9794176,28.8569153&sensor=true



                    }
                });

            }
        } else {
            // Show rationale and request permission.
        }
        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }


    private class save_Adres extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {


            //  Log.e("response", "responsesverify -----" + ticket);



             jsonObj = new JSONObject();
            try {


                jsonObj.put("Ticket", MainActivity.active_ticket);

                jsonObj.put("UserID", MainActivity.mail);
                jsonObj.put("MemberId", MainActivity.member_id);
               // jsonObj.put("Explanation", notes);
               // jsonObj.put("Street", sokak);






                try {

                    if(Main2Activity.c_id!=null){
                        jsonObj.put("CountryId",Integer.parseInt( Main2Activity.c_id));
                    }


                    if(Main2Activity.city_id!=null){
                        jsonObj.put("CityId",Integer.parseInt( Main2Activity.city_id));

                    }

                    if(Main2Activity.DistrictID!=null){
                        jsonObj.put("DistrictID",Integer.parseInt( Main2Activity.DistrictID));

                    }else{
                        jsonObj.put("DistrictID",0);
                    }

                    if(Main2Activity.NeighborhoodID!=null){
                        jsonObj.put("NeighborhoodID",Integer.parseInt( Main2Activity.NeighborhoodID));

                    }else{
                        jsonObj.put("NeighborhoodID",0);

                    }                }
                catch (NumberFormatException e) {
                    // ...
                }


                jsonObj.put("IsActive",true);
                jsonObj.put("PrimaryAddress",primaryadres);
                jsonObj.put("DoNotSave",saveadress);
                jsonObj.put("AddressText",sokak);
                jsonObj.put("Explanation",notes);



                Log.i("denemesonuç", String.valueOf(jsonObj));



            } catch (JSONException e) {
                e.printStackTrace();
            }


            HttpClient httpClient = new DefaultHttpClient(); //Deprecated


            StringEntity params1 = null;
            try {
                    HttpPost request = new HttpPost("http://46.197.42.230:85/Location.svc/AddMemberAddress");
                params1 = new StringEntity(jsonObj.toString());
                request.addHeader("content-type", "application/x-www-form-urlencoded");
                request.setEntity(params1);
                HttpResponse response = httpClient.execute(request);
                InputStream inputStream = response.getEntity().getContent();
                InputStreamToStringExample str = new InputStreamToStringExample();
                responseServer = str.getStringFromInputStream(inputStream);
                Log.e("response", "responsesverify -----" + responseServer);





                // handle response here...
            } catch (Exception ex) {
                // handle exception here
            } finally {
                httpClient.getConnectionManager().shutdown(); //Deprecated
            }








            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);






        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);
        }
    }
    public static class InputStreamToStringExample {

        public static void main(String[] args) throws IOException {

            // intilize an InputStream
            InputStream is =
                    new ByteArrayInputStream("file content..blah blah".getBytes());

            String result = getStringFromInputStream(is);

            System.out.println(result);
            System.out.println("Done");


        }

        // convert InputStream to String
        private static String getStringFromInputStream(InputStream is) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }

    }
}
