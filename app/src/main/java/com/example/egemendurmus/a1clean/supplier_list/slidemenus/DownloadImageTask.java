package com.example.egemendurmus.a1clean.supplier_list.slidemenus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by egemen.durmus on 24/06/16.
 */


public class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {

    private String mUrl;
    private ImageView mImageView = null;

    public DownloadImageTask(String Urls, ImageView imageView) {

        mUrl = Urls;
        this.mImageView = imageView;
    }

    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (result != null) {
            mImageView.setImageBitmap(result);
        }
    }

    protected Bitmap doInBackground(Void... params) {

        Bitmap bitmap = getBitmap(mUrl);
        return bitmap;
    }

    public Bitmap getBitmap(String imageUrl) {
        Bitmap mBitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            mBitmap = BitmapFactory.decodeStream(is);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mBitmap;
    }
}