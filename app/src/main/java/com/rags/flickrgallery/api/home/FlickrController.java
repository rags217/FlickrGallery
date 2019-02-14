package com.rags.flickrgallery.api.home;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rags.flickrgallery.R;
import com.rags.flickrgallery.model.home.ItemsList;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//This class is responsible for downloading data using third party library
public class FlickrController {

    private static final String SERVER_END_POINT = "https://api.flickr.com/";


    String str = "";

    public static FlickrResponse downloadItems(OkHttpClient client, String tag) {

        //Creating a gson object to parse json data
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
            call = itemsAPI.loadItems(tag);
            itemsResponse = call.execute();



            if(itemsResponse.code() != 200) { // If unable to download data, send appropriate error message to the presenter
                response.setErrorMessageResId(R.string.network_error);
                return response;
            }

            ItemsList itemsList = itemsResponse.body();
            if(itemsList == null) {     //If no data is available to download, send appropriate message to the presenter
                response.setErrorMessageResId(R.string.data_error);
                return response;
            } else {    //If data is downloaded successfully,, send data to the presenter
                response.setItems(itemsList.getItems());
                response.setItemsResponseFlag();
                return response;
            }
        } catch (IOException ex) {      //If there is an exception while attempting to download, send appropriate error message to the presenter
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
