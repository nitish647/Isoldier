package com.deepak.isoldier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Activity2 extends AppCompatActivity {
ListView listView;
TextView title_text;
LinearLayout linearLayout;
ArrayList list, list2,list3;
    Intent listview_intent = null;
    private InterstitialAd mInterstitialAd;
AdView banner_adview;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        listView = (ListView)findViewById(R.id.activity_2_listview);
        title_text =(TextView)findViewById(R.id.activity2_title_text);
        Intent intent = getIntent();
       name = intent.getStringExtra("name").toLowerCase();
      banner_adview = (AdView)findViewById(R.id.activity2_banner);
       title_text.setText(name.toUpperCase());
        set_array_list();

// google ads

        AdRequest adRequest = new AdRequest.Builder().build();
        banner_adview.loadAd(adRequest);

// google inter
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_inter));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
setmInterstitialAd_listner();

setListView_lisner();

if(listView.getCount()<1)
{
    title_text.setTextColor(Color.RED);
    title_text.setText("Coming Soon...");
}


    }
    public void set_array_list()


    {
        list3 = new ArrayList();
        list = new ArrayList();
list2 = new ArrayList();



        try {
            String[] array = getAssets().list(name);



            list2.addAll(Arrays.asList(getAssets().list(name)));

            for(int i = 0 ;i<list2.size();i++)
            {
                if(name.contains("sample"))
                    list2.set(i,list2.get(i).toString().replace(".pdf",""));
                else
                list2.set(i,list2.get(i).toString().replace(".html",""));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }





    }
    public void setListView_lisner()
    {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(name.contains("sample"))
                 listview_intent = new Intent(view.getContext(), Pdf_viewer_act.class);
                else
               listview_intent = new Intent(view.getContext(), Activity3_webview.class);
               listview_intent.putExtra("position",i);
                listview_intent.putExtra("topic",listView.getItemAtPosition(i).toString());
               listview_intent.putExtra("name",name);
                if(mInterstitialAd.isLoaded())
                    mInterstitialAd.show();
                else
                startActivity(listview_intent);
            }
        });

        ArrayAdapter arrayAdapter = new ArrayAdapter(this ,R.layout.listview_layout,list2)

        {
            public View getView(int position, View convertView, ViewGroup container) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout, container, false);
                }

                TextView textView = (TextView)convertView.findViewById(R.id.list_text);
                textView.setTextColor(Color.WHITE);
textView.setBackground(Helper_class.set_gradient("#BA278D","#AD38B1",25, GradientDrawable.Orientation.LEFT_RIGHT));
textView.setText((CharSequence) list2.get(position));
                return convertView;
        }};


        listView.setAdapter(arrayAdapter);



//        View view= getLayoutInflater().inflate(R.layout.listview_layout,null);
//
//
//        TextView textView = (TextView)view.findViewById(R.id.list_text);
//
//        textView.setTextColor(Color.GREEN);

    }
public void setmInterstitialAd_listner()
{
    mInterstitialAd.setAdListener(new AdListener(){
        @Override
        public void onAdClosed() {
            startActivity(listview_intent);
            super.onAdClosed();
        }
    });
}


}