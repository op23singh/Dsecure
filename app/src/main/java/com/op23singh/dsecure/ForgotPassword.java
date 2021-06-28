package com.op23singh.dsecure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText txtForgetEmail;
    private Button btnresetpassword;
    private ProgressBar prgbare;
    FirebaseAuth auth;//we did not take mauth since it is made public and accessible to all
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtForgetEmail=findViewById(R.id.txtforgetEmail);
        btnresetpassword=findViewById(R.id.btnresetpassword);
        prgbare=findViewById(R.id.prgbare);
        auth= FirebaseAuth.getInstance();
        btnresetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    private void resetPassword(){
        String email=txtForgetEmail.getText().toString().trim();
        if(email.isEmpty()){
            txtForgetEmail.setError("Enter your email");
            txtForgetEmail.requestFocus();
            return ;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtForgetEmail.setError("Please enter valid email address");
            txtForgetEmail.requestFocus();
            return ;
        }
        prgbare.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Please check your email to reset your password",Toast.LENGTH_LONG).show();
                    prgbare.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(ForgotPassword.this,"Something went wrong ",Toast.LENGTH_LONG).show();
                    prgbare.setVisibility(View.GONE);
                }
            }
        });
    }
}