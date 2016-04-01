package com.example.app.azni.sharemultimediaapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;


public class DisplayContact extends AppCompatActivity {
    TextView name, phone, email, address, nickname;
    int id_To_Update = 0;
//    CharSequence cDate = DateFormat.getDateTimeInstance().format(new Date());
    //    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb;
    CharSequence time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook);

        name = (TextView) findViewById(R.id.nameID);
        phone = (TextView) findViewById(R.id.phoneID);
        email = (TextView) findViewById(R.id.emailID);
        address = (TextView) findViewById(R.id.addressID);
        nickname = (TextView) findViewById(R.id.nicknameID);
        time  = DateFormat.getDateTimeInstance().format(new Date());

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                String phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                String emai = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
                String addre = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_ADDRESS));
                String nickna = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NICKNAME));

                Log.d("rs", rs.toString());

                if (!rs.isClosed()) {
                    rs.close();

                    Log.d("!rsClosed!!!", "");
                }

                name.setText((CharSequence) nam);
                name.setFocusable(false);
                name.setClickable(false);

                phone.setText((CharSequence) phon);
                phone.setFocusable(false);
                phone.setClickable(false);

                email.setText((CharSequence) emai);
                email.setFocusable(false);
                email.setClickable(false);

                address.setText((CharSequence) addre);
                address.setFocusable(false);
                address.setClickable(false);

                nickname.setText((CharSequence) nickna);
                nickname.setFocusable(false);
                nickname.setClickable(false);

                Button b = (Button) findViewById(R.id.saveButton);
                b.setVisibility(View.INVISIBLE);

                //                set button invisible
                //read button's id
                Button bcall = (Button) findViewById(R.id.buttonCall);
                bcall.setVisibility(View.VISIBLE);
                Button bmessage = (Button) findViewById(R.id.buttonMessage);
                bmessage.setVisibility(View.VISIBLE);
                Button bshare = (Button) findViewById(R.id.buttonShare);
                bshare.setVisibility(View.VISIBLE);
                Button badd = (Button) findViewById(R.id.buttonAdd);
                badd.setEnabled(false);

                // TODO Message & Call Function by ID

//                Time time = new Time();
//                time.setToNow();
//
// String time = new SimpleDateFormat().format(new Date());
//
//                CharSequence cDate = DateFormat.getDateTimeInstance().format(new Date());

//                Calendar c = Calendar.getInstance();
//                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss a");
//                String time = sdf.format(c.getTime());

//                Toast.makeText(DisplayContact.this, "current time: " + time, Toast.LENGTH_SHORT).show();

//                Toast.makeText(DisplayContact.this, "current contact number:" + " " + phon, Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                getMenuInflater().inflate(R.menu.display_contact, menu);
            } else {
                getMenuInflater().inflate(R.menu.phonebook_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.Edit_Contact:

                Button b = (Button) findViewById(R.id.saveButton);
                b.setVisibility(View.VISIBLE);

//                set button invisible
                //read button's id
                Button bcall = (Button) findViewById(R.id.buttonCall);
                bcall.setVisibility(View.INVISIBLE);
                Button bmessage = (Button) findViewById(R.id.buttonMessage);
                bmessage.setVisibility(View.INVISIBLE);
                Button bshare = (Button) findViewById(R.id.buttonShare);
                bshare.setVisibility(View.INVISIBLE);
                Button badd = (Button) findViewById(R.id.buttonAdd);
                badd.setVisibility(View.INVISIBLE);


                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                phone.setEnabled(true);
                phone.setFocusableInTouchMode(true);
                phone.setClickable(true);

                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);

                address.setEnabled(true);
                address.setFocusableInTouchMode(true);
                address.setClickable(true);

                nickname.setEnabled(true);
                nickname.setFocusableInTouchMode(true);
                nickname.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), PhonebookActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                if (mydb.updateContact(id_To_Update, name.getText().toString(),
                        phone.getText().toString(), email.getText().toString(),
                        address.getText().toString(), nickname.getText().toString(),
                        time.toString())) {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), PhonebookActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mydb.insertContact(name.getText().toString(), phone.getText().toString(),
                        email.getText().toString(), address.getText().toString(),
                        nickname.getText().toString(), time.toString())) {
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), PhonebookActivity.class);
                startActivity(intent);
            }
        }
    }

    public void call(View v) {
        String number = "100";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));

        //if use sdkVersion 23, run this code to get permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.CALL_PHONE}, 0);
            return;
        }
        startActivity(intent);
//        Log.d("Invoke call maethod", "entered!!");
    }

    //message method
    public void message(View v) {
        //get v
//        Toast.makeText(DisplayContact.this, "value of v: " + " " + v, Toast.LENGTH_SHORT).show();
        String number = "100";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + number));
        intent.putExtra("sms_body", "The sms text");
        startActivity(intent);
    }
}