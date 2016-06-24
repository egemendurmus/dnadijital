package com.example.egemendurmus.a1clean.supplier_list.slidemenus;

/**
 * Created by egemen.durmus on 24/06/16.
 */
public class Data {
    private String mText1;
    private String mresim;
    private String _pro_grup;



    private String _pro_cat_id;





    Data(String text1, String resim, String pro_grup,String cat_id){
        mText1 = text1;
        mresim=resim;
        _pro_grup=pro_grup;
        _pro_cat_id=cat_id;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getMresim() {
        return mresim;
    }

    public void setMresim(String mresim) {
        this.mresim = mresim;
    }


    public String get_pro_grup() {
        return _pro_grup;
    }

    public void set_pro_grup(String _pro_grup) {
        this._pro_grup = _pro_grup;
    }

    public String get_pro_cat_id() {
        return _pro_cat_id;
    }

    public void set_pro_cat_id(String _pro_cat_id) {
        this._pro_cat_id = _pro_cat_id;
    }
}
