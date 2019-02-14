package com.rags.flickrgallery.api.home;

import  android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;


import com.rags.flickrgallery.view.home.ViewHolder;

import java.io.IOException;
import java.net.URL;


public class ImageDownloader extends AsyncTask<ViewHolder, Void, Bitmap> {

    //holder obect the image has to be set after downloading on successful download
    ViewHolder viewHolder;

    //Bitmap cache to store downloaded image so that the images don't have to be dowloaded while scrolling up and down
    LruCache<String, Bitmap> mMemoryCache;

    public ImageDownloader(LruCache<String, Bitmap> memoryCache) {
        //Memory cache instance passed from the adapter
        this.mMemoryCache = memoryCache;
    }

    @Override
    protected Bitmap doInBackground(ViewHolder... params) {
        Bitmap bitmap = null;
        viewHolder = params[0];

        try {
            URL imageURL = new URL(viewHolder.urlString);
            bitmap = BitmapFactory.decodeStream(imageURL.openStream());
        } catch (IOException e) {
            Log.e("error", "Downloading Image Failed");
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        //Check if the bitmap is not null and store in cache if so.
        if (bitmap != null) {
            viewHolder.image.setImageBitmap(bitmap);
            mMemoryCache.put(viewHolder.urlString, bitmap);
        }
    }
}

