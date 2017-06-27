package com.example.andyyy.touristapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class MonumentDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monument_detail);


        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        TextView textView = (TextView) findViewById(R.id.textView);
        final TextView textViewDate = (TextView) findViewById(R.id.textView4);
        textView.setText(message);

        Switch switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    String currentDateTimeString ="Navštíveno dne " + DateFormat.getDateTimeInstance().format(new Date());
                    textViewDate.setText(currentDateTimeString);
                } else {
                    textViewDate.setText("");
                }

            }
        });
    }

    public void saveToDB() {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("Monument_DB.xls");
            Workbook workbook = Workbook.getWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(0);
            int row = sheet.getRows();
            String displayText = "";
            for (int i = 1; i < row; i++) {
                Cell cell = sheet.getCell(3, i);
                displayText = cell.getContents();
                cell = sheet.getCell(7, i);
                displayText = displayText + " - " + cell.getContents();

            }
        } catch(Exception e) {

        }
    }

}
