package com.rags.flickrgallery.view.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rags.flickrgallery.R;
import com.rags.flickrgallery.model.home.Item;
import com.rags.flickrgallery.presenter.home.ListDownloadTask;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeView {

    //List view that loads the gallery images and metadata
    ListView galleryList;

    //Text view that is shown when no image is there to show
    TextView noImageToShow;

    //Progress bar to show that the app is working to download data
    ProgressBar progressBar;

    //Adapter for list view
    HomeAdapter adapter;

    ArrayList<Item> items;

    private boolean showSearchBar = true;

    private boolean requiresRefresh; // Flag that tells the app to refresh on resume if data was not populated on previous onCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        galleryList = (ListView)findViewById(R.id.gallery_list);
        noImageToShow = (TextView)findViewById(R.id.no_image_to_show);
        progressBar = findViewById(R.id.progress_bar);

        handleIntent(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(requiresRefresh)
            handleIntent(getIntent());
    }

    //Handle the intent and decide if you have to fetch images feeds with or without tag
    private void handleIntent(Intent intent) {
        String query = null;

        //Check if the activity is triggered by search action. If yes, fetch images with tag searched for
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            showSearchBar = false;
            ActionBar bar = getSupportActionBar();
            bar.setTitle(getString(R.string.search_result_for) + " " + query);
        }

        //Download image feeds synchronously
        new ListDownloadTask(this).execute(query);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        if(showSearchBar) {
            MenuInflater inflater = getMenuInflater();

            //inflate search action menu
            inflater.inflate(R.menu.menu_main, menu);

            //Use native search service
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            //Use native search view
            SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

            //Set the search result point to same activity which triggered the search
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }

        return true;
    }

    @Override
    public void showList(ArrayList<Item> items) {
        adapter = new HomeAdapter(this, R.layout.fg_list_item, items);
        galleryList.setAdapter(adapter);
        galleryList.setVisibility(View.VISIBLE);
        noImageToShow.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        requiresRefresh = true; //Set this flag to false as now it has got data and thus avoiding unnecessary refresh
    }

    @Override
    public void showError(int resID) {
        noImageToShow.setText(resID);
        noImageToShow.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        requiresRefresh = true; //Set this flag to true so that the app will try to download the data again on resume since it has not got any data
    }

    @Override
    public void showNothing() {
        noImageToShow.setText(R.string.empty_list);
        noImageToShow.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        requiresRefresh = true; //Set this flag to true so that the app will try to download the data again on resume since it has not got any data
    }
}
