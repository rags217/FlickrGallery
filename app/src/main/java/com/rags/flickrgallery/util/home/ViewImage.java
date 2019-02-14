package com.rags.flickrgallery.util.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.rags.flickrgallery.R;

public class ViewImage {

    //view image in browser
    //@param: url string to load the browser with the image
    public void viewInBrowser(String url, Context context) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(Intent.createChooser(i,context.getString(R.string.view_in_browser)));
    }
}
