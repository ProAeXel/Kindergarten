package com.grigorescu.kindergarten;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ViewFoodMenus extends AppCompatActivity {

    private static final String URL = new ReadIP().getIP()+"/foodmenu_control.php";
    private TextView snack1,snack2,maincourse1,maincourse2,desert1,desert2;
    private Button choosemenu1, choosemenu2;
    private RequestQueue requestQueue;
    private boolean selected = false;
    Animation frombottom,bganimopen, bganimclose; // animation part
    ImageView bgapp;


    public void onBackPressed()
    {
        bgapp.startAnimation(bganimclose);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(ViewFoodMenus.this, CommonMenu.class);
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
        setContentView(R.layout.activity_view_food_menus);

        snack1 = (TextView)findViewById(R.id.snack1);
        maincourse1 = (TextView)findViewById(R.id.maincourse1);
        desert1 = (TextView)findViewById(R.id.desert1);
        choosemenu1 = (Button)findViewById(R.id.choosemenu1btn);

        snack2 = (TextView)findViewById(R.id.snack2);
        maincourse2 = (TextView)findViewById(R.id.maincourse2);
        desert2 = (TextView)findViewById(R.id.desert2);
        choosemenu2 = (Button)findViewById(R.id.choosemenu2btn);

        bgapp = (ImageView)findViewById(R.id.bgapp);
        bganimopen = AnimationUtils.loadAnimation(this, R.anim.bganim);
        bganimclose = AnimationUtils.loadAnimation(this, R.anim.bganimclose);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        bgapp.startAnimation(bganimopen);

        requestQueue = Volley.newRequestQueue(this);
        getMenus();
        choosemenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selected){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.names().get(0).equals("success")) {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                    selected = true;
                                } else {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams(){
                            HashMap<String, String> hashMap = new HashMap<String, String>();

                            hashMap.put("menunumber", "1");
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(getApplicationContext(), "You've already selected a menu ", Toast.LENGTH_SHORT).show();
                }
            }

        });

        choosemenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selected) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.names().get(0).equals("success")) {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                    selected = true;
                                } else {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            HashMap<String, String> hashMap = new HashMap<String, String>();

                            hashMap.put("menunumber", "2");
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(getApplicationContext(), "You've already selected a menu ", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }



    public String getMenus(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int nr = jsonObject.getInt("menunumber");
                        if(nr == 1){

                            snack1.setText(jsonObject.getString("snack"));
                            maincourse1.setText(jsonObject.getString("maincourse"));
                            desert1.setText(jsonObject.getString("desert"));
                        }else if(nr == 2){
                            snack2.setText(jsonObject.getString("snack"));
                            maincourse2.setText(jsonObject.getString("maincourse"));
                            desert2.setText(jsonObject.getString("desert"));
                        }

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
            protected Map<String, String> getParams()
            {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("check", "1");
                return hashMap;
            }
        };




        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return null;
    }


}
