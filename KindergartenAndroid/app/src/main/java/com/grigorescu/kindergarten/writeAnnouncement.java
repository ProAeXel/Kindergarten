package com.grigorescu.kindergarten;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class writeAnnouncement extends AppCompatActivity {

    private EditText title, announcement;
    private Button postbutton;
    private static final String POST_URL = new ReadIP().getIP()+"/announcement_control.php";
    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    ConstraintLayout inputs; // animation part
    Animation frombottom, bganimopen, bganimclose; // animation part
    ImageView bgapp; // animation part

    @Override
    public void onBackPressed()
    {
        bgapp.startAnimation(bganimclose);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(writeAnnouncement.this, CommonMenu.class);
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
        setContentView(R.layout.activity_write_announcement);

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

        requestQueue = Volley.newRequestQueue(this);

        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringRequest = new StringRequest(Request.Method.POST, POST_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(), "SUCCESS:" + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                title.setText(null);
                                announcement.setText(null);
                            } else {
                                Toast.makeText(getApplicationContext(), "FAILED:" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
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
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("title", title.getText().toString());
                        hashMap.put("atext", announcement.getText().toString());

                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });


    }
}
