package com.grigorescu.kindergarten;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnouncementsPage extends AppCompatActivity {

    private static final String URL = new ReadIP().getIP()+"/announcement_control.php";
    ListView listView;
    List<Demo> demoList;
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

                Intent intent = new Intent(AnnouncementsPage.this, CommonMenu.class);
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
        setContentView(R.layout.activity_announcements_page);

        bgapp = (ImageView)findViewById(R.id.bgapp);
        card = (ConstraintLayout)findViewById(R.id.card);

        bganimopen = AnimationUtils.loadAnimation(this, R.anim.bganim);
        bganimclose = AnimationUtils.loadAnimation(this, R.anim.bganimclose);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        bgapp.startAnimation(bganimopen);
        card.startAnimation(frombottom);

        listView = (ListView)findViewById(R.id.list1);
        demoList = new ArrayList<>();
        getAnnouncements();

    }


    public void getAnnouncements(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Demo demo = new Demo(jsonObject.getString("title"), jsonObject.getString("atext"));
                        demoList.add(demo);
                    }
                    ListViewAdapter listViewAdapter = new ListViewAdapter(demoList, getApplicationContext());
                    listView.setAdapter(listViewAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("check", "1");
                return hashMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
