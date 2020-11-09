package com.deepak.isoldier;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
GridView gridView;
ArrayList text_list;
ArrayList color;

LinearLayout updte_linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
gridView =(GridView)findViewById(R.id.main_gridview);
updte_linear =(LinearLayout)findViewById(R.id.linear_update);

        setListView();



        setGridView();


        setGridView_click_listner();
    }




    public void setListView()
    {
        text_list = new ArrayList<>();
        color = new ArrayList<>();





        color.add(R.drawable.history);
        color.add(R.drawable.document);
        color.add(R.drawable.eligible);
        color.add(R.drawable.operation3);
        color.add(R.drawable.syllabus);
        color.add(R.drawable.sample_papers);
        color.add(R.drawable.fitness_test);
        color.add(R.drawable.ask_question);
        color.add(R.drawable.more_link);
        color.add(R.drawable.history_2);


        text_list.add("History");
        text_list.add("Documents");
        text_list.add("Eligibility");
        text_list.add("operations");
        text_list.add("Syllabus");
        text_list.add("Sample Papers");
        text_list.add("Fitness Test");
        text_list.add("FAQ");
        text_list.add("More Links");



    }


    public void setGridView_click_listner()
    {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = null;
                if(i==1||i==2)
                {
                    intent = new Intent(getApplicationContext(),Activity3_webview.class);
                    intent.putExtra("position",0);

                }

                else if(i==(text_list.size()-1))
//                    intent = new Intent(getApplicationContext(),Post_update_activity.class);
                    show_dialog();
                else {
                    intent = new Intent(getApplicationContext(),Activity2.class);

                }
                if(intent!=null)
                {
                intent.putExtra("name", (String) gridView.getItemAtPosition(i).toString().toLowerCase());
                intent.putExtra("topic", (String) gridView.getItemAtPosition(i).toString());
startActivity(intent);}

            }
        });

        updte_linear.setBackground(Helper_class.set_gradient("#000000","#F1B215",20, GradientDrawable.Orientation.LEFT_RIGHT));
        updte_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//startActivity(new Intent(view.getContext(),Update_activity.class));
            }
        });




    }


public  void setGridView()
{

    gridView.setLayoutMode(ViewGroup.LAYOUT_MODE_CLIP_BOUNDS);

    final ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.grid_item, text_list) {
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, container, false);
            }
            CardView cardView= (CardView)convertView.findViewById(R.id.grid_cardview);

            cardView.setCardBackgroundColor(Color.YELLOW);
            ColorDrawable colorDrawable = new ColorDrawable();
            colorDrawable.setColor(Color.YELLOW);
            cardView.setBackground(Helper_class.set_gradient("#17DE2C","#17DE2C",150, GradientDrawable.Orientation.LEFT_RIGHT));
        //    cardView.setBackground(getResources().getDrawable(R.drawable.circle1));
            TextView title = (TextView) convertView.findViewById(R.id.grid_textview);
            title.setTextColor(Color.BLACK);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_imageView);
         //   imageView.setBackground(getResources().getDrawable(R.drawable.circle1));
            imageView.setImageResource((Integer) color.get(position));
            //   imageView.setBackgroundColor( color.get(position));
            title.setText((CharSequence) text_list.get(position));

            return convertView;
        }
    };
    gridView.setAdapter(arrayAdapter);
}
public void show_dialog()
{
    LayoutInflater factory = LayoutInflater.from(this);
    final View custom_dialog_layout = factory.inflate(R.layout.more_links, null);
    final AlertDialog dialog = new AlertDialog.Builder(this).create();
    dialog.setView(custom_dialog_layout);

    Button privacy = (Button)custom_dialog_layout.findViewById(R.id.btn_policy);
    Button share = (Button)custom_dialog_layout.findViewById(R.id.share);
    Button more_apps = (Button)custom_dialog_layout.findViewById(R.id.more_apps);
privacy.setBackground(btn_background("#C832D1"));
    privacy.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          dialog.dismiss();
            WebView webView = new WebView(view.getContext());
            final AlertDialog dialog2 = new AlertDialog.Builder(view.getContext()).create();
            dialog2.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog2.dismiss();
                }
            });
            webView.loadUrl("file:///android_asset/policy.html");
            dialog2.setView(webView);
            dialog2.show();


        }
    });
share.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Prepare for GD exam with free sample papers and updated with this app https://play.google.com/store/apps/details?id=com.deepak.isoldier");
        startActivity(Intent.createChooser(intent,"share using"));

    }
});
    more_apps.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.deepak.isoldier"));

             startActivity(intent);
        }
    });
    dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
    dialog.show();
}
public GradientDrawable btn_background(String color)
{
    return Helper_class.set_gradient(color,color,20, GradientDrawable.Orientation.LEFT_RIGHT);
}



}