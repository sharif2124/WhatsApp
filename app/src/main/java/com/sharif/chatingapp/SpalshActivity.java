package com.sharif.chatingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sharif.chatingapp.databinding.ActivitySpalshBinding;

import static java.lang.Thread.sleep;

public class SpalshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spalsh);
        getSupportActionBar().hide();

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                dowork();
            }
        });

        thread.start();
    }
    private void dowork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent= new Intent(SpalshActivity.this,SignInActivity.class);
                startActivity(intent);
               SpalshActivity.this.finish();
            }
        }).start();


    }
}