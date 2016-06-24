package com.example.egemendurmus.a1clean;

import android.os.AsyncTask;
import android.util.Log;

import com.example.egemendurmus.a1clean.supplier_list.slidemenus.Main3Activity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by egemen.durmus on 16/06/16.
 */
public class generalpost extends AsyncTask<Void, Void, String> {

    HttpPost request;
    public static String responseServer, url,ProductGroupID;
    JSONObject jb;
    public static JSONObject jsonObj;

    protected String doInBackground(Void... unused) { // Arka Planda yap�lacaklar. Yani Post i�lemi

        List<NameValuePair> params = new ArrayList<NameValuePair>(2); //Post edilecek de�i�kenleri ayarliyoruz.
        //Bu de�i�kenler bu uygulamada hi�bir i�e yaram�yor.Sadece g�stermek ama�l�
        //  params.add(new BasicNameValuePair("UserID", "admin"));
        //params.add(new BasicNameValuePair("Password", "4321"));


         jsonObj = new JSONObject();



        try {

            if(Main3Activity.pro_grup==true){

               int id=Integer.parseInt(ProductGroupID);

                jsonObj.put("ProductGroupID", id);

            }


            jsonObj.put("UserID", MainActivity.mail);
             jsonObj.put("Ticket", MainActivity.active_ticket);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("jsonobje", String.valueOf(jsonObj));

        Log.e("jsonobje", String.valueOf(url));


        params.add(new BasicNameValuePair("", jsonObj.toString()));

        HttpClient httpClient = new DefaultHttpClient(); //Deprecated


        StringEntity params1 = null;
        try {


            request = new HttpPost(url);


            params1 = new StringEntity(jsonObj.toString());
            request.addHeader("content-type", "application/x-www-form-urlencoded");
            request.setEntity(params1);
            HttpResponse response = httpClient.execute(request);
            InputStream inputStream = response.getEntity().getContent();
            InputStreamToStringExample str = new InputStreamToStringExample();
            responseServer = str.getStringFromInputStream(inputStream);
            Log.e("response", "genelpost" + responseServer);


            // handle response here...
        } catch (Exception ex) {
            // handle exception here
        } finally {
            httpClient.getConnectionManager().shutdown(); //Deprecated
        }


        //    veri_string = post.httpPost(url, "POST", params1, 20000); //PostClass daki httpPost metodunu �a��rd�k.Gelen string de�erini ald�k

        Log.d("para:", "" + params);// gelen veriyi log tuttuk

        return responseServer;
    }

    public void onPostExecute(String unused) { //Posttan sonra


        try {

            jb = new JSONObject(responseServer);

            String ticket = jb.getString("Ticket");
            Log.e("mesaj_genelpost", ticket);

            String messageData = jb.getString("Data");
            JSONObject InData = new JSONObject(messageData);


        } catch (JSONException e) {
            e.printStackTrace();
        }

/*
        Intent i = new Intent(MainActivity.this, customer_slide.class);
        startActivity(i);

        customer_slide.name = isim;
        customer_slide.surname = soyisim;
        customer_slide.image = profilepicture;
        Main2Activity.ticket = active_ticket;
        customer_slide.mail = mail;
        sakla("login_isim", isim);
        sakla("login_soyisim", isim);*/


        Log.e("mesajgenelpost", responseServer);






    }




}

class InputStreamToStringExample {

    public static void main(String[] args) throws IOException {

        // intilize an InputStream
        InputStream is =
                new ByteArrayInputStream("file content..blah blah".getBytes());

        String result = getStringFromInputStream(is);

        System.out.println(result);
        System.out.println("Done");

    }

    // convert InputStream to String
    public static String getStringFromInputStream(InputStream is) {

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

