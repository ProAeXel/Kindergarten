package com.grigorescu.kindergarten;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {

    /** ==============================================ANIMATION=================================================== **/
    ImageView bgapp; // animation part
    ConstraintLayout menus, textsplash, editprofile, food, announcement, chat, generatecode, educators; // animation part
    Animation frombottom, profilebtn; // animation part
    TextView fullName;
    /** ========================================================================================================== **/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom); // animation part
        profilebtn = AnimationUtils.loadAnimation(this, R.anim.profilebutton); // animation part

        bgapp = (ImageView)findViewById(R.id.bgapp); // animation part
        textsplash = (ConstraintLayout)findViewById(R.id.textsplash); // animation part
        editprofile = (ConstraintLayout)findViewById(R.id.editprofilebtn); // animation part
        menus = (ConstraintLayout) findViewById(R.id.menus); // animation part
        food = (ConstraintLayout)findViewById(R.id.food);
        announcement = (ConstraintLayout)findViewById(R.id.announcement);
        educators = (ConstraintLayout)findViewById(R.id.educators);
        generatecode = (ConstraintLayout)findViewById(R.id.generatecode);
        chat = (ConstraintLayout)findViewById(R.id.chat);

        bgapp.animate().translationY(-2300).setDuration(800).setStartDelay(300); // animation part

        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300); // animation part

        editprofile.startAnimation(frombottom); // animation part
        menus.startAnimation(frombottom); // animation part

        String fullname = getIntent().getStringExtra("firstname") + " " + getIntent().getStringExtra("lastname");
        fullName = (TextView)findViewById(R.id.fullNameWelcome);
        fullName.setText(fullname);

    }
    
    public void openAnnouncementsPage(View view) {
        startActivity(new Intent(getApplicationContext(), AnnouncementsPage.class));
    }
    public void viewMenus(View view)
    {
        editprofile.startAnimation(profilebtn);
        bgapp.animate().translationY(0).setDuration(800).setStartDelay(300); /** animation part **/
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),ViewFoodMenus.class));
                bgapp.animate().translationY(-2300).setDuration(800).setStartDelay(300); /** animation part **/
            }
        }, 1000);
    }
    public void showEducators(View view)
    {
        startActivity(new Intent(getApplicationContext(),EducatorPage.class));
    }
    public void openEditProfile(View view)
    {
        editprofile.startAnimation(profilebtn);
        bgapp.animate().translationY(0).setDuration(800).setStartDelay(300); /** animation part **/
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                intent.putExtra("email", getIntent().getStringExtra("email"));
                intent.putExtra("rank", getIntent().getStringExtra("rank"));
                startActivity(intent);
                bgapp.animate().translationY(-2300).setDuration(800).setStartDelay(300); /** animation part **/
            }
        }, 1000);
    }
}
