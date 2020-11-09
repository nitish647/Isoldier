package com.deepak.isoldier;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class RecyclerviewAdaper_updates extends RecyclerView.Adapter<RecyclerviewAdaper_updates.MyviewHolder>{

Context context;
HashMap hashMap ;
ArrayList title_list= new ArrayList();
    ArrayList desc_list= new ArrayList();
    ArrayList color_list = new ArrayList();
    ArrayList url_list= new ArrayList();
JSONObject jsonObject  = new JSONObject();
public  RecyclerviewAdaper_updates (Context context,HashMap MhashMap)
{
this.context =context;
    this.hashMap = MhashMap;
    data_resolver();
}

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(context).inflate(R.layout.recycler_update_items,parent,false);

        return new MyviewHolder(view);




    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, final int position) {

    holder.title_text.setText((CharSequence) title_list.get(position));
    holder.desc_text.setText((CharSequence) desc_list.get(position));
    holder.update_imageview.setImageResource(R.drawable.update_icon2);

//    try {
//        holder.recycler_constraintLayout.setBackgroundColor((Integer) color_list.get(position));
//
//    }catch (Exception e)
//    {
//        holder.recycler_constraintLayout.setBackgroundColor( Color.BLACK);
//
//    }



        String url = (String) url_list.get(position);
        if(!url.contains("http"))
        url = "https://"+url;
        final Uri uri = Uri.parse(url);
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


try {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setData(uri);
    context.startActivity(intent);
}
catch (Exception e){
    Helper_class.show_toast(context,"error "+e.getMessage());
}

    }
});

    }

    @Override
    public int getItemCount() {
        return hashMap.keySet().size();

    }



    public static class MyviewHolder extends RecyclerView.ViewHolder {
ConstraintLayout  recycler_constraintLayout;
TextView title_text,desc_text;
ImageView update_imageview;
        public MyviewHolder(@NonNull View itemView) {

            super(itemView);
            update_imageview = (ImageView)itemView.findViewById(R.id.update_imageView);
            recycler_constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.item_contraint);
            title_text =(TextView)itemView.findViewById(R.id.updte_title);
            desc_text= (TextView)itemView.findViewById(R.id.updte_description);

        }
    }
    public void data_resolver()
    {
        title_list.addAll(hashMap.keySet());


        JSONObject jsonObject ;
        JSONArray jsonArray = new JSONArray(hashMap.values());
        for (int i = 0;i<jsonArray.length();i++)
        {
            try {
                jsonObject =  jsonArray.getJSONObject(i);
                title_list.addAll(Collections.singleton(jsonObject.getString("title")));
                desc_list.addAll(Collections.singleton(jsonObject.getString("description")));
                url_list.addAll(Collections.singleton(jsonObject.getString("url")));
                color_list.addAll(Collections.singleton(jsonObject.getInt("color")));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }





    }

}
