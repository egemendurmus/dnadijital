package com.example.egemendurmus.a1clean.supplier_list;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.egemendurmus.a1clean.R;
import com.example.egemendurmus.a1clean.supplier_list.slidemenus.Main3Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class suplier_info extends AppCompatActivity {
    static String Title, rating, properties_array, workhours_array,distance,price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suplier_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "opensans.ttf");//yazı fontu


        TextView title= (TextView) findViewById(R.id.textView19);
        title.setText(Title);

        TextView dist= (TextView) findViewById(R.id.textView20);
        dist.setText("distance within "+distance);

        TextView days= (TextView) findViewById(R.id.textView26);
        TextView saturday= (TextView) findViewById(R.id.textView27);
        TextView sunday= (TextView) findViewById(R.id.textView28);


        Log.i("workhours",workhours_array);


        Button geri = (Button) findViewById(R.id.button11);
        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


        Button tap_here= (Button) findViewById(R.id.button18);
        tap_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(suplier_info.this,Main3Activity.class);
                startActivity(i);

            }
        });







        try {
            JSONArray w_hours=new JSONArray(workhours_array);

            for(int i=0 ; i<w_hours.length();i++){
                JSONObject jsonobject = w_hours.getJSONObject(i);


                String h_içi=jsonobject.getString("Day");
                String start_saat=jsonobject.getString("StartTime").substring(0,5);
                String end_saat=jsonobject.getString("EndTime").substring(0,5);



                if(h_içi.equals("WeekDay")){
                    days.setText("Monday-Friday");
                    days.setTypeface(tf);

                    TextView saat= (TextView) findViewById(R.id.textView29);
                    saat.setTypeface(tf);
                    saat.setText(start_saat+" - "+end_saat);



                }else if((h_içi.equals("Saturday"))){
                    saturday.setText("Saturday");
                    saturday.setTypeface(tf);


                    TextView saat= (TextView) findViewById(R.id.textView30);
                    saat.setTypeface(tf);
                    saat.setText(start_saat+" - "+end_saat);



                }else if((h_içi.equals("Sunday"))){
                    TextView saat= (TextView) findViewById(R.id.textView31);
                    saat.setTypeface(tf);
                    saat.setText(start_saat+" - "+end_saat);

                    sunday.setText("Sunday");
                    sunday.setTypeface(tf);
                }





            }





        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
