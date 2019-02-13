package com.rags.flickrgallery;

import java.util.ArrayList;

public interface HomeView {

    void showImages(ArrayList<Item> trainTimes);
    void showError(int resID);
    void showNothing();

}
