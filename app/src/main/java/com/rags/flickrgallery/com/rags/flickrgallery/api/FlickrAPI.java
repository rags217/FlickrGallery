package com.rags.flickrgallery.com.rags.flickrgallery.api;

import android.support.annotation.NonNull;

import com.rags.flickrgallery.model.ItemsList;


import retrofit2.Call;
import retrofit2.http.GET;

public interface FlickrAPI {

    String SERVER_ACTION = "services/feeds/photos_public.gne?format=json&nojsoncallback=1";

    @NonNull
    @GET(SERVER_ACTION)
    Call<ItemsList> loadItems();
}
