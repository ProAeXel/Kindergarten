package com.grigorescu.kindergarten;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Notes extends AppCompatActivity {

    Animation bganimopen,bganimclose,frombottom;
    ImageView bgapp;
    ConstraintLayout inputs; // animation part
    private EditText title, announcement;
    private Button postbutton;

    public void onBackPressed()
    {
        bgapp.startAnimation(bganimclose);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(Notes.this, CommonMenu.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0,0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
        }, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom); // animation part
        bganimopen = AnimationUtils.loadAnimation(this, R.anim.bganim);
        bganimclose = AnimationUtils.loadAnimation(this, R.anim.bganimclose);

        bgapp = (ImageView)findViewById(R.id.bgapp); // animation part
        bgapp.startAnimation(bganimopen);

        inputs = (ConstraintLayout)findViewById(R.id.inputs); // animation part
        inputs.startAnimation(frombottom);


        title = (EditText)findViewById(R.id.txttitle);
        announcement = (EditText)findViewById(R.id.txtannouncement);
        postbutton = (Button)findViewById(R.id.postbtn);
    }
}
