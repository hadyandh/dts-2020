package com.hunhun.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnStore, btnGetall;
    private EditText etname;
    private DatabaseHelper databaseHelper;
    private TextView tvnames;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        tvnames = (TextView) findViewById(R.id.tvnames);
        etname = (EditText) findViewById(R.id.etname);

        btnStore = (Button) findViewById(R.id.btnstore);
        btnGetall = (Button) findViewById(R.id.btnGet);

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.addStudentDetail(etname.getText().toString());

                etname.setText("");

                Toast.makeText(MainActivity.this, "Stored Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        btnGetall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList = databaseHelper.getAllStudentsList();
                tvnames.setText("");
                for (int i = 0; i < arrayList.size(); i++){
                    tvnames.setText(tvnames.getText().toString()+"," +arrayList.get(i));
                }
            }
        });
    }
}