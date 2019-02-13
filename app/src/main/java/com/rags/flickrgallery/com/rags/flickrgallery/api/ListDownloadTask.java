package com.rags.flickrgallery.com.rags.flickrgallery.api;

import android.os.AsyncTask;

import com.rags.flickrgallery.HomeView;
import com.rags.flickrgallery.model.Item;

import java.util.List;

import okhttp3.OkHttpClient;

public class ListDownloadTask extends AsyncTask<Void, Integer, FlickrResponse> {

    private HomeView view;

    public ListDownloadTask(HomeView view) {
        this.view = view;
    }

    @Override
    protected FlickrResponse doInBackground(Void... v) {
        return FlickrController.downloadItems(new OkHttpClient());

    }

    @Override
    protected void onPostExecute(FlickrResponse response) {

        if(response.hasItemsResponse()) {

            List<Item> items = response.getItems();
            if (items.size() > 0)
                view.showList(items);
            else
                view.showNothing();
        } else
            view.showError(response.getErrorMessageResId());
    }
}
