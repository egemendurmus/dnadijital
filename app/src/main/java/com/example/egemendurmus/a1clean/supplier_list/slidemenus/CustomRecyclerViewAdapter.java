package com.example.egemendurmus.a1clean.supplier_list.slidemenus;

/**
 * Created by egemen.durmus on 24/06/16.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.egemendurmus.a1clean.R;
import com.example.egemendurmus.a1clean.generalpost;

import java.util.ArrayList;

/**
 * Created by javierg on 14/03/16.
 */
public class CustomRecyclerViewAdapter extends RecyclerView
        .Adapter<CustomRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Data> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;
        ImageView icon;
        DownloadImageTask mTask;
        generalpost gp=new generalpost();

        public DataObjectHolder(View itemView) {



            super(itemView);
            label = (TextView) itemView.findViewById(R.id.item_list_view_text_view);
            icon=(ImageView)itemView.findViewById(R.id.imageView8);
         //   dateTime = (TextView) itemView.findViewById(R.id.item_list_view_text_view_two);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public CustomRecyclerViewAdapter(ArrayList<Data> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item3, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getmText1());


        holder.mTask=new DownloadImageTask(mDataset.get(position).getMresim().toString(),holder.icon);

       // new DownloadImageTask(mDataset.get(position).getMresim().toString(),holder.icon);
       // generalpost.ProductGroupID=mDataset.get(position).get_pro_grup();


        if (!holder.mTask.isCancelled()) {
            holder.mTask.execute();
        }

//        holder.dateTime.setText(mDataset.get(position).getmText2());
    }

    public void addItem(Data dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}