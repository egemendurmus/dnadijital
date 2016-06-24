package com.example.egemendurmus.a1clean.supplier_list.slidemenus;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.example.egemendurmus.a1clean.R;
import com.example.egemendurmus.a1clean.generalpost;
import com.example.egemendurmus.a1clean.url_libs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Main3Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static String ProductGroupID;
    private static String LOG_TAG = "RecyclerViewActivity";
    static ArrayList<Data> mDataSource = new ArrayList<Data>();
    public static boolean pro_grup;
    ArrayList pro_gr=new ArrayList();
    ListView listView;
    String[] names={"User 1","User 2","User 3","User 4","User 5","User 6"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_main3);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setBackgroundColor(getResources().getColor(R.color.wallet_highlighted_text_holo_light));


        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);//new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new CustomRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL);
        mRecyclerView.addItemDecoration(itemDecoration);








        listView = (ListView) findViewById(R.id.listView1);
         //listView.setAdapter(adapter);







        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((CustomRecyclerViewAdapter) mAdapter).setOnItemClickListener(new CustomRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);


                pro_grup=true;

                generalpost.url = url_libs.getproductlistbyproductgroup;
                //linki belirtiyoruz

                generalpost.ProductGroupID= (String) pro_gr.get(position);


                try {
                    synchronized (this) {
                        wait(100);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    generalpost gp = new generalpost();


                                    String serverResponse = gp.execute().get();

                                    if (serverResponse != null) {


                                        JSONObject jo = new JSONObject(serverResponse);

                                        String data = jo.getString("Data");

                                        JSONObject job = new JSONObject(data);


                                          System.out.println("olurmuböyle hasan"+" "+data);

                                    } else {


                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private ArrayList<Data> getDataSet() {
        final ArrayList results = new ArrayList<Data>();

        //üst slide daki gömlek patolon vs menüsü

        generalpost.url = url_libs.getproductgrouplist;

        try {
            synchronized (this) {
                wait(100);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            generalpost gp = new generalpost();
                            String serverResponse = gp.execute().get();

                            if (serverResponse != null) {


                                JSONObject jo = new JSONObject(serverResponse);

                                String data = jo.getString("Data");

                                JSONArray jar = new JSONArray(data);

                                for (int i = 0; i < jar.length(); i++) {

                                    JSONObject job = jar.getJSONObject(i);

                                    String gr_name = job.getString("GroupName");
                                    String pic_name = job.getString("GroupImageURL");
                                    ProductGroupID = job.getString("ProductGroupID");

                                    pro_gr.add(ProductGroupID);


                                    String ProductCategoryID = job.getString("ProductCategoryID");


                                    Data obj = new Data(gr_name, ("http://46.197.42.230:85/" + pic_name).toString(), ProductGroupID, ProductCategoryID);
                                    results.add(i, obj);


                                }


                            } else {


                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return results;
    }

}
