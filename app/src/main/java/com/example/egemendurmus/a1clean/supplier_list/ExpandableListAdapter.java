package com.example.egemendurmus.a1clean.supplier_list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.egemendurmus.a1clean.R;
import com.example.egemendurmus.a1clean.slidemenu.customer_slide;
import com.example.egemendurmus.a1clean.slidemenu.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter implements Filterable {

    private Context _context;
    //private List<Data> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Data, List<String>> _listDataChild;
    public ArrayList<Data> mObjects;
    private Typeface tf;

    String title;

    ArrayList<Data>ascendings;


    public ExpandableListAdapter(Context context, ArrayList<Data> mObjects,
                                 HashMap<Data, List<String>> listChildData ) {
        tf = Typeface.createFromAsset(context.getAssets(), "opensans.ttf");

        this._context = context;
        this.mObjects = mObjects;
        this._listDataChild = listChildData;



    }



    public void setObjects(ArrayList<Data> mObjects, HashMap<Data, List<String>> listChildData) {
        this.mObjects = mObjects;
        this._listDataChild = listChildData;

    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this.mObjects.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        final Data item = mObjects.get(groupPosition);

        Button hire = (Button) convertView.findViewById(R.id.button16);
        hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Data item = mObjects.get(groupPosition);

            //   order.header=item.getmTitle();


                Intent i1 =new Intent(v.getContext(),customer_slide.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                v.getContext().startActivity(i1);


            }
        });


        Button more_info= (Button) convertView.findViewById(R.id.button17);
        more_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i1 =new Intent(v.getContext(),suplier_info.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                v.getContext().startActivity(i1);

                suplier_info.Title=item.getmTitle();
                suplier_info.distance=item.getmdistance();
                suplier_info.rating=item.getmrating();
                suplier_info.workhours_array=item.getworkarray();
                order.supplier=item.getmTitle();


            }
        });






        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this.mObjects.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return (this.mObjects.get(groupPosition));

    }

    @Override
    public int getGroupCount() {
        return (this.mObjects.size());

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


        Data item = mObjects.get(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);


        }


        //supplier adı
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setTypeface(tf);


        lblListHeader.setText(item.getmTitle());

         //distance ölçümü
        TextView distances = (TextView) convertView
                .findViewById(R.id.textView14);
        distances.setTypeface(tf);
        distances.setText("within"+" "+item.getmdistance());


        //price
        TextView price = (TextView) convertView
                .findViewById(R.id.textView17);
        price.setTypeface(tf);
        price.setText("price range:"+" "+item.getmprice());

        //rating
        TextView rating = (TextView) convertView
                .findViewById(R.id.textView16);
        rating.setTypeface(tf);


        if(item.getmrating() != null && !item.getmrating().isEmpty()) {

            rating.setText(item.getmrating());

        }else{
           rating.setText("");
       }


        suplier_info.Title=item.getmTitle();
        suplier_info.distance=item.getmdistance();
        suplier_info.rating=item.getmrating();
        suplier_info.workhours_array=item.getworkarray();
        order.supplier=item.getmTitle();









        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mObjects = (ArrayList<Data>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Data> FilteredArrayNames = new ArrayList<Data>();

                // perform your search here using the searchConstraint String.

                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < mObjects.size(); i++) {
                    Data dataNames = mObjects.get(i);

                   String title=dataNames.getmTitle();
                    if (title.toLowerCase().startsWith(constraint.toString()))  {
                        FilteredArrayNames.add((dataNames));
                    }
                }

                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;
                Log.e("VALUES", results.values.toString());

                return results;
            }
        };
        return filter;
    }
}