package com.example.andyyy.touristapp;

import android.app.LauncherActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class MonumentOverviewActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editText, editText1;
    Button buttonAddMon;
    ListView listView;
    ListAdapter listAdapter;
    ArrayList<String> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monument_overview_activity);

        editText = (EditText) findViewById(R.id.editText6);
        editText1 = (EditText) findViewById(R.id.editText2);
        buttonAddMon = (Button) findViewById(R.id.buttonCity);
        myDB = new DatabaseHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        listView = (ListView) findViewById(R.id.ListViewMonument);
        listItems = new ArrayList<>();
        Cursor data = myDB.getListContents();
        while (data.moveToNext()){
            listItems.add(data.getString(1));
            listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listItems);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Toast.makeText(view.getContext(),adapter.getItem(position),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MonumentOverviewActivity.this, MonumentDetailActivity.class);
                    String message = listAdapter.getItem(position).toString();
                    intent.putExtra("EXTRA_MESSAGE", message);
                    startActivityForResult(intent, 0);

                }
            });
        }*/
        order();

        buttonAddMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                String newEntry1 = editText1.getText().toString();
                if (editText.length() != 0) {
                    AddData(newEntry, newEntry1);
                    editText.setText("");
                } else{
                    Toast.makeText(MonumentOverviewActivity.this, "Musis vlozit text", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void AddData(String newEntry, String newEntry1){
        boolean insertData = myDB.addData(newEntry, newEntry1);

        if (insertData == true) {
            Toast.makeText(MonumentOverviewActivity.this, "Vlozeno", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MonumentOverviewActivity.this, "Neco se porouchalo", Toast.LENGTH_SHORT).show();
        }
    }

    public void order () {

       // EditText editTextCity = (EditText) findViewById(R.id.editTextCity);
        listView = (ListView) findViewById(R.id.ListViewMonument);
        final ArrayAdapter<String> adapter;
        listItems = new ArrayList<>();
        //Nahrani databaze z Excelu
        try{
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("Monument_DB.xls");
            Workbook workbook = Workbook.getWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(0);
            int row = sheet.getRows();
            String displayText = "";
            for(int i=1; i<row;i++){
                Cell cell = sheet.getCell(3,i);
                displayText = cell.getContents();
                cell = sheet.getCell(7,i);
                displayText = displayText + " - " + cell.getContents();
                listItems.add(displayText);
            }
            adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.txtitem, listItems);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   // Toast.makeText(view.getContext(),adapter.getItem(position),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MonumentOverviewActivity.this, MonumentDetailActivity.class);
                    String message = adapter.getItem(position).toString();
                    intent.putExtra("EXTRA_MESSAGE", message);
                    startActivityForResult(intent, 0);

                }
            });
       /*     editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                        initList();
                    }
                    else {
                    searchItem(s.toString());
                }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });*/
        }
        catch(Exception e){

        }
    }

    public void initList() {

    }
    public void searchItem(String textToSearch) {

    }
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
