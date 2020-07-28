package com.grigorescu.kindergarten;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity{


    private EditText email, password;
    private RequestQueue requestQueue;

    private static final String URL =  new ReadIP().getIP()+ "/user_control.php";
    private StringRequest stringRequest;
    public static String rank, firstName, lastName, scholarship, phone, address, image;

    /** ========================================ANIMATION=================================================**/
    ImageView bgapp,registerButton,loginButton;
    Animation frombottom, bganim, bganimclose, registerbtnanimation, loginbtnanimation; // animation part
    ConstraintLayout card; // animation part
    TextView logintext, registertext;
    /**==================================================================================================**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //region Animation

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        bganim = AnimationUtils.loadAnimation(this, R.anim.bganim);
        bganimclose = AnimationUtils.loadAnimation(this, R.anim. bganimclose);
        registerbtnanimation = AnimationUtils.loadAnimation(this, R.anim.registerbutton);
        loginbtnanimation = AnimationUtils.loadAnimation(this, R.anim.loginbutton);

        logintext = (TextView)findViewById(R.id.logintxt);
        registertext = (TextView)findViewById(R.id.registertxt);
        bgapp = (ImageView) findViewById(R.id.bgapp);
        registerButton = (ImageView)findViewById(R.id.registerbuttonanimate);
        loginButton = (ImageView)findViewById(R.id.loginbuttonanimate);
        card = (ConstraintLayout) findViewById(R.id.card);

        bgapp.startAnimation(bganim);
        registerButton.startAnimation(registerbtnanimation);
        loginButton.startAnimation(loginbtnanimation);
        card.startAnimation(frombottom);
        logintext.startAnimation(frombottom);
        registertext.startAnimation(frombottom);
        //endregion

        email = (EditText)findViewById(R.id.txtemail);
        password = (EditText)findViewById(R.id.txtpassword);

        requestQueue = Volley.newRequestQueue(this);

    }

    public void loginPerformed(View view){

        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    rank = jsonObject.getString("status");
                    firstName = jsonObject.getString("fname");
                    lastName = jsonObject.getString("lname");
                    scholarship = jsonObject.getString("scholarship");
                    address = jsonObject.getString("address");
                    phone = jsonObject.getString("phone");
                    image = jsonObject.getString("profilepicture");

                    if (rank.equals("admin")) {
                        Toast.makeText(getApplicationContext(), "Login successfully." , Toast.LENGTH_SHORT).show();
                        PreferenceData.setUserName(getApplicationContext(), email.getText().toString());
                        PreferenceData.setUserRank(getApplicationContext(), rank);
                        PreferenceData.setUserFirstName(getApplicationContext(), firstName);
                        PreferenceData.setUserLastName(getApplicationContext(), lastName);
                        PreferenceData.setUserEmail(getApplicationContext(), email.getText().toString());
                        PreferenceData.setUserAddress(getApplicationContext(), address);
                        PreferenceData.setUserPhone(getApplicationContext(), phone);
                        PreferenceData.setUserProfileImage(getApplicationContext(), image);
                        bgapp.startAnimation(bganimclose);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), CommonMenu.class);
                                intent.putExtra("rank", rank);
                                intent.putExtra("firstname", firstName);
                                intent.putExtra("lastname", lastName);
                                intent.putExtra("email", email.getText().toString());

                                finish();
                                startActivity(intent);
                                bgapp.startAnimation(bganim);
                                //bgapp.animate().translationY(-2300).setDuration(800).setStartDelay(300); /** animation part **/
                            }
                        }, 1000);

                    }else if(rank.equals("user")) {
                        Toast.makeText(getApplicationContext(), "Login successfully.", Toast.LENGTH_SHORT).show();
                        PreferenceData.setUserName(getApplicationContext(), email.getText().toString());
                        PreferenceData.setUserRank(getApplicationContext(), rank);
                        PreferenceData.setUserFirstName(getApplicationContext(), firstName);
                        PreferenceData.setUserLastName(getApplicationContext(), lastName);
                        PreferenceData.setUserEmail(getApplicationContext(), email.getText().toString());
                        PreferenceData.setUserScholarship(getApplicationContext(),scholarship);
                        PreferenceData.setUserAddress(getApplicationContext(), address);
                        PreferenceData.setUserPhone(getApplicationContext(), phone);
                        PreferenceData.setUserProfileImage(getApplicationContext(), image);
                        bgapp.startAnimation(bganimclose);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), CommonMenu.class);
                                intent.putExtra("rank", rank);
                                intent.putExtra("firstname", firstName);
                                intent.putExtra("lastname", lastName);
                                intent.putExtra("email", email.getText().toString());
                                startActivity(intent);
                                //bgapp.animate().translationY(-2300).setDuration(800).setStartDelay(300); /** animation part **/
                            }
                        }, 1000);

                    }else if(rank.equals("educator")){
                        Toast.makeText(getApplicationContext(), "Login successfully.", Toast.LENGTH_SHORT).show();
                        PreferenceData.setUserName(getApplicationContext(), email.getText().toString());
                        PreferenceData.setUserRank(getApplicationContext(), rank);
                        PreferenceData.setUserFirstName(getApplicationContext(), firstName);
                        PreferenceData.setUserLastName(getApplicationContext(), lastName);
                        PreferenceData.setUserEmail(getApplicationContext(), email.getText().toString());
                        PreferenceData.setUserAddress(getApplicationContext(), address);
                        PreferenceData.setUserPhone(getApplicationContext(), phone);
                        PreferenceData.setUserProfileImage(getApplicationContext(), image);
                        bgapp.startAnimation(bganimclose);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), CommonMenu.class);
                                intent.putExtra("rank", rank);
                                intent.putExtra("firstname", firstName);
                                intent.putExtra("lastname", lastName);
                                intent.putExtra("email", email.getText().toString());
                                startActivity(intent);
                                //bgapp.animate().translationY(-2300).setDuration(800).setStartDelay(300); /** animation part **/
                            }
                        }, 1000);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("email", email.getText().toString());
                hashMap.put("password", password.getText().toString());

                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void openRegisterPage(View view){
        bgapp.startAnimation(bganimclose);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                //bgapp.startAnimation(bganim);
            }
        }, 1000);

    }

}
