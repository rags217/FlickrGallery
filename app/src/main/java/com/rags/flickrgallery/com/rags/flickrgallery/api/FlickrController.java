package com.rags.flickrgallery.com.rags.flickrgallery.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rags.flickrgallery.R;
import com.rags.flickrgallery.model.ItemsList;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlickrController {

    private static final String SERVER_END_POINT = "https://api.flickr.com/";


    String str = "";

    public static FlickrResponse downloadItems(OkHttpClient client) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_END_POINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FlickrAPI itemsAPI = retrofit.create(FlickrAPI.class);
        Call<ItemsList> call;
        Response<ItemsList> itemsResponse;
        FlickrResponse response = new FlickrResponse();
        try {
            call = itemsAPI.loadItems();
            itemsResponse = call.execute();


            if(itemsResponse.code() != 200) {
                response.setErrorMessageResId(R.string.network_error);
                return response;
            }

            ItemsList itemsList = itemsResponse.body();
            if(itemsList == null) {
                response.setErrorMessageResId(R.string.data_error);
                return response;
            } else {
                response.setItems(itemsList.getItems());
                response.setItemsResponseFlag();
                return response;
            }
        } catch (IOException ex) {
            response.setErrorMessageResId(R.string.network_error);
            return response;
        } catch (IllegalStateException ex) {
            response.setErrorMessageResId(R.string.data_error);
            return response;
        } catch (Exception ex) {
            response.setErrorMessageResId(R.string.unknown_error);
            return response;
        }
    }
}
