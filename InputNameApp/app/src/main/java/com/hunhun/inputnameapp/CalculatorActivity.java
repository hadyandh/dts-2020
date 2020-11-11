package com.hunhun.inputnameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtResult;
    private EditText edtNum1, edtNum2;
    private Button btnTambah, btnKurang, btnKali, btnBagi, btnClear;

    private Double result, num1, num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        txtResult = findViewById(R.id.txtResult);
        edtNum1 = findViewById(R.id.edtFirstNum);
        edtNum2 = findViewById(R.id.edtSecondNum);
        btnTambah = findViewById(R.id.btnTambah);
        btnKurang = findViewById(R.id.btnKurang);
        btnKali = findViewById(R.id.btnKali);
        btnBagi = findViewById(R.id.btnBagi);
        btnClear = findViewById(R.id.btnClear);

        btnTambah.setOnClickListener(this);
        btnKali.setOnClickListener(this);
        btnBagi.setOnClickListener(this);
        btnKurang.setOnClickListener(this);

        btnClear.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTambah:
                tambah();
                break;

            case R.id.btnKurang:
                kurang();
                break;

            case R.id.btnBagi:
                bagi();
                break;

            case R.id.btnKali:
                kali();
                break;

            case R.id.btnClear:
                clear();
                break;
        }
    }

    public void tambah(){
        num1 = Double.parseDouble(edtNum1.getText().toString());
        num2 = Double.parseDouble(edtNum2.getText().toString());
        result = num1 + num2;

        txtResult.setText(Double.toString(result));
    }

    public void kurang(){
        num1 = Double.parseDouble(edtNum1.getText().toString());
        num2 = Double.parseDouble(edtNum2.getText().toString());
        result = num1 - num2;

        txtResult.setText(Double.toString(result));
    }

    public void kali(){
        num1 = Double.parseDouble(edtNum1.getText().toString());
        num2 = Double.parseDouble(edtNum2.getText().toString());
        result = num1 * num2;

        txtResult.setText(Double.toString(result));
    }

    public void bagi(){
        num1 = Double.parseDouble(edtNum1.getText().toString());
        num2 = Double.parseDouble(edtNum2.getText().toString());
        result = num1 / num2;

        txtResult.setText(Double.toString(result));
    }

    public void clear(){
        edtNum1.setText("");
        edtNum2.setText("");
        txtResult.setText("0");
    }
}