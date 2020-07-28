package com.grigorescu.kindergarten;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenuEducator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_educator);
    }

    public void openAnnouncementsPage(View view) {
        startActivity(new Intent(getApplicationContext(), AnnouncementsPage.class));
    }

}
