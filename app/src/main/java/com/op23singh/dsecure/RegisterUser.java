package com.op23singh.dsecure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricFragment;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.biometric.BiometricViewModel;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.security.identity.IdentityCredential;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;

import java.util.concurrent.Executor;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private ImageView registerimglogo;
    private EditText txtFirstName,txtLastName,txtEmail,txtregisterPassword,Phonenumber;
    private Button buttonregister;
    private ProgressBar registerprg;
    private String signature;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private ImageView fingerimage;
    private Boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        registerimglogo = findViewById(R.id.registerimglogo);
        //registerimglogo.setOnClickListener(this);
        registerprg=findViewById(R.id.registerprg);
        txtFirstName=findViewById(R.id.txtFirstName);
        txtLastName=findViewById(R.id.txtLastName);
        txtEmail=findViewById(R.id.txtEmail);
        txtregisterPassword=findViewById(R.id.txtregisterPassword);
        Phonenumber=findViewById(R.id.phonenum);
        buttonregister=findViewById(R.id.buttonRegister);
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    public Boolean startbio(){
        String status="false";
        BiometricManager bm = BiometricManager.from(RegisterUser.this);
//        switch (bm.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG| BiometricManager.Authenticators.DEVICE_CREDENTIAL)){
//            case BiometricManager.BIOMETRIC_SUCCESS:
//                System.out.println("have scanner ");
//        }
//        switch (bm.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL | BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
//            case BiometricManager.BIOMETRIC_SUCCESS:
//                Toast.makeText(this,"success found",Toast.LENGTH_SHORT).show();
//                break;
//            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
//                Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show();
//                break;
//            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
//                Toast.makeText(this, "biometric senser is currently unavailable", Toast.LENGTH_SHORT).show();
//                break;
//            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
//                Toast.makeText(this, "your device don't have any fingerprint enrolled ,please check your security setttings", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                System.out.println("switch case is default");
//        }
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(RegisterUser.this, "Authentication error try again", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                setvalue(true);
                super.onAuthenticationSucceeded(result);
            }
            @Override
            public void onAuthenticationFailed() {
                Toast.makeText(RegisterUser.this, "Authentication failed try again", Toast.LENGTH_SHORT).show();
                super.onAuthenticationFailed();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("using biometric authentication")
                .setNegativeButtonText("using userId and password")
                .build();
        biometricPrompt.authenticate(promptInfo);
        return flag;
    }

    private void setvalue(boolean b) {
        flag=b;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerimglogo:
              //  startActivity(new Intent(this,main_Page.class));
                break;
            case R.id.buttonRegister:
                registerUser();
                break;
            default:
                break;
        }
    }

    private void registerUser() {
        String email = txtEmail.getText().toString().trim();
        String FirstName = txtFirstName.getText().toString().trim();
        String LastName = txtLastName.getText().toString().trim();
        String registerPassword = txtregisterPassword.getText().toString().trim();
        String phonenumber = Phonenumber.getText().toString().trim();
        if (FirstName.isEmpty()) {
            txtFirstName.setError("Please enter Firstname");
            txtFirstName.requestFocus();
            return;
        }
        if (LastName.isEmpty()) {
            txtLastName.setError("Please enter LastName");
            txtLastName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            txtEmail.setError("Please enter your email");
            txtEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Please enter a valid Email");
            txtEmail.requestFocus();
            return;
        }
        if (registerPassword.isEmpty()) {
            txtregisterPassword.setError("Password is required!");
            txtregisterPassword.requestFocus();
            return;
        }
        if (registerPassword.length() < 6) {
            txtregisterPassword.setError("Password must be of minimum 6 characters");
            txtregisterPassword.requestFocus();
            return;
        }
        if(phonenumber.length()<10 | phonenumber.length()>10){
            Phonenumber.setError("Please enter a valid phone number");
            Phonenumber.requestFocus();
            return ;
        }
        registerprg.setVisibility(View.VISIBLE);
        if (startbio()) {
            mAuth.createUserWithEmailAndPassword(email, registerPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(FirstName, LastName, email, phonenumber);
                                    FirebaseDatabase.getInstance().getReference()
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                user.sendEmailVerification();
                                                if (user.isEmailVerified()) {
                                                    Toast.makeText(RegisterUser.this, "user registered successfully", Toast.LENGTH_LONG).show();
                                                    registerprg.setVisibility(View.GONE);
                                                    startActivity(new Intent(RegisterUser.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                                                } else {
                                                    Toast.makeText(RegisterUser.this, "user registered successfully", Toast.LENGTH_LONG).show();
                                                    registerprg.setVisibility(View.GONE);
                                                    startActivity(new Intent(RegisterUser.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                                                }
                                            } else {
                                                Toast.makeText(RegisterUser.this, "Failed to Register TryAgain", Toast.LENGTH_LONG).show();
                                                registerprg.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                            } else {
                                Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_LONG).show();
                                registerprg.setVisibility(View.GONE);
                            }
                        }
                    });
        }
            else {
                registerprg.setVisibility(View.GONE);
                //Toast.makeText(RegisterUser.this,"this email is already registered with us",Toast.LENGTH_SHORT).show();
            }
        }
}