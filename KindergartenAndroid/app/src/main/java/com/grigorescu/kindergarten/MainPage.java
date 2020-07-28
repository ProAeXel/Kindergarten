package com.grigorescu.kindergarten;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

public class MainPage extends AppCompatActivity {

    private static final String URL = new ReadIP().getIP()+"/announcement_control.php";
    private TextView cardView1Title, cardView2Title, cardView3Title, cardView1Text, cardView2Text, cardView3Text;
    List<Demo> demoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        demoList = new ArrayList<>();
        getAnnouncements();
        setAnnouncementContent();
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
            protected Map<String, String> getParams(){
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("check", "3");
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



    public void setAnnouncementContent(){

        cardView1Text = (TextView)findViewById(R.id.txtcardview1text);
        cardView1Title = (TextView)findViewById(R.id.txtcardview1title);
        cardView2Title = (TextView)findViewById(R.id.txtcardview2title);
        cardView3Title = (TextView)findViewById(R.id.txtcardview3title);
        cardView1Text = (TextView)findViewById(R.id.txtcardview1text);
        cardView2Text = (TextView)findViewById(R.id.txtcardview2text);
        cardView3Text = (TextView)findViewById(R.id.txtcardview3text);

        cardView1Title.setText(demoList.get(0).getTitle());
        cardView2Title.setText(demoList.get(1).getTitle());
        cardView3Title.setText(demoList.get(2).getTitle());
        cardView1Text.setText(demoList.get(0).getText());
        cardView2Text.setText(demoList.get(1).getText());
        cardView3Text.setText(demoList.get(2).getText());
        cardView1Text.setText(demoList.toString());

    }

    public void goToMenu(View view){
        String rank = getIntent().getStringExtra("rank");

        switch (rank){
            case "admin":
                startActivity(new Intent(getApplicationContext(), CommonMenu.class));
                break;
            case "user":
                startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
                break;
            case "educator":
                startActivity(new Intent(getApplicationContext(), MainMenuEducator.class));
                break;
        }
    }
}
