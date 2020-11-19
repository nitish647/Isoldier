package com.deepak.isoldier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.FtsOptions;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Update_activity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView ;
    TextView test_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        test_text = (TextView)findViewById(R.id.test_textview);
        recyclerView  =(RecyclerView)findViewById(R.id.update_recycler);
      //  db.document("moneyvio/updates").get().getResult().getData();
   //     CollectionReference collectionReference = db.collection("isoldier");
        test_text.append("hjashjashj");
        DocumentReference documentReference =  db.collection("isoldier").document("test");
db.collection("isoldier").orderBy("test", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        QueryDocumentSnapshot queryDocumentSnapshots = null;
        if(task.isComplete())

        {

            List<String> list = new ArrayList<>();
            for (QueryDocumentSnapshot document : task.getResult()) {
                list.add(document.getId());

            }
            test_text.append(list.toString());
//  for (QueryDocumentSnapshot document : task.getResult()) {
//
//                test_text.append("doc "+document.getData().toString());
//
//            }



        }
        else

            test_text.setText("not successfull");

    }}).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        test_text.setText(e.toString());
    }
});

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot snapshot = task.getResult();
                    if(snapshot.exists())
                    {

                        HashMap hashMap = new HashMap(snapshot.getData());

                        try {

                            JSONObject jsonObject = new JSONObject();
                            JSONArray jsonArray = new JSONArray(hashMap.values());
                            for (int i = 0;i<jsonArray.length();i++)
                            {    jsonObject =  jsonArray.getJSONObject(i);
                         //       test_text.append("hashmap :"+jsonObject.getString("url"));

                            }
//                            JSONObject jsonObject = new JSONObject( hashMap.get("qwerty1").toString());
                         //   HashMap hashMap1 = new HashMap((Map) hashMap.get("qwerty1"));
                          RecyclerviewAdaper_updates recyclerviewAdaper_updates = new RecyclerviewAdaper_updates(getApplicationContext(),hashMap);
                          recyclerView.setAdapter(recyclerviewAdaper_updates);

                          recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                          test_text.append("hasmap keys :"+hashMap.keySet() +hashMap.keySet().size());



                          //  jsonObject.getString("value");
                        //    Helper_class.show_toast(getApplicationContext(), "value "+jsonObject.getString("value").toString());

                        } catch (Exception e) {
                            Helper_class.show_toast(getApplicationContext(), "exception "+e.toString());

                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        Helper_class.show_toast(getApplicationContext(),"error snapshot does not exists ");

                    }
                }
                else {
                    task.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Helper_class.show_toast(getApplicationContext(),"not successful "+e.toString());
                        }
                    });

                }
            }
        });

    }
}