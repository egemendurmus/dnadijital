package com.example.egemendurmus.a1clean;

import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.egemendurmus.a1clean.customer_adress.Main2Activity;
import com.example.egemendurmus.a1clean.signup.sign_up;
import com.example.egemendurmus.a1clean.slidemenu.customer_slide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

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

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    // Google client to interact with Google API
    private static final int SIGN_IN_CODE = 9001;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private GoogleSignInAccount account;
    private static final String TAG = "IdTokenActivity";

    private static final int RC_SIGN_IN = 9001;
    private static final int REQ_SIGN_IN_REQUIRED = 55664;

    String mAccountName;
    boolean kontrol;
    boolean servis;

    HttpPost request;

    ProgressDialog pDialog;
    String url;
    String veri_string;
    Button buton;
    static String responseServer;
    public static String username, profilepicture;
    static String password, guest;
    PostClass post = new PostClass();  //Post Class dan post ad�nda nesne olusturduk.Post class�n i�indeki methodu kullanabilmek i�in
    public static String isim, soyisim, mail, active_ticket, user_id;

    public static int member_id;


    JSONObject jb;
    final Context context = this;

    // facebook post ayarla
    public static String fb_accessToken;
    public static String fb_user_id;


    facepost fp = new facepost();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }


    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            // tv_username.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));

            String tok = acct.getIdToken();
            String u_id = acct.getId();
            String authCode = acct.getEmail();

            System.out.println("token is =" + tok + " " + u_id + " " + authCode);


        } else {
            // Signed out, show unauthenticated UI.
            // updateUI(false);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);


        // Initialize the SDK before executing any other operations,
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();


        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

      /*  RelativeLayout rl= (RelativeLayout) findViewById(R.id.rel);
        rl.setAlpha((float) 0.4);*/


        mAccountName = "egemania55@gmail.com";


        AccessToken token = AccessToken.getCurrentAccessToken();

        Log.i("token", String.valueOf(token));


        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "opensans.ttf");//yazı fontu

        TextView login_as_guest = (TextView) findViewById(R.id.guest);
        TextView ask_account = (TextView) findViewById(R.id.textView2);
        TextView ask_account_sign = (TextView) findViewById(R.id.textView3);
        final EditText u_name = (EditText) findViewById(R.id.editText);
        final EditText psw = (EditText) findViewById(R.id.editText2);

        login_as_guest.setTypeface(tf);
        ask_account.setTypeface(tf);
        ask_account_sign.setTypeface(tf);


        //kayıtlı kullanıcı kontrolü


        String user = kayıtlı_kullanıcı("login_mail").toString();

        if (user != null && !user.isEmpty()) { /* do your stuffs here */

            u_name.setText(kayıtlı_kullanıcı("login_mail"));
            psw.setText(kayıtlı_kullanıcı("login_pass"));


        }


        //misafir kullanıcı girişi

        login_as_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Post().execute(); //Asynctask Classı Çağırıyoruz


                kontrol = true;


            }
        });


        //yeni kayıt olduğu zaman
        ask_account_sign.setMovementMethod(LinkMovementMethod.getInstance());

        ask_account_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent sign_up = new Intent(MainActivity.this, sign_up.class);
                startActivity(sign_up);
            }
        });


        //sistem logini mail girişli olan (post işlemi)

        buton = (Button) findViewById(R.id.button);

        buton.setTypeface(tf);
        buton.setOnClickListener(new View.OnClickListener() { //buton a click listener ekledik

            public void onClick(View v) {


                u_name.setTypeface(tf);
                psw.setTypeface(tf);

                username = u_name.getText().toString();
                password = psw.getText().toString();

                new Post().execute(); //Asynctask Classı Çağırıyoruz

                kontrol = false;


            }
        });


        //facebook logini yapılıyor
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        loginButton.setBackgroundResource(R.drawable.fb_login);
        loginButton.setText(" ");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                Log.d(TAG, object.optString("id"));
                                Log.d(TAG, object.optString("email"));


                                fb_accessToken = loginResult.getAccessToken().getToken();
                                Log.d(TAG, fb_accessToken);


                                fb_user_id = object.optString("id");

                                //facebook post ayarla

                                fp.username = fb_user_id;
                                fp.password = fb_accessToken;
                                fp.execute();


                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id");

                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }


        });

        String serverClientId = getString(R.string.server_client_id);

// Initializing google plus api client

        //953282697165-tp08p3m3vuvra87q2obsasblhgmt4osv.apps.googleusercontent.com
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


// Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        AccountManager am = AccountManager.get(this);
        Bundle options = new Bundle();


        SignInButton signInButton = (SignInButton) findViewById(R.id.btn_sign_in);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                new RetrieveTokenTask().execute(mAccountName);


            }
        });

    }

    private void validateServerClientID() {
        String serverClientId = getString(R.string.server_client_id);
        String suffix = ".apps.googleusercontent.com";
        if (!serverClientId.trim().endsWith(suffix)) {
            String message = "Invalid server client ID in strings.xml, must end with " + suffix;

            Log.w(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    private void signIn() {

        //google api bağlantısı başlatılıyor

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    class Post extends AsyncTask<Void, Void, Void> {


        protected void onPreExecute() { // Post tan �nce yap�lacak i�lemler. Y�kleniyor yaz�s�n�(ProgressDialog) g�sterdik.
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Yükleniyor...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false); // ProgressDialog u iptal edilemez hale getirdik.
            pDialog.show();


        }

        protected Void doInBackground(Void... unused) { // Arka Planda yap�lacaklar. Yani Post i�lemi

            List<NameValuePair> params = new ArrayList<NameValuePair>(2); //Post edilecek de�i�kenleri ayarliyoruz.
            //Bu de�i�kenler bu uygulamada hi�bir i�e yaram�yor.Sadece g�stermek ama�l�
            //  params.add(new BasicNameValuePair("UserID", "admin"));
            //params.add(new BasicNameValuePair("Password", "4321"));


            JSONObject jsonObj = new JSONObject();
            try {

                if (kontrol == true) {
                    servis = true;
                }


                if (kontrol == false) {
                    servis = false;
                    jsonObj.put("UserID", username);
                    jsonObj.put("Password", password);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            params.add(new BasicNameValuePair("", jsonObj.toString()));

            HttpClient httpClient = new DefaultHttpClient(); //Deprecated


            StringEntity params1 = null;
            try {

                if (servis == true) {
                    request = new HttpPost("http://46.197.42.230:85/Membership.svc/GetGuestTicket");


                }
                if (servis == false) {
                    request = new HttpPost("http://46.197.42.230:85/Membership.svc/login");


                }

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


            //    veri_string = post.httpPost(url, "POST", params1, 20000); //PostClass daki httpPost metodunu �a��rd�k.Gelen string de�erini ald�k

            Log.d("HTTP POST CEVAP:", "" + veri_string);// gelen veriyi log tuttuk
            Log.d("para:", "" + params);// gelen veriyi log tuttuk

            return null;
        }

        protected void onPostExecute(Void unused) { //Posttan sonra
            pDialog.dismiss();  //ProgresDialog u kapat�yoruz.


            try {

                jb = new JSONObject(responseServer);

                String ticket = jb.getString("Ticket");
                Log.e("mesajgiriş", ticket);
                active_ticket = ticket;

                String messageData = jb.getString("Data");
                Log.e("mesajgiriş", messageData);
                JSONObject InData = new JSONObject(messageData);

                if (servis == true) {
                    mail = InData.getString("UserID");
                }
                if (servis == false) {
                    mail = InData.getString("Email");

                }

                Log.e("mesaj", mail);


                isim = InData.getString("FirstName");
                soyisim = InData.getString("LastName");


                profilepicture = InData.getString("ProfilePictureURL");

                user_id = InData.getString("ID");
                member_id = Integer.parseInt(InData.getString("MemberID"));


                if (isim != null) {

                } else {
                    isim = "1clean";
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            Intent i = new Intent(MainActivity.this, customer_slide.class);
            startActivity(i);

            customer_slide.name = isim;
            customer_slide.surname = soyisim;
            customer_slide.image = profilepicture;
            Main2Activity.ticket = active_ticket;
            customer_slide.mail = mail;
            sakla("login_mail", mail);
            sakla("login_pass", password);


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


    private void sakla(String key, String value) {
        // shared preferences ile mydocs ad altnda bir tane xml dosya ayor
        SharedPreferences sharedPreferences = getSharedPreferences("mydocs",
                MODE_PRIVATE);
        // dosyaya yazmamza yardmc olacak bir tane editr oluturduk.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // editr aracl ile key value deerlerini yazdk.
        // ben string kaydettim ama farkl veri tipleri de kaydediliyor.
        editor.putString(key, value);
        // Farkl veri tiplerine rnek
        // editor.putBoolean("boolean", true);
        // editor.putFloat("float", (float) 123.23);
        // editor.putInt("int", 23);
        // editor.putLong("long", 123134243);

        /*
         * Serilize edilmi dosyay aadaki gibi kaydedebilirz.
         * ArrayList<String> al=new SharedPreferencesSampleActivity();
         * editor.putString(ARRAY_LIST_TAG, ObjectSerializer.serialize(al));
         */

        // bilgileri kaydettim.
        editor.commit();
    }


    private CharSequence kayıtlı_kullanıcı(String key) {

        SharedPreferences sharedPreferences = getSharedPreferences("mydocs",
                MODE_PRIVATE);
        // key deerini vererek value deerini aldm.
        String strSavedMem1 = sharedPreferences.getString(key, "");

        /*
         * Serilize edilmi dosyay aadaki gibi okuruz. return
         * (ArrayList<String>)
         * ObjectSerializer.deserialize(sharedPreferences.getString(key, ));
         */

        return strSavedMem1;

        // TODO Auto-generated method stub
    }


    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            String scopes = "oauth2:profile email";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(getApplicationContext(), accountName, scopes);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } catch (UserRecoverableAuthException e) {
                startActivityForResult(e.getIntent(), REQ_SIGN_IN_REQUIRED);
            } catch (GoogleAuthException e) {
                Log.e(TAG, e.getMessage());
            }
            return token;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("Token Value: " + s);
        }
    }


}
