package com.hunhun.sqlliteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hunhun.sqlliteapp.helper.DbHelper;

public class AddEdit extends AppCompatActivity {

    EditText edtId, edtName, edtAddress;
    Button btnSubmit, btnCancel;

    DbHelper SQLite = new DbHelper(this);

    String id, name, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);

        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        name = getIntent().getStringExtra(MainActivity.TAG_NAME);
        address = getIntent().getStringExtra(MainActivity.TAG_ADDRESS);

        if (id == null || id == ""){
            setTitle("Add Data");
        } else {
            setTitle("Edit Text");
            edtId.setText(id);
            edtName.setText(name);
            edtAddress.setText(address);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (edtId.getText().toString().equals("")){
                        save();
                    } else {
                        edit();
                    }
                } catch (Exception e){
                    Log.e("Submit", e.toString());
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blank();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void blank(){
        edtName.requestFocus();
        edtId.setText(null);
        edtName.setText(null);
        edtAddress.setText(null);
    }

    private void save(){
        String sName = edtName.getText().toString();
        String sAddress = edtAddress.getText().toString();

        if (sName.equals("") || sName.equals(null) || sAddress.equals("") || sAddress.equals(null)){
            Toast.makeText(this, "Please input name or address", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.insert(sName.trim(), sAddress.trim());
            blank();
            finish();
        }
    }

    private void edit(){
        String sId = edtId.getText().toString();
        String sName = edtName.getText().toString();
        String sAddress = edtAddress.getText().toString();

        if (sName.equals("") || sName.equals(null) || sAddress.equals("") || sAddress.equals(null)){
            Toast.makeText(this, "Please input name or address", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.update(Integer.parseInt(sId.trim()), sName.trim(), sAddress.trim());
            blank();
            finish();
        }
    }
}