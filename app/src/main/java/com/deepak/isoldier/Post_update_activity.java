package com.deepak.isoldier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class Post_update_activity extends AppCompatActivity {
FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
EditText edit_title,edit_url,edit_description;
Button update_btn,color_btn,preview_btn;
Spinner spinner;
    ArrayList<Integer> color_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_update_activity);
edit_description  =(EditText)findViewById(R.id.edit_update_desc);
        edit_title =(EditText)findViewById(R.id.edit_update_title);
        edit_url  =(EditText)findViewById(R.id.edit_update_url);
        preview_btn =(Button)findViewById(R.id.preview_btn) ;
        update_btn = (Button)findViewById(R.id.btn_update);
        color_btn =(Button)findViewById(R.id.color_btn);
spinner =(Spinner)findViewById(R.id.color_spinner);
setSpinner();
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseFirestore.collection("isoldier").document("test").update(set_hashmap()).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Helper_class.show_toast(getApplicationContext(),"success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Helper_class.show_toast(getApplicationContext(),"failed "+e.toString());

                    }
                });


            }
        });

        preview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog();
            }
        });



    }
    public HashMap set_hashmap()
    {
        HashMap hashMap = new HashMap();
HashMap hashMap1 = new HashMap(0);

        hashMap1.put("title",edit_title.getText().toString());
        hashMap1.put("description",edit_description.getText().toString());
        hashMap1.put("url",edit_url.getText().toString());
        hashMap1.put("color",color_list.get(spinner.getSelectedItemPosition()));
        hashMap.put(edit_title.getText().toString(),hashMap1);

        return hashMap;
    }
    public void setSpinner()
    {
     color_list = new ArrayList<>();
        color_list.add(Color.BLACK);
        color_list.add(Color.RED);
        color_list.add(Color.BLUE);
        color_list.add(Color.MAGENTA);
        color_list.add(Color.CYAN);

        ArrayList<String> spinner_color_list = new ArrayList<>();

        spinner_color_list.add("Black");
        spinner_color_list.add("Red");
        spinner_color_list.add("Blue");
        spinner_color_list.add("Magneta");
        spinner_color_list.add("Cyan");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,spinner_color_list);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            color_btn.setBackgroundColor(color_list.get(spinner.getSelectedItemPosition()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void alertdialog()
    {
        LayoutInflater factory = LayoutInflater.from(this);
        final View custom_dialog_layout = factory.inflate(R.layout.recycler_update_items, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(custom_dialog_layout);

        ConstraintLayout constraintLayout = custom_dialog_layout.findViewById(R.id.item_contraint);
        TextView title_text  = custom_dialog_layout.findViewById(R.id.updte_title);
        TextView title_desc  = custom_dialog_layout.findViewById(R.id.updte_description);

        constraintLayout.setBackgroundColor(color_list.get(spinner.getSelectedItemPosition()));
        title_text.setText(edit_title.getText().toString());
        title_desc.setText(edit_description.getText().toString());
        dialog.getWindow().getDecorView().setLayoutParams(new LinearLayout.LayoutParams(800, 300));
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        dialog.show();

    }

}