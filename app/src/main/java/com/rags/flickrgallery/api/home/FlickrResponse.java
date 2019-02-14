package com.rags.flickrgallery.api.home;

import com.rags.flickrgallery.model.home.Item;
import java.util.ArrayList;


// Response structure of the request made.
public class FlickrResponse {

    public boolean hasItemsResponse() {
        return itemsResponseFlag;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getErrorMessageResId() {
        return errorMessageResId;
    }

    public void setItemsResponseFlag() {
        this.itemsResponseFlag = true;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void setErrorMessageResId(int errorMessageResId) {
        this.errorMessageResId = errorMessageResId;
    }

    private boolean itemsResponseFlag = false;
    private ArrayList<Item> items;
    private int errorMessageResId;

}
