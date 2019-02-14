package com.rags.flickrgallery.view.home;

import com.rags.flickrgallery.model.home.Item;

import java.util.ArrayList;

//This is a view class that helps to isolate business logic from the view. MVP architecture is followed in this project.
public interface HomeView {

    //On successfully retrieving the data, populate home page with list of images.
    void showList(ArrayList<Item> items);

    //If the data cannot be downloaded, show error as a label
    void showError(int resID);

    //If the request returns empty response, tell user that there is nothing to be shown at this time.
    void showNothing();

}
