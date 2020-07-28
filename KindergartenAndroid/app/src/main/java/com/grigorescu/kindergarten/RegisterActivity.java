package com.grigorescu.kindergarten;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, password, confirmpassword, code, fname, lname, phone, address;
    private Button registerbutton;
    private RequestQueue requestQueue;
    private static final String URL = new ReadIP().getIP()+"/user_control.php";
    private StringRequest stringRequest;
    private TextView goToLogin;
    private ProgressBar loading;

    Animation bganimopen, bganimclose;
    ImageView bgapp;

    @Override
    public void onBackPressed()
    {
        bgapp.startAnimation(bganimclose);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(RegisterActivity.this, CommonMenu.class);
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
        setContentView(R.layout.activity_register);

        bganimopen = AnimationUtils.loadAnimation(this, R.anim.bganim);
        bganimclose = AnimationUtils.loadAnimation(this, R.anim.bganimclose);

        loading = (ProgressBar)findViewById(R.id.loading);
        email = (EditText)findViewById(R.id.txtemailregister);
        password = (EditText)findViewById(R.id.txtpasswordregister);
        confirmpassword = (EditText)findViewById(R.id.txtconfirmpasswordregister);
        code = (EditText)findViewById(R.id.txtcoderegister);
        fname = (EditText)findViewById(R.id.txtfirstname);
        lname = (EditText)findViewById(R.id.txtlastname);
        phone = (EditText)findViewById(R.id.txtphone);
        address = (EditText)findViewById(R.id.txtaddress);
        registerbutton = (Button)findViewById(R.id.btnregister);
        bgapp = (ImageView)findViewById(R.id.bgapp);

        bgapp.startAnimation(bganimopen);

        goToLogin = (TextView)findViewById(R.id.alreadyaccount);
        goToLogin.setClickable(true);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bgapp.startAnimation(bganimclose);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });


        requestQueue = Volley.newRequestQueue(this);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                loading.setVisibility(View.VISIBLE);
                                registerbutton.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                finish();
                                bgapp.startAnimation(bganimclose);
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("failed"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            registerbutton.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError{
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("emailR", email.getText().toString());
                        hashMap.put("passwordR", password.getText().toString());
                        hashMap.put("confirmedpassword", confirmpassword.getText().toString());
                        hashMap.put("code", code.getText().toString());
                        hashMap.put("fname",fname.getText().toString());
                        hashMap.put("lname",lname.getText().toString());
                        hashMap.put("phone", phone.getText().toString());
                        hashMap.put("address", address.getText().toString());

                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }
}
