package com.rags.flickrgallery.presenter.home;

import android.os.AsyncTask;

import com.rags.flickrgallery.api.home.FlickrController;
import com.rags.flickrgallery.api.home.FlickrResponse;
import com.rags.flickrgallery.model.home.Item;
import com.rags.flickrgallery.view.home.HomeView;

import java.util.ArrayList;

import okhttp3.OkHttpClient;


//Presenter class responsible for downloading the data
public class ListDownloadTask extends AsyncTask<String, Void, FlickrResponse> {

    private HomeView view;

    public ListDownloadTask(HomeView view) {
        this.view = view;
    }

    @Override
    protected FlickrResponse doInBackground(String... args) {

        String tag = "";
        if(args[0] != null && !args[0].isEmpty())
            tag += args[0];

        return FlickrController.downloadItems(new OkHttpClient(), tag);

    }

    @Override
    protected void onPostExecute(FlickrResponse response) {

        if(response.hasItemsResponse()) {

            ArrayList<Item> items = response.getItems();
            if (items.size() > 0)
                view.showList(items);
            else
                view.showNothing();
        } else
            view.showError(response.getErrorMessageResId());
    }
}
