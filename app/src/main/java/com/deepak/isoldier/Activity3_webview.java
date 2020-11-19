package com.deepak.isoldier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.firestore.Query;

import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Activity3_webview extends AppCompatActivity {
WebView webView;
ArrayList list;
TextView topic;
Button back_btn;
    private InterstitialAd mInterstitialAd;
    AdView banner_adview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Intent i = getIntent();
        list = new ArrayList();
        back_btn = (Button) findViewById(R.id.back_btn);
        topic = (TextView)findViewById(R.id.webview_text);
        String name = i.getStringExtra("name").toLowerCase();
        String topic_text = i.getStringExtra("topic");
        int postition  = i.getIntExtra("position",0);
        webView = (WebView)findViewById(R.id.act3_webview);
        banner_adview = (AdView)findViewById(R.id.webview_banner);

        topic.setText(topic_text);
        topic.setTextColor(Color.WHITE);
        // google ads

        AdRequest adRequest = new AdRequest.Builder().build();
        banner_adview.loadAd(adRequest);

        //google inter ads
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_inter));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        setmInterstitialAd_listner();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        try {
         list.addAll(Arrays.asList(getAssets().list(name)))  ;
        } catch (IOException e) {
            e.printStackTrace();
        }
      webView.loadUrl("file:///android_asset/"+name+"/"+list.get(postition));


    }
    @Override
    public void onBackPressed() {
        if(mInterstitialAd.isLoaded())
            mInterstitialAd.show();
        else
            super.onBackPressed();
    }

    public void setmInterstitialAd_listner()
    {
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                onBackPressed();
                super.onAdClosed();
            }
        });
    }
}