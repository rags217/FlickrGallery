package com.rags.flickrgallery.util.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.LruCache;

import com.rags.flickrgallery.view.home.ViewHolder;


public class SaveImage {


    //save image to gallery
    //@param: holder object which contains key for bitmap in bitmap cache
    public void save(ViewHolder holder, Context context, LruCache<String,Bitmap> memoryCache) {
        MediaStore.Images.Media.insertImage(context.getContentResolver(), memoryCache.get(holder.urlString), null, null);
    }
}
