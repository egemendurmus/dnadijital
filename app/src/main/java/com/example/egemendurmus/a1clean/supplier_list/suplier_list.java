package com.example.egemendurmus.a1clean.supplier_list;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.egemendurmus.a1clean.R;
import com.example.egemendurmus.a1clean.signup.sms_verification;
import com.example.egemendurmus.a1clean.slidemenu.customer_slide;

import java.util.Collections;

public class suplier_list extends TabActivity {
    private FragmentTabHost mTabHost;
   TabHost.TabSpec tab1;

  TabHost.TabSpec tab2;
    boolean clicks;


    static EditText edit_ara;
    String sort_status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.supplierlist);

        final RelativeLayout rl_search= (RelativeLayout) findViewById(R.id.rl1);
        rl_search.setVisibility(View.INVISIBLE);
        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "opensans.ttf");//yazı fontu

        final TextView t11= (TextView) findViewById(R.id.textView11);
        t11.setTypeface(tf);


         edit_ara= (EditText) findViewById(R.id.editText13);




        Button edit_sil= (Button) findViewById(R.id.button21);
        edit_sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_ara.setText("");


                rl_search.setVisibility(View.INVISIBLE);
                t11.setVisibility(View.VISIBLE);

                Intent i =new Intent(suplier_list.this,suplier_list.class);
                startActivity(i);

            }
        });





        Button ara= (Button) findViewById(R.id.button12);
        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rl_search.setVisibility(View.VISIBLE);
                t11.setVisibility(View.INVISIBLE);


            }
        });







        Button geri= (Button) findViewById(R.id.button11);
        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(suplier_list.this,customer_slide.class);
                startActivity(i);
            }
        });






        final TabHost tabh = (TabHost)findViewById(android.R.id.tabhost);

        final FrameLayout frame = (FrameLayout) findViewById(android.R.id.tabcontent);
        final LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);



        //filter ekranına geçiş view

        final View filter=inflater.inflate(R.layout.filter_shops,null);

        Button b14 = (Button) findViewById(R.id.button14);
        b14.setTypeface(tf);
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame.removeAllViews();

                frame.addView(filter);

                CheckBox cb = (CheckBox) findViewById(R.id.checkBox);
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if ( isChecked )
                        {
                            Toast.makeText(getApplicationContext(),
                                    "Checked", Toast.LENGTH_LONG).show();



                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "unChecked", Toast.LENGTH_LONG).show();
                        }
                    }
                });






            }
        });










           //sort ekranına geçiş view olarak

        final View vv = inflater.inflate(R.layout.activity_shop_shorts, null);



          final Button b13= (Button) findViewById(R.id.button13);
          b13.setText("SORTİNG");
          b13.setTypeface(tf);
          b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frame.removeAllViews();

                 frame.addView(vv);

                Button sort_back= (Button) findViewById(R.id.button22);
                sort_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // onBackPressed();
                        tabh.setup();

                    }
                });



              //price ascending
                RadioButton price_asc= (RadioButton) findViewById(R.id.radioButton);
                price_asc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Collections.sort(expand_list.mDataSource,sort_methods.totalComparator);
                        sort_status="Price (Ascending)";

                        b13.setText(sort_status);

                    }
                });



                //price descending

                RadioButton price_desc= (RadioButton) findViewById(R.id.radioButton2);
                price_desc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Collections.sort(expand_list.mDataSource,sort_methods.totalComparator_desc);
                        sort_status="Price (Descending)";
                        b13.setText(sort_status);


                    }
                });





                //rating ascending
                RadioButton rating_asc= (RadioButton) findViewById(R.id.radioButton5);
                rating_asc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Collections.sort(expand_list.mDataSource,sort_methods.rating_compare_asc);
                        sort_status="Review (Ascending)";

                        b13.setText(sort_status);


                    }
                });



                //rating descending

                RadioButton rating_desc= (RadioButton) findViewById(R.id.radioButton6);
                rating_desc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Collections.sort(expand_list.mDataSource,sort_methods.rating_compare_desc);

                        sort_status="Review (Descending)";
                        b13.setText(sort_status);

                    }
                });



                //alfabe ascending

                RadioButton alfabe_asc= (RadioButton) findViewById(R.id.radioButton7);
                alfabe_asc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Collections.sort(expand_list.mDataSource,sort_methods.alphabet_compare_asc);
                        sort_status="A - Z";
                        b13.setText(sort_status);


                    }
                });



                //alfabe descending

                RadioButton alfabe_desc= (RadioButton) findViewById(R.id.radioButton8);
                alfabe_desc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Collections.sort(expand_list.mDataSource,sort_methods.alphabet_compare_desc);

                        sort_status="Z - A";
                        b13.setText(sort_status);

                    }
                });


                ////////////////distance eklenecek servisten gelmiyor ?




























            }
        });





        getTabHost().setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                frame.removeView(vv);
                frame.removeView(filter);


            }

        });


        tab1 = tabh.newTabSpec("tab menü 1. seçenek");
        tab2 = tabh.newTabSpec("tab menü 2. seçenek");

        tab1.setIndicator("ALL");

        expand_list.mDataSource.clear();
        tab1.setContent(new Intent(suplier_list.this,expand_list.class));



        tab2.setIndicator("Favourites");
        tab2.setContent(new Intent(suplier_list.this,sms_verification.class));


        tabh.addTab(tab1); tabh.addTab(tab2);









     /*   final TabHost host = (TabHost)findViewById(android.R.id.tabhost);
       host.setup(this.getLocalActivityManager());

        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setIndicator("SORT SHOPS BY");


        spec = host.newTabSpec("Tab Two");
        spec.setIndicator("FILTER SHOPS BY");


/////////////////////////////////////////////////////////////////////////////////////////////////
        //all-favorites

        final TabHost host1 = (TabHost)findViewById(R.id.tabhosts);

        host1.setup(this.getLocalActivityManager());
        final TabHost.TabSpec spec1 = host1.newTabSpec("Tab One");
        TabHost.TabSpec spec2 = host1.newTabSpec("Tab 2");
        host.setVisibility(View.GONE);




        spec1.setContent(new Intent(this,expand_list.class));
        spec1.setIndicator("All");
        host1.addTab(spec1);





        spec1.setContent(new Intent(this,sms_verification.class));
        spec1.setIndicator("Favourites");
        host1.addTab(spec1);





        spec1.setContent(new Intent(this,sms_verification.class));
        spec1.setIndicator("SORT SHOPS BY");
        host.addTab(spec1);


        spec1.setContent(new Intent(this,expand_list.class));
        spec1.setIndicator("FILTER SHOPS BY");
        host.addTab(spec1);



        Button b = (Button) findViewById(R.id.button20);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                host.setVisibility(View.VISIBLE);

            }
        });


*/


    }
}
