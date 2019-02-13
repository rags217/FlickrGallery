package com.rags.flickrgallery;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.rags.flickrgallery.com.rags.flickrgallery.api.ListDownloadTask;
import com.rags.flickrgallery.model.Item;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeView {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new ListDownloadTask(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


            MenuInflater inflater = getMenuInflater();

            //inflate search action menu
            inflater.inflate(R.menu.menu_main, menu);

            //Use native search service
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            //Use native search view
            SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

            //Set the search result point to same activity which triggered the search
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public void showList(List<Item> items) {
        mRecyclerView.setAdapter(new HomeAdapter(items));
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(int resID) {

    }

    @Override
    public void showNothing() {

    }
}
