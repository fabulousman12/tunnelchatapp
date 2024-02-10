package com.example.tunnelchat;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import pub.devrel.easypermissions.EasyPermissions;

public class splash extends AppCompatActivity {

    ImageView logo;
    TextView name,own1,own2;
    Animation Topani,bottomani;
    final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logoimg);
        name = findViewById(R.id.logonameimg);
        own1 = findViewById(R.id.ownone);
        own2 = findViewById(R.id.owntwo);



        Topani = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomani = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo.setAnimation(Topani);
        name.setAnimation(bottomani);
        own1.setAnimation(bottomani);
        own2.setAnimation(bottomani);
      if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_CONTACTS)) {
          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  Intent intent = new Intent(splash.this, login.class);
                  startActivity(intent);
                  finish();
              }
          }, 4000);
      }

        else{
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs permission to access contacts.",
                    PERMISSIONS_REQUEST_READ_CONTACTS,
                    Manifest.permission.READ_CONTACTS
            );
          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  Intent intent = new Intent(splash.this, login.class);
                  startActivity(intent);
                  finish();
              }
          }, 4000);
        }

    }
}