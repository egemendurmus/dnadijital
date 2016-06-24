package com.example.egemendurmus.a1clean.slidemenu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.egemendurmus.a1clean.R;
import com.example.egemendurmus.a1clean.customer_adress.MapsActivity;
import com.example.egemendurmus.a1clean.generalpost;
import com.example.egemendurmus.a1clean.supplier_list.suplier_list;
import com.example.egemendurmus.a1clean.url_libs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static com.google.android.gms.internal.zzir.runOnUiThread;

public class order extends Fragment {


generalpost gp=new generalpost();
    public static String sonuç_resp,header,supplier;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       /* getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getActivity().requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        getActivity(). setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);*/

        final Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf");//yazı fontu


        FragmentActivity fa;

        // Inflate the layout for this fragment
       final View root= inflater.inflate(R.layout.content_ustomer_slider, container, false);

        //default text ayarları

        final TextView tv_1= (TextView)root.findViewById(R.id.textView6);
        TextView tv_2= (TextView)root. findViewById(R.id.textView7);
        TextView tv_3= (TextView)root. findViewById(R.id.textView8);
        TextView tv_4= (TextView)root. findViewById(R.id.textView9);
        EditText supplier_result= (EditText) root.findViewById(R.id.editText9);

        tv_1.setTypeface(tf);
        tv_2.setTypeface(tf);
        tv_3.setTypeface(tf);
        tv_4.setTypeface(tf);
        supplier_result.setTypeface(tf);


            supplier_result.setText(supplier);

















        //konum alma

        final Button konum= (Button) root.findViewById(R.id.button3);


            generalpost.url= url_libs.getmemberdefaultadress;






        try {
                synchronized (this) {
                    wait(100);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {





                                String serverResponse = gp.execute().get();

                                if (serverResponse != null) {


                                    JSONObject jo = new JSONObject(serverResponse);

                                    String data = jo.getString("Data");

                                    JSONObject job = new JSONObject(data);


                                    String member_country = job.getString("CountryName");
                                    String member_city = job.getString("CityName");
                                    String member_distirct = job.getString("DistrictName");
                                    String member_neighboor = job.getString("NeighborhoodName");

                                    EditText e8 = (EditText) root.findViewById(R.id.editText8);

                                    e8.setText(member_neighboor + " " + member_distirct + " " + member_city + " " + member_country);


                                    //   System.out.println("olurmuböyle hasan"+" "+member_country);

                                }else{


                                }
                                }catch(InterruptedException e){
                                    e.printStackTrace();
                                }catch(ExecutionException e){
                                    e.printStackTrace();
                                }catch(JSONException e){
                                    e.printStackTrace();
                                }


                        }
                    });

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }













        konum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(), MapsActivity.class);
                startActivity(i);
            }
        });






        //konum alma sonradan eklenildiği için transparent button konum butonu ile aynı işi yapıyor

        Button b23= (Button) root.findViewById(R.id.button23);
        b23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(getActivity(), MapsActivity.class);
                startActivity(i);

            }
        });







      //supplierlist

        EditText supp= (EditText) root.findViewById(R.id.editText9);
        supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sp= new Intent(getActivity().getApplicationContext(), suplier_list.class);
                startActivity(sp);
            }
        });


        Button b24= (Button) root.findViewById(R.id.button24);
        b24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(getActivity(), suplier_list.class);
                startActivity(i);

            }
        });






        //müşteriden alınan tarihler

        final Button tarih = (Button)root. findViewById(R.id.button4);
        tarih.setTypeface(tf);
        tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Calendar mcurrentTime = Calendar.getInstance();
                int year = mcurrentTime.get(Calendar.YEAR);//Güncel Yılı alıyoruz
                int month = mcurrentTime.get(Calendar.MONTH);//Güncel Ayı alıyoruz
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);//Güncel Günü alıyoruz


                DatePickerDialog datePicker ;//Datepicker objemiz


                //müşteriden alınan tarih ve saatler

                datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        //tarihTextView.setText( dayOfMonth + "/" + monthOfYear+ "/"+year);//Ayarla butonu tıklandığında textview'a yazdırıyoruz

                        tarih.setText(dayOfMonth + "/" + monthOfYear+ "/"+year);

                    }
                },year,month,day);//başlarken set edilcek değerlerimizi atıyoruz
                DatePicker dp = datePicker.getDatePicker();

                dp.setMinDate(mcurrentTime.getTimeInMillis());//set the current day as the max date







                Calendar now=Calendar.getInstance();



                now.add(Calendar.DAY_OF_MONTH,30);

                dp.setMaxDate(now.getTimeInMillis());





                datePicker.setTitle("Tarih Seçiniz");
                datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", datePicker);


                datePicker.show();






            }
        });


        final  Button saat = (Button)root. findViewById(R.id.button5);
        saat.setTypeface(tf);
        saat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });





        //kuru temizlemiciden alınan tarihler

        final Button kuru_tarih= (Button)root. findViewById(R.id.button6);
        final Button kuru_saat= (Button)root. findViewById(R.id.button7);


        kuru_saat.setTypeface(tf);
        kuru_tarih.setTypeface(tf);

        kuru_tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int year = mcurrentTime.get(Calendar.YEAR);//Güncel Yılı alıyoruz
                int month = mcurrentTime.get(Calendar.MONTH);//Güncel Ayı alıyoruz
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);//Güncel Günü alıyoruz


                DatePickerDialog datePicker ;//Datepicker objemiz


                //müşteriden alınan tarih ve saatler

                datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        //tarihTextView.setText( dayOfMonth + "/" + monthOfYear+ "/"+year);//Ayarla butonu tıklandığında textview'a yazdırıyoruz

                        kuru_tarih.setText(dayOfMonth + "/" + monthOfYear+ "/"+year);

                    }
                },year,month,day);//başlarken set edilcek değerlerimizi atıyoruz
                DatePicker dp = datePicker.getDatePicker();









                Calendar now=Calendar.getInstance();
                Calendar fut=Calendar.getInstance();
                fut.add(Calendar.DAY_OF_MONTH,1);



                now.add(Calendar.DAY_OF_MONTH,30);

                dp.setMinDate(fut.getTimeInMillis());//set the current day as the max date

                dp.setMaxDate(now.getTimeInMillis());







                datePicker.setTitle("Tarih Seçiniz");
                datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", datePicker);


                datePicker.show();



            }
        });





        kuru_saat.setOnClickListener(new View.OnClickListener() {







            @Override
            public void onClick(View v) {



                // TODO Auto-generated method stub






            }
        });



        //talebi gönderme tuşu

        Button odeme = (Button)root. findViewById(R.id.button8);
        odeme.setText("ödeme yap");
        odeme.setTypeface(tf);
        odeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





        return root;
    }

   public void frags(Fragment frag) {
       System.out.println("egemen");


       FragmentManager fm = getFragmentManager();


           fm.beginTransaction().add(R.id.content_frame, frag).commit();






   }



}