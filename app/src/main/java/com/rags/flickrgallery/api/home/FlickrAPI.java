package com.rags.flickrgallery.api.home;

import android.support.annotation.NonNull;

import com.rags.flickrgallery.model.home.ItemsList;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrAPI {

    String SERVER_ACTION = "services/feeds/photos_public.gne?format=json&nojsoncallback=1";

    @NonNull
    @GET(SERVER_ACTION)
    Call<ItemsList> loadItems(@Query("tags") String tag);
}
