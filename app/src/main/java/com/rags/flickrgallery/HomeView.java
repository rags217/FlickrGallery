package com.rags.flickrgallery;

import com.rags.flickrgallery.model.Item;

import java.util.List;

public interface HomeView {

    void showList(List<Item> items);
    void showError(int resID);
    void showNothing();

}
