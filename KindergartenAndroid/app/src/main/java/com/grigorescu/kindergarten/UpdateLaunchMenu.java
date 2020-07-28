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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

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

public class UpdateLaunchMenu extends AppCompatActivity {

    private EditText snack,maincourse,desert;
    private Button updatebutton;
    private ToggleButton menuNumber;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private String checked = "1";
    private static final String URL = new ReadIP().getIP()+"/foodmenu_control.php";
    ImageView bgapp;
    Animation bganimopen,bganimclose,frombottom;
    ConstraintLayout inputs;

    @Override
    public void onBackPressed()
    {
        bgapp.startAnimation(bganimclose);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(UpdateLaunchMenu.this, CommonMenu.class);
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
        setContentView(R.layout.activity_update_launch_menu);

        snack = (EditText)findViewById(R.id.snackinput);
        maincourse = (EditText)findViewById(R.id.maincourseinput);
        desert = (EditText)findViewById(R.id.desertinput);
        updatebutton = (Button)findViewById(R.id.updatebtn);
        menuNumber = (ToggleButton)findViewById(R.id.switchbtn);
        inputs = (ConstraintLayout)findViewById(R.id.inputs);

        bgapp = (ImageView)findViewById(R.id.bgapp);
        bganimopen = AnimationUtils.loadAnimation(this, R.anim.bganim);
        bganimclose = AnimationUtils.loadAnimation(this, R.anim.bganimclose);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom); // animation part
        bgapp.startAnimation(bganimopen);
        inputs.startAnimation(frombottom);

        requestQueue = Volley.newRequestQueue(this);

        menuNumber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checked = "2";
                }
                else {
                    checked = "1";
                }

            }
        });


        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
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
                        hashMap.put("snack", snack.getText().toString());
                        hashMap.put("maincourse", maincourse.getText().toString());
                        hashMap.put("desert", desert.getText().toString());
                        hashMap.put("menunumber", checked);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

    }
}
