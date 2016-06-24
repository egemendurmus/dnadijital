package com.example.egemendurmus.a1clean.slidemenu;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.egemendurmus.a1clean.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class customer_slide extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


   public static String name,mail,surname,image,deneme;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_customer_slide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        navigationView.setItemIconTintList(null);

        View hView =  navigationView.getHeaderView(0);

        iv = (ImageView)hView.findViewById(R.id.imageView);
        String photo_url="http://46.197.42.230:85"+"/"+image;
        photo_url= photo_url.replaceAll(" ", "%20");

        new ImageDownloader().execute(photo_url);
        Log.i("image",photo_url);

       //kullanıcı kayıtlımı değil mi slide menu de yazacak
       if(name!=null){
           TextView nav_user = (TextView)hView.findViewById(R.id.username);
           nav_user.setText(name+" "+surname);


           TextView nav_mail = (TextView)hView.findViewById(R.id.mail);
           nav_mail.setText(mail);

       }else{
           TextView nav_user = (TextView)hView.findViewById(R.id.username);
           nav_user.setText("guest user");


           TextView nav_mail = (TextView)hView.findViewById(R.id.mail);
           nav_mail.setText(" ");

       }












        Fragment fragment=null;
        fragment = new order();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_slide, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment=null;


        if (id == R.id.nav_camera) {
            // Handle the camera action

            fragment = new order();

        } else if (id == R.id.nav_gallery) {
            fragment=new ayarlar();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class ImageDownloader extends AsyncTask<String, Integer, Bitmap> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpCon = (HttpURLConnection) url
                        .openConnection();
                if (httpCon.getResponseCode() != 200)
                    throw new Exception("Failed to connect");
                InputStream is = httpCon.getInputStream();
                return BitmapFactory.decodeStream(is);
            } catch (Exception e) {

                Log.e("Image", "Failed to load image", e);
            }
            return null;
        }

        protected void onProgressUpdate(Integer... params) {

        }

        protected void onPostExecute(Bitmap img) {
            if (iv != null && img != null) {
                iv.setImageBitmap(img);
            }






        }

        protected void onCancelled() {
        }
    }
}
