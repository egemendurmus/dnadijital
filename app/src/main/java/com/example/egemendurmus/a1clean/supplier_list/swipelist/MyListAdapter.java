package com.example.egemendurmus.a1clean.supplier_list.swipelist;

/**
 * Created by egemen.durmus on 24/06/16.
 */

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.egemendurmus.a1clean.R;

public class MyListAdapter extends BaseAdapter {

    private Context ctx;
    private String[] names;

    public MyListAdapter(Context ctx, String[] data) {
        this.ctx = ctx;
        this.names = data;
    }

    static class ViewHolder {
        RelativeLayout container;
        TextView userName;
        GestureDetectorCompat mDetector;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int arg0) {
        return names[arg0];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(
                    R.layout.list_row_item, null);
            final ViewHolder holder = new ViewHolder();
            holder.container = (RelativeLayout) convertView
                    .findViewById(R.id.container);
            holder.userName = (TextView) convertView.findViewById(R.id.name);
            holder.mDetector = new GestureDetectorCompat(ctx,
                    new MyGestureListener(ctx, convertView));
            convertView.setTag(holder);

        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.userName.setText(names[position]);
        holder.container.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                holder.mDetector.onTouchEvent(event);
                return true;
            }
        });

        return convertView;
    }

}