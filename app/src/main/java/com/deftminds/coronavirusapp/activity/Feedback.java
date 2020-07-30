package com.deftminds.coronavirusapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.deftminds.coronavirusapp.R;
import com.deftminds.coronavirusapp.model.Feedbags;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {
    EditText et_your_name,et_email,et_contact_number,et_enter_message;
    TextView title, tv_submit;
    ImageView iv_back;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        title = (TextView)findViewById(R.id.title);
        title.setText("Feedback");
        databaseReference = FirebaseDatabase.getInstance().getReference("Feedback");

        et_your_name = (EditText) findViewById(R.id.et_your_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_contact_number = (EditText) findViewById(R.id.et_contact_number);
        et_enter_message = (EditText) findViewById(R.id.et_enter_message);
        tv_submit =(TextView) findViewById(R.id.tv_submit);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_your_name.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String contact = et_contact_number.getText().toString().trim();
                String message = et_enter_message.getText().toString().trim();
                //checking if the value is provided
                if (TextUtils.isEmpty(name)|| TextUtils.isEmpty(email) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(message)) {
                    //if the value is not given displaying a toast
                    Toast.makeText(Feedback.this, "Please fill all fields.", Toast.LENGTH_LONG).show();

                } else {
                    //getting a unique id using push().getKey() method
                    //it will create a unique id and we will use it as the Primary Key for our Artist
                    String id = databaseReference.push().getKey();

                    //creating an Artist Object
                    Feedbags artist = new Feedbags(id, name, email,contact,message);

                    //Saving the Artist
                    databaseReference.child(id).setValue(artist);

                    //setting edittext to blank again
                    et_your_name.setText("");
                    et_email.setText("");
                    et_contact_number.setText("");
                    et_enter_message.setText("");

                    //displaying a success toast
                    Toast.makeText(Feedback.this, "Thank you for connecting with us. Your Feedback is precious for us.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
