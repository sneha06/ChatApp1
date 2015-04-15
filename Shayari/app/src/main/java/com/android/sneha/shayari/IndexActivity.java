package com.android.sneha.shayari;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class IndexActivity extends ActionBarActivity {

    ImageView mBewafa, mDard, mLove, mRomantic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mBewafa = (ImageView) findViewById(R.id.bewafa);
        mDard = (ImageView) findViewById(R.id.dard);
        mLove = (ImageView) findViewById(R.id.love);
        mRomantic = (ImageView) findViewById(R.id.romantic);

        mBewafa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, ShayarilistActivity.class);
                intent.putExtra("image", "bewafa");
                startActivity(intent);
            }
        });
        mDard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, ShayarilistActivity.class);
                intent.putExtra("image", "dard");
                startActivity(intent);
            }
        });
        mLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, ShayarilistActivity.class);
                intent.putExtra("image", "love");
                startActivity(intent);
            }
        });
        mRomantic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, ShayarilistActivity.class);
                intent.putExtra("image", "romantic");
                startActivity(intent);
            }
        });

    }


}
