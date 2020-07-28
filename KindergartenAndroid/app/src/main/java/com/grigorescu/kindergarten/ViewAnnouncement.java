package com.grigorescu.kindergarten;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ViewAnnouncement extends AppCompatActivity {

    private TextView titleview,textview;
    Animation bganimopen,bganimclose,frombottom;
    ImageView bgapp;
    ConstraintLayout card;

    public void onBackPressed()
    {
        bgapp.startAnimation(bganimclose);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(ViewAnnouncement.this, AnnouncementsPage.class);
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
        setContentView(R.layout.activity_view_announcement);

        titleview = (TextView) findViewById(R.id.specifictitle);
        textview = (TextView)findViewById(R.id.specifictext);

        bgapp = (ImageView)findViewById(R.id.bgapp);
        card = (ConstraintLayout)findViewById(R.id.card);

        bganimopen = AnimationUtils.loadAnimation(this, R.anim.bganim);
        bganimclose = AnimationUtils.loadAnimation(this, R.anim.bganimclose);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        bgapp.startAnimation(bganimclose);
        bgapp.startAnimation(bganimopen);
        card.startAnimation(frombottom);

        String title = getIntent().getStringExtra("title");
        String text = getIntent().getStringExtra("text");
        titleview.setText(title);
        textview.setText(text);
        //Toast.makeText(getApplicationContext(), *CEVA DE AFISAT* , Toast.LENGTH_SHORT).show();
    }



}
