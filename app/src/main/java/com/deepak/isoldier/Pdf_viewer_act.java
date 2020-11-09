package com.deepak.isoldier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.pdfview.PDFView;

public class Pdf_viewer_act extends AppCompatActivity {
PDFView pdfView;
    AdView banner_adview;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer_act);
        pdfView =(PDFView)findViewById(R.id.pdfviewer);
        banner_adview = (AdView)findViewById(R.id.pdf_banner);

        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String topic = i.getStringExtra("topic");


        pdfView.fromAsset(name+"/"+topic+".pdf").show();



        // google ads

        AdRequest adRequest = new AdRequest.Builder().build();
        banner_adview.loadAd(adRequest);


        //google inter ads
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_inter));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        setmInterstitialAd_listner();
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