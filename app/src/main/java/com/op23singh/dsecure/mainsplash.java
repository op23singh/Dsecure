package com.op23singh.dsecure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class mainsplash extends AppCompatActivity {
    Animation main_animation,main_animation2;
    ImageView imagehead;
    TextView txthead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsplash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        txthead=findViewById(R.id.txthead);
        imagehead=findViewById(R.id.imagehead);
        main_animation= AnimationUtils.loadAnimation(this,R.anim.main_animation);
        main_animation2= AnimationUtils.loadAnimation(this,R.anim.main_animation2);
        imagehead.setAnimation(main_animation);
        txthead.setAnimation(main_animation2);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mainsplash.this,MainActivity.class));
                finish();
            }
        },2000);
    }
}