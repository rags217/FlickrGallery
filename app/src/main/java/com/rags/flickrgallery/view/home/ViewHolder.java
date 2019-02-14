package com.rags.flickrgallery.view.home;

import android.widget.ImageView;
import android.widget.TextView;


//ViewHolder class is used to recycle view object in list view adapter. Attribute names are self explanatory
public class ViewHolder {
    public ImageView image;
    public TextView title;
    public TextView author;
    public TextView published;
    public String link;
    public String urlString;
}
