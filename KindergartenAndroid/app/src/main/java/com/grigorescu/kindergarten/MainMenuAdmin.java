/*
package com.grigorescu.kindergarten;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainMenuAdmin extends AppCompatActivity {

    */
/** ==============================================ANIMATION=================================================== **//*

    ImageView bgapp; // animation part
    ConstraintLayout menus, textsplash, editprofile, food, announcement, chat, generatecode, educators; // animation part
    Animation frombottom, profilebtn, bganimopen, bganimclose; // animation part
    TextView fullName;
    */
/** ========================================================================================================== **//*


    @Override
    public void onBackPressed()
    {
        this.finish();
        Intent intent = new Intent(MainMenuAdmin.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_admin);


        */
/** ==============================================ANIMATION=================================================== **//*

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom); // animation part
        profilebtn = AnimationUtils.loadAnimation(this, R.anim.profilebutton); // animation part
        bganimopen = AnimationUtils.loadAnimation(this, R.anim.bganim);
        bganimclose = AnimationUtils.loadAnimation(this, R.anim.bganimclose);

        bgapp = (ImageView)findViewById(R.id.bgapp); // animation part

        textsplash = (ConstraintLayout)findViewById(R.id.textsplash); // animation part
        editprofile = (ConstraintLayout)findViewById(R.id.editprofilebtn); // animation part
        menus = (ConstraintLayout) findViewById(R.id.menus); // animation part
        food = (ConstraintLayout)findViewById(R.id.food);
        announcement = (ConstraintLayout)findViewById(R.id.announcement);
        educators = (ConstraintLayout)findViewById(R.id.educators);
        generatecode = (ConstraintLayout)findViewById(R.id.generatecode);
        chat = (ConstraintLayout)findViewById(R.id.chat);

        textsplash.animate().alpha(0).setDuration(800).setStartDelay(300); // animation part

        editprofile.startAnimation(frombottom); // animation part
        menus.startAnimation(frombottom); // animation part
        bgapp.startAnimation(bganimopen);

        String fullname = getIntent().getStringExtra("firstname") + " " + getIntent().getStringExtra("lastname");
        fullName = (TextView)findViewById(R.id.fullNameWelcome);
        fullName.setText(fullname);
        */
/** ========================================================================================================== **//*

    }

    public void writeAnnouncement(View view)
    {
        editprofile.startAnimation(profilebtn);
        bgapp.startAnimation(bganimclose);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(getApplicationContext(), writeAnnouncement.class));
                bgapp.startAnimation(bganimopen);
            }
        }, 1000);
    }
    public void updateLaunchMenu(View view)
    {
        startActivity(new Intent(getApplicationContext(),UpdateLaunchMenu.class));
    }
    public void generateCode(View view)
    {
        startActivity(new Intent(getApplicationContext(),GenerateCode.class));
    }
    public void showEducators(View view)
    {
        startActivity(new Intent(getApplicationContext(),EducatorPage.class));
    }
    public void showUsers(View view)
    {
        startActivity(new Intent(getApplicationContext(), UsersPage.class));
    }
    public void openEditProfile(View view)
    {
        editprofile.startAnimation(profilebtn);
        bgapp.startAnimation(bganimclose);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                intent.putExtra("email", getIntent().getStringExtra("email"));
                intent.putExtra("rank", getIntent().getStringExtra("rank"));
                startActivity(intent);
                bgapp.startAnimation(bganimopen);
            }
        }, 1000);
    }

}
*/
