 package com.example.egemendurmus.a1clean.supplier_list;

 import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
 import android.text.Editable;
 import android.text.TextWatcher;
 import android.util.Log;
import android.widget.ExpandableListView;

import com.example.egemendurmus.a1clean.MainActivity;
import com.example.egemendurmus.a1clean.R;
import com.example.egemendurmus.a1clean.slidemenu.order;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class expand_list extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    static List<Data> listDataHeader = new ArrayList<>();
    static List<String> listDataHeader_copy = new ArrayList<String>();
    HttpPost request;
    static ArrayList<Data> mDataSource = new ArrayList<Data>();

    Fragment fragment = null;
    HashMap<Data, List<String>> listDataChild = new HashMap<Data, List<String>>();

    public static String responseServer;
    JSONObject jb;


    static String Title, rating, properties_array, workhours_array,distance,price,organic;
    boolean listViewPopulated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supllier_expand);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        new Post().execute();




    }



    class Post extends AsyncTask<Void, Void, Void> {


        protected void onPreExecute() { // Post tan �nce yap�lacak i�lemler. Y�kleniyor yaz�s�n�(ProgressDialog) g�sterdik.


        }

        protected Void doInBackground(Void... unused) { // Arka Planda yap�lacaklar. Yani Post i�lemi

            List<NameValuePair> params = new ArrayList<NameValuePair>(2); //Post edilecek de�i�kenleri ayarliyoruz.
            //Bu de�i�kenler bu uygulamada hi�bir i�e yaram�yor.Sadece g�stermek ama�l�
            //  params.add(new BasicNameValuePair("UserID", "admin"));
            //params.add(new BasicNameValuePair("Password", "4321"));


            JSONObject jsonObj = new JSONObject();
            try {

                jsonObj.put("Ticket", MainActivity.active_ticket);


                jsonObj.put("UserID", MainActivity.mail);
                jsonObj.put("NeighborhoodID", 20894);
                jsonObj.put("MemberAddressID", 249);


                Log.e("response", "userid -----" + MainActivity.mail);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            params.add(new BasicNameValuePair("", jsonObj.toString()));

            HttpClient httpClient = new DefaultHttpClient(); //Deprecated


            StringEntity params1 = null;
            try {
                request = new HttpPost("http://46.197.42.230:85/Supplier.svc/GetSupplierListByNeighborhood");


                params1 = new StringEntity(jsonObj.toString());
                request.addHeader("content-type", "application/x-www-form-urlencoded");
                request.setEntity(params1);
                HttpResponse response = httpClient.execute(request);
                InputStream inputStream = response.getEntity().getContent();
                InputStreamToStringExample str = new InputStreamToStringExample();
                responseServer = str.getStringFromInputStream(inputStream);
                Log.e("response", "responses -----" + responseServer);


                // handle response here...
            } catch (Exception ex) {
                // handle exception here
            } finally {
                httpClient.getConnectionManager().shutdown(); //Deprecated
            }



            Log.d("para:", "" + params);// gelen veriyi log tuttuk

            return null;
        }

        protected void onPostExecute(Void unused) { //Posttan sonra
            // get the listview

            fragment = new order();

            expListView = (ExpandableListView) findViewById(R.id.lvExp);


            listAdapter = new ExpandableListAdapter(getApplication(), mDataSource, listDataChild);

            // setting list adapter
            expListView.setAdapter(listAdapter);


            suplier_list.edit_ara.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    listAdapter.getFilter().filter(s.toString());



                }
            });




            JSONArray jsonarray = null;
            try {
                JSONObject job = new JSONObject(responseServer);

                System.out.println("dene" + job.getString("Data"));


                String data = job.getString("Data");


                jsonarray = new JSONArray(data);


                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    Title = jsonobject.getString("Title");
                    System.out.println("dene" + Title);
                    rating = jsonobject.getString("AverageRating");
                    System.out.println(rating);
                    properties_array = jsonobject.getString("Properties");
                    System.out.println(properties_array);


                    workhours_array = jsonobject.getString("WorkHours");
                    distance=jsonobject.getString("Distance");
                    price=jsonobject.getString("MinimumDeliveryPrice");




                    System.out.println(workhours_array);
                    System.out.println(distance);
                    System.out.println(price);








                    ArrayList<String> addList = new ArrayList<String>();
                    String x = null;


                    mDataSource.add(new Data(Title,distance,price,rating,workhours_array, addList));




                    List<String> top250 = new ArrayList<String>();
                    top250.add("The Shawshank Redemption");


                    listDataChild.put(mDataSource.get(i), top250); // Header, Child data

                }


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