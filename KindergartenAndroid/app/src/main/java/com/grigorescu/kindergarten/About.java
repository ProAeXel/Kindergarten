package com.grigorescu.kindergarten;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class About extends AppCompatActivity {

    private TextView address, description, phone, fax;
    private Button gps, update;
    private static final String URL = new ReadIP().getIP()+"/about_control.php";
    ImageView bgapp;
    Animation bganimopen,bganimclose,frombottom;
    ConstraintLayout card;

    @Override
    public void onBackPressed()
    {
        bgapp.startAnimation(bganimclose);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(About.this, CommonMenu.class);
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
        setContentView(R.layout.activity_about);

        address = (TextView)findViewById(R.id.addresstxt);
        description = (TextView)findViewById(R.id.abouttxt);
        phone = (TextView)findViewById(R.id.phonetxt);
        fax = (TextView)findViewById(R.id.faxtxt);
        gps = (Button)findViewById(R.id.gpsbtn);
        update = (Button)findViewById(R.id.updateaboutbtn);
        bgapp = (ImageView)findViewById(R.id.bgapp);
        bganimopen = AnimationUtils.loadAnimation(this, R.anim.bganim);
        bganimclose = AnimationUtils.loadAnimation(this, R.anim.bganimclose);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom); // animation part
        card = (ConstraintLayout)findViewById(R.id.card);

        bgapp.startAnimation(bganimopen);
        card.startAnimation(frombottom);

        getInfo();

        if(PreferenceData.getUserRank(this).equals("admin")){
            update.setVisibility(View.VISIBLE);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UpdateAbout.class));
            }
        });

        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String map = "http://maps.google.co.in/maps?q=" + jsonObject.getString("address");
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                            startActivity(intent);
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
                        hashMap.put("getinfo", "1");
                        return hashMap;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
    }

    public void finishAbout() {
        finish();
    }

    public String getInfo(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    description.setText(jsonObject.getString("description"));
                    address.setText(jsonObject.getString("address"));
                    phone.setText(jsonObject.getString("phone"));
                    fax.setText(jsonObject.getString("fax"));
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
                hashMap.put("getinfo", "1");
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return null;
    }


}
