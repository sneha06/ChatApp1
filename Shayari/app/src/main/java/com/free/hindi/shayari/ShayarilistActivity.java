package com.free.hindi.shayari;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.startapp.android.publish.StartAppAd;

import adapters.ListAdapter;


public class ShayarilistActivity extends ActionBarActivity {

    ListView list;
    ListAdapter adapter;
    protected static String sid;
    public static String mimage;
    private StartAppAd startAppAd = new StartAppAd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.free.hindi.shayari.R.layout.activity_shayarilist);

        AdView mAdView = (AdView) findViewById(com.free.hindi.shayari.R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

          mimage = getIntent().getStringExtra("image");

        Toolbar toolbar = (Toolbar) findViewById(com.free.hindi.shayari.R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(mimage.toUpperCase() + " Shayari");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = (ListView) findViewById(com.free.hindi.shayari.R.id.listView);

        adapter = new ListAdapter(this, new ParseQueryAdapter.QueryFactory<ParseObject>() {

            public ParseQuery<ParseObject> create() {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(mimage);

                return query;
            }
        }, mimage);
        adapter.setTextKey("shayari_name");

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ShayarilistActivity.this, ShowShayariActivity.class);
                sid = adapter.getItem(position).getObjectId();
                System.out.println(" set on item click listener call" + adapter.getItem(position).getObjectId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        startAppAd.onResume();
    }
}
