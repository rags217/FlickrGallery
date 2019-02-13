package com.rags.flickrgallery.com.rags.flickrgallery.api;

import com.rags.flickrgallery.model.Item;

import java.util.List;

public class FlickrResponse {

    public boolean hasItemsResponse() {
        return itemsResponseFlag;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getErrorMessageResId() {
        return errorMessageResId;
    }

    public void setItemsResponseFlag() {
        this.itemsResponseFlag = true;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setErrorMessageResId(int errorMessageResId) {
        this.errorMessageResId = errorMessageResId;
    }

    private boolean itemsResponseFlag = false;
    private List<Item> items;
    private int errorMessageResId;

}
