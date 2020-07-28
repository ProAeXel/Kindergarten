package com.grigorescu.kindergarten;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CommonMenu extends AppCompatActivity {

    ImageView bgapp; // animation part
    ConstraintLayout menus, textsplash, editprofile, food, announcement, about, generatecode, educators, users, userannouncements, viewmenus, group, notes; // animation part
    Animation frombottom, profilebtn, bganimopen, bganimclose; // animation part
    TextView fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_menu);

        if (PreferenceData.getUserName(CommonMenu.this).length() == 0)
        {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

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
        users = (ConstraintLayout)findViewById(R.id.userlist);
        generatecode = (ConstraintLayout)findViewById(R.id.generatecode);
        about = (ConstraintLayout)findViewById(R.id.about);
        userannouncements = (ConstraintLayout)findViewById(R.id.announcements);
        viewmenus = (ConstraintLayout)findViewById(R.id.viewfood);
        group = (ConstraintLayout)findViewById(R.id.group);
        notes = (ConstraintLayout)findViewById(R.id.notes);

        textsplash.animate().alpha(0).setDuration(800).setStartDelay(300); // animation part

        editprofile.startAnimation(frombottom); // animation part
        menus.startAnimation(frombottom); // animation part
        bgapp.startAnimation(bganimopen);

        String fullname = PreferenceData.getUserFirstName(getApplication()) + " " + PreferenceData.getUserLastName(getApplication());
        fullName = (TextView)findViewById(R.id.fullNameWelcome);
        fullName.setText(fullname);

        hideAll();

        if(PreferenceData.getUserRank(CommonMenu.this).equals("admin")){
            adminMenu();
        }else if(PreferenceData.getUserRank(CommonMenu.this).equals("educator")){
            educatorMenu();
        }else {
            userMenu();
        }
    }

    public void adminMenu(){
        announcement.setVisibility(View.VISIBLE);
        generatecode.setVisibility(View.VISIBLE);
        food.setVisibility(View.VISIBLE);
        educators.setVisibility(View.VISIBLE);
        users.setVisibility(View.VISIBLE);
        about.setVisibility(View.VISIBLE);
    }

    public void educatorMenu(){
        group.setVisibility(View.VISIBLE);
        userannouncements.setVisibility(View.VISIBLE);
        educators.setVisibility(View.VISIBLE);
        about.setVisibility(View.VISIBLE);
        users.setVisibility(View.VISIBLE);
        notes.setVisibility(View.VISIBLE);
    }

    public void userMenu(){
        userannouncements.setVisibility(View.VISIBLE);
        viewmenus.setVisibility(View.VISIBLE);
        educators.setVisibility(View.VISIBLE);
        users.setVisibility(View.VISIBLE);
        about.setVisibility(View.VISIBLE);
        notes.setVisibility(View.VISIBLE);
    }

    private void hideAll(){
        food.setVisibility(View.INVISIBLE);
        announcement.setVisibility(View.INVISIBLE);
        educators.setVisibility(View.INVISIBLE);
        users.setVisibility(View.INVISIBLE);
        generatecode.setVisibility(View.INVISIBLE);
        about.setVisibility(View.INVISIBLE);
        userannouncements.setVisibility(View.INVISIBLE);
        viewmenus.setVisibility(View.INVISIBLE);
        group.setVisibility(View.INVISIBLE);
        notes.setVisibility(View.INVISIBLE);
    }

    public void writeAnnouncement(View view)
    {
        editprofile.startAnimation(profilebtn);
        bgapp.startAnimation(bganimclose);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), writeAnnouncement.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
                overridePendingTransition(0,0);
            }
        }, 1000);
    }
    public void updateLaunchMenu(View view)
    {
        editprofile.startAnimation(profilebtn);
        bgapp.startAnimation(bganimclose);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),UpdateLaunchMenu.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
                overridePendingTransition(0,0);
            }
        }, 1000);

    }
    public void generateCode(View view)
    {
        editprofile.startAnimation(profilebtn);
        bgapp.startAnimation(bganimclose);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),GenerateCode.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
                overridePendingTransition(0,0);
            }
        },1000);

    }
    public void showEducators(View view)
    {
        editprofile.startAnimation(profilebtn);
        bgapp.startAnimation(bganimclose);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),EducatorPage.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
                overridePendingTransition(0,0);
            }
        }, 1000);

    }
    public void showUsers(View view)
    {
        editprofile.startAnimation(profilebtn);
        bgapp.startAnimation(bganimclose);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), UsersPage.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
                overridePendingTransition(0,0);
            }
        }, 1000);

    }
    public void viewMenus(View view)
    {
        if(PreferenceData.getUserScholarship(getApplication()).equals("1")){
            Toast.makeText(getApplicationContext(), "You don't have permission to access this.", Toast.LENGTH_SHORT).show();
        }else {
            editprofile.startAnimation(profilebtn);
            bgapp.startAnimation(bganimclose);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(),ViewFoodMenus.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    finish();
                    overridePendingTransition(0,0);
                }
            }, 1000);
        }
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
                finish();
                overridePendingTransition(0,0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
        }, 1000);
    }

    public void openAnnouncementsPage(View view) {
        editprofile.startAnimation(profilebtn);
        bgapp.startAnimation(bganimclose);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), AnnouncementsPage.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
                overridePendingTransition(0,0);
            }
        },1000);

    }

    public void showAbout(View view){
        editprofile.startAnimation(profilebtn);
        bgapp.startAnimation(bganimclose);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), About.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
                overridePendingTransition(0,0);
            }
        },1000);
    }

    public void showNotes(View view){
        startActivity(new Intent(getApplicationContext(), Notes.class));
    }

    public void showGroup(View view){

    }

    @Override
    public void onBackPressed()
    {
        bgapp.startAnimation(bganimclose);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }
}
