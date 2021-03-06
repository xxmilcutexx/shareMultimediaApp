package com.example.app.azni.sharemultimediaapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class PhonebookActivity extends AppCompatActivity {
    //    public final static String EXTRA_MESSAGE = "MESSAGE";
//    final private int REQUEST_CODE_ASK_PERMISSIONS = 201;
    DBHelper mydb;
    CustomListViewAdapter listDataAdapter;
    ListView listView;
    Cursor cursor;
    private ListView obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaycontact);

        mydb = new DBHelper(this);

        cursor = mydb.getAllContacts();
        listView = (ListView) findViewById(R.id.listView1);
        listDataAdapter = new CustomListViewAdapter(getApplicationContext(), R.layout.list_row_layout);
        listView.setAdapter(listDataAdapter);

        if (cursor.moveToFirst()) {
            do {

                String id, name, phoneNum, date;
                id = cursor.getString(0);
                name = cursor.getString(1);
                phoneNum = cursor.getString(2);
                date = cursor.getString(6);

                DataProvider dataProvider = new DataProvider(id, name, date, phoneNum);
                listDataAdapter.add(dataProvider);
            } while (cursor.moveToNext());
        }

//        listView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                int id_To_Search = arg2 + 1;
//
//                Bundle dataBundle = new Bundle();
//                dataBundle.putInt("id", id_To_Search);
//
//                Intent intent = new Intent(getApplicationContext(), DisplayContact.class);
//
//                intent.putExtras(dataBundle);
//                startActivity(intent);
//            }
//        });


//        ArrayList array_list = mydb.getAllContacts();
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);
//        obj = (ListView) findViewById(R.id.listView1);
//        obj.setAdapter(arrayAdapter);
//
//
//        obj.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                int id_To_Search = arg2 + 1;
//
//                Bundle dataBundle = new Bundle();
//                dataBundle.putInt("id", id_To_Search);
//
//                Intent intent = new Intent(getApplicationContext(), DisplayContact.class);
//
//                intent.putExtras(dataBundle);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.phonebook_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.item1:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(), DisplayContact.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}