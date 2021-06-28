package com.op23singh.dsecure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Phone extends AppCompatActivity {

    private EditText phonenumber;
    private Button verify;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        verify=findViewById(R.id.verify);
        phonenumber=findViewById(R.id.phonenumber);
        if(phonenumber.length()<10 || !Patterns.PHONE.matcher((CharSequence) phonenumber).matches()) {
            phonenumber.requestFocus();
            phonenumber.setError("Invalid Phonenumber");
            return;
        }
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true){

                }
                else{
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                }
            }
        });
    }
}