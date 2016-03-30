package com.example.app.azni.sharemultimediaapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PhonebookActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBHelper mydb;
    Button btnMessage, btnCall, btnShare, btnAdd;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaycontact);

        mydb = new DBHelper(this);
        ArrayList array_list = mydb.getAllCotacts();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);

        obj = (ListView) findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(), DisplayContact.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
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


    public void message(View v)
    {
        String number = "100";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + number));
        intent.putExtra("sms_body", "The sms text");
        startActivity(intent);
    }

    //call method




//
//	class btnCallClicker implements View.OnClickListener {
//		public void onClick(View v) {
//			Log.d("Call:","------------------------------>");
//
//			String number = "100";
//			Intent intent = new Intent(Intent.ACTION_CALL);
//			intent.setData(Uri.parse("tel:" +number));
//			startActivity(intent);
////			Toast.makeText(getApplicationContext(), "Call Function ", Toast.LENGTH_SHORT).show();
//
//		}
//	}
//
//	class btnShareClicker implements View.OnClickListener {
//		public void onClick(View v) {
//			Log.d("Share:","------------------------------>");
//			Toast.makeText(getApplicationContext(), "Share Function ", Toast.LENGTH_SHORT).show();
//
//		}
//	}
//
//	class btnAddClicker implements View.OnClickListener {
//		public void onClick(View v) {
//			Log.d("Add:","------------------------------>");
//			Toast.makeText(getApplicationContext(), "Add New Contact ", Toast.LENGTH_SHORT).show();
//
//		}
//	}
}