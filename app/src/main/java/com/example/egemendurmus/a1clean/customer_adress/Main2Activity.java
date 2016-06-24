package com.example.egemendurmus.a1clean.customer_adress;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.egemendurmus.a1clean.MainActivity;
import com.example.egemendurmus.a1clean.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class Main2Activity extends Activity implements OnItemClickListener {

    private static final String LOG_TAG = "Google Places Autocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyAtMgyEOkwJqZGM7xyN42Ov6Fu5nnv2JdM";
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    ArrayList place;
    public static String placeid, ülke, şehir, ilçe, mahalle, responseServer, ticket, user_id, c_id, city_id, DistrictID, NeighborhoodID;
    JSONArray adm_area;

    JSONObject jsonObj = new JSONObject();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);


        setContentView(R.layout.otolayout);


        AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.autocompletetext, R.id.text));
        autoCompView.setOnItemClickListener(this);

        Log.e("Country", ticket + " " + MainActivity.user_id);

        Button geri = (Button) findViewById(R.id.button11);
        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this, MapsActivity.class);
                startActivity(i);


            }
        });


    }

    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);
        //  Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        MapsActivity.places = str;


        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(str,
                getApplicationContext(), new GeocoderHandler());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String url_str = String.valueOf(place.get(position));


        Toast.makeText(this, url_str, Toast.LENGTH_SHORT).show();

        System.out.println("egemen" + url_str);
        https:
//maps.googleapis.com/maps/api/place/details/json?input=bar&placeid=ChIJ-epeupm5yhQRyZu38FE8U2Q&key=AIzaSyAtMgyEOkwJqZGM7xyN42Ov6Fu5nnv2JdM

        getJSONFromUrl("https://maps.googleapis.com/maps/api/place/details/json?&placeid=" + url_str + "&key=AIzaSyAtMgyEOkwJqZGM7xyN42Ov6Fu5nnv2JdM");

        Log.i("yeniurl", "https://maps.googleapis.com/maps/api/place/details/json?&placeid=" + url_str + "&key=AIzaSyAtMgyEOkwJqZGM7xyN42Ov6Fu5nnv2JdM");


    }

    public ArrayList autocomplete(String input) {
        ArrayList resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            // sb.append("&components=country:tr");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            Log.e("deneme", String.valueOf(url));


            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e("deneme", "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e("deneme", "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());


            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");


            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            place = new ArrayList(predsJsonArray.length());

            for (int i = 0; i < predsJsonArray.length(); i++) {

                //https://maps.googleapis.com/maps/api/place/details/json?input=bar&placeid=ChIJyS6FdmW3yhQREOoLHOrasR8&key=AIzaSyAtMgyEOkwJqZGM7xyN42Ov6Fu5nnv2JdM


                System.out.println(jsonObj);
                System.out.println("============================================================");

                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));

                //   placeid=predsJsonArray.getJSONObject(i).getString("place_id");
                System.out.println("============================================================");

                place.add(predsJsonArray.getJSONObject(i).getString("place_id"));
                System.out.println(place);


                //    Log.i("yeniurl","https://maps.googleapis.com/maps/api/place/details/json?&placeid="+placeid+"&key=AIzaSyAtMgyEOkwJqZGM7xyN42Ov6Fu5nnv2JdM");

                System.out.println(resultList);
                //aldığımız kodları parsegoogle servisine gönderiyoruz


            }


        } catch (JSONException e) {
            Log.e("deneme", "Cannot process JSON results", e);
        }


        return resultList;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId, int text) {
            super(context, textViewResourceId, text);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return String.valueOf(resultList.get(index));
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public JSONObject getJSONFromUrl(String url) {

        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, HTTP.UTF_8), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);


            try {
                new String(jObj.getString("result").getBytes("ISO-8859-1"), "UTF-8");

                Log.i("egejson", new String(jObj.getString("result").getBytes("ISO-8859-1"), "UTF-8"));

                JSONObject jo = jObj.getJSONObject("result");

                JSONArray adres_comp = jo.getJSONArray("address_components");
                for (int i = 0; i < adres_comp.length(); i++) {
                    String long_name = adres_comp.getJSONObject(i).getString("long_name");
                    String short_name = adres_comp.getJSONObject(i).getString("short_name");


                    adm_area = adres_comp.getJSONObject(i).getJSONArray("types");

                    Log.e("JSON Parser", "area1 " + adm_area);


                    for (int j = 0; j < adm_area.length(); j++) {
                        String street = adm_area.getString(j);

                        if (street.equals("administrative_area_level_1")) {
                            Log.e("JSON Parser", "area1 " + long_name);

                            // Locale enLocale = Locale.forLanguageTag("en_US");
                            String x = long_name.toLowerCase();
                            //   String y=x.toUpperCase(enLocale);


                            şehir = x;


                        }

                        if (street.equals("administrative_area_level_2")) {


                            //   Locale enLocale = Locale.forLanguageTag("en_US");
                            String x = long_name.toLowerCase();
                            // String y=x.toUpperCase(enLocale);

                            ilçe = x;
                            Log.e("JSON Parser", "area2 " + ilçe);


                        }

                        if (street.equals("administrative_area_level_4")) {


                            //  Locale enLocale = Locale.forLanguageTag("en_US");
                            String x = long_name.toLowerCase();
                            // String y=x.toLowerCase(enLocale);




                         /*   int y_pos=y.indexOf("mh.");




                            Log.e("JSON Parser", "area4 " + y.substring(0,y_pos));*/

                            mahalle = x;
                        }

                        if (street.equals("country")) {
                            Log.e("JSON Parser", "short " + short_name);
                            ülke = short_name;
                        }


                    }


                    Log.e("JSON Parser", "area3 " + şehir + ülke + ilçe + mahalle);

                    new parsegoogleaddres().execute();


                }


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }


    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            Log.i("denemejson", locationAddress);
        }
    }

    private class parsegoogleaddres extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {


            try {
                jsonObj.put("Ticket", MainActivity.active_ticket);
                jsonObj.put("City", şehir);

                jsonObj.put("UserID", MainActivity.mail);
                jsonObj.put("District", ilçe);
                jsonObj.put("Country", ülke);
                jsonObj.put("Neighborhood", mahalle);
                Log.e("response", "jsonobj -----" + jsonObj);


                HttpClient httpClient = new DefaultHttpClient(); //Deprecated


                StringEntity params1 = null;
                try {
                    HttpPost request = new HttpPost("http://46.197.42.230:85/Location.svc/ParseGoogleAddress");
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


                Log.e("Country", MainActivity.active_ticket + " " + MainActivity.user_id);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }


        protected void onPostExecute(Void unused) { //Posttan sonra


            try {

                JSONObject jb = new JSONObject(responseServer);

                String ticket = jb.getString("Ticket");
                Log.e("mesaj", ticket);

                String messageData = jb.getString("Data");
                Log.e("mesaj", messageData);
                JSONObject InData = new JSONObject(messageData);
                c_id = InData.getString("CountryID");
                ;
                city_id = InData.getString("CityID");
                DistrictID = InData.getString("CityID");
                NeighborhoodID = InData.getString("NeighborhoodID");


                Log.d("sonuç", String.valueOf(jb));


            } catch (JSONException e) {
                e.printStackTrace();
            }


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

