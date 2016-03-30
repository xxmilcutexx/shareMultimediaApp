package com.example.app.azni.sharemultimediaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class EmailActivity extends AppCompatActivity{

    Button buttonSend;
    EditText textTo;
    EditText textSubject;
    EditText textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        textTo = (EditText) findViewById(R.id.emailTo);
        textSubject = (EditText) findViewById(R.id.textSubject);
        textMessage = (EditText) findViewById(R.id.textMesssage);

        buttonSend.setOnClickListener(new OnClickListener() {

            String to = textTo.getText().toString();
            String subject = textSubject.getText().toString();
            String message = textMessage.getText().toString();

            @Override
            public void onClick(View v) {

                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                email.putExtra(Intent.EXTRA_EMAIL, new String []{to});
                email.putExtra(Intent.EXTRA_SUBJECT,new String []{subject} );
                email.putExtra(Intent.EXTRA_TEXT,new String []{message});
                startActivity(Intent.createChooser(email,"Choose an Email client :"));

            }
        });
    }

    public void Undo (View v)
    {
//        Intent launchNewIntent = new Intent(EmailActivity.this,MainActivity.class);
//
//        startActivityForResult(launchNewIntent);
//        finish();
        super.onBackPressed();
    }
}