package com.ar.qr.adhaarcard.adhaarcardqr;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    List<DataAttributes> msqliteStingList = new ArrayList<>();
    DataBAse dataBAse;
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);




        dataBAse = new DataBAse(Main2Activity.this);

        Cursor cursor = dataBAse.getListContents();
        empty=(TextView)findViewById(R.id.empty_view);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        linearLayout=(LinearLayout) findViewById(R.id.click2_1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Main2Activity.this,MainActivity.class);
                startActivity(i);
                finish();



            }
        });


        int i = 0;
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
               DataAttributes msqliteSting = new DataAttributes(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5),
                        cursor.getString(6), cursor.getString(7), cursor.getString(8),
                        cursor.getString(9),cursor.getString(10));
                msqliteStingList.add(i, msqliteSting);
                i++;
            }

        }
        if (msqliteStingList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(msqliteStingList);
        recyclerView.setAdapter(adapter);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Main2Activity.this);

        linearLayoutManager.getReverseLayout();
        recyclerView.setLayoutManager(linearLayoutManager);


    }

}
