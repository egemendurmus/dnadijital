
package com.example.egemendurmus.a1clean.supplier_list;

import java.util.ArrayList;

/**
 * Created by egemen.durmus on 15/06/16.
 */
public class Data {


    private String _mTitle;
    private String _mdistance;
    private String _mprice;
    private String _mrating;
    private String _workarray;

    private ArrayList<String> mAddList;

    public Data(String Title,String distance,String price,String rating,String workarray,ArrayList<String> addList) {
        super();

        this._mTitle = Title;
        this._mdistance=distance;
        this._mprice=price;
        this._mrating=rating;
        this._workarray=workarray;

    }



    public String getmTitle() {
        return _mTitle;
    }

    public void setmTitle(String mTitle) {
        this._mTitle = mTitle;
    }




    public String getmdistance() {
        return _mdistance;
    }

    public void set_mdistance(String mdistance) {
        this._mdistance = mdistance;
    }



    public void set_mdprice(String price) {
        this._mprice = price;
    }


    public String getmprice() {
        return _mprice;
    }

    public void set_mrating(String rating) {
        this._mrating = rating;
    }

    public String getmrating() {
        return _mrating;
    }






    public String getworkarray() {
        return _workarray;
    }

    public void set_workarray(String workarray) {
        this._workarray = workarray;
    }





}