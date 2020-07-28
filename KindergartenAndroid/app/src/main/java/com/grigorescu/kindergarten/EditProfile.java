package com.grigorescu.kindergarten;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class EditProfile extends AppCompatActivity {

    ImageView bgapp, profilePicture;
    Animation frombottom, bganimclose, bganimopen; // animation part
    ConstraintLayout menus;
    EditText firstName, lastName, phone, address, currentPassword, newPassword;
    Button saveButton, changePassword, profilePictureButton, logoutButton;
    TextView changepasswordtxt, manageprofiletxt, phonetxt, addresstxt, nametxt, name2txt;
    boolean changingPassword = false;

    private RequestQueue requestQueue;
    private static final String URL =  new ReadIP().getIP()+ "/user_control.php";
    private static String URL_IMG = new ReadIP().getIP()+ "/pictures/";
    private static String URL_IMG_HANDLER = new ReadIP().getIP() + "/upload.php";
    private StringRequest stringRequest;

    @Override
    public void onBackPressed()
    {
        bgapp.startAnimation(bganimclose);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(EditProfile.this, CommonMenu.class);
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
        setContentView(R.layout.activity_edit_profile);

        changepasswordtxt = (TextView)findViewById(R.id.changepasswordtxt);
        manageprofiletxt = (TextView)findViewById(R.id.manageprofiletxt);
        phonetxt = (TextView)findViewById(R.id.phonetxt);
        addresstxt = (TextView)findViewById(R.id.addresstxt);
        nametxt = (TextView)findViewById(R.id.nametxt);
        name2txt = (TextView)findViewById(R.id.name2txt);

        firstName = (EditText)findViewById(R.id.firstnameinput);
        lastName = (EditText)findViewById(R.id.lastnameinput);
        phone = (EditText)findViewById(R.id.phoneinput);
        address = (EditText)findViewById(R.id.addressinput);
        currentPassword = (EditText)findViewById(R.id.currentpasswordinput);
        newPassword = (EditText)findViewById(R.id.newpasswordinput);
        saveButton = (Button)findViewById(R.id.savebtn);
        changePassword = (Button)findViewById(R.id.changepasswordbtn);
        profilePictureButton = (Button)findViewById(R.id.editprofilepicturebtn);
        profilePicture = (ImageView)findViewById(R.id.profilepicture);
        logoutButton = (Button)findViewById(R.id.logoutbtn);

        //region Animation
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom); // animation part
        bgapp = (ImageView)findViewById(R.id.bgapp); // animation part
        bganimclose = AnimationUtils.loadAnimation(this, R.anim.bganimclose);
        bganimopen = AnimationUtils.loadAnimation(this, R.anim.bganim);
        menus = (ConstraintLayout) findViewById(R.id.menus); // animation part
        menus.startAnimation(frombottom); // animation part
        bgapp.startAnimation(bganimopen);
        //endregion

        //getInformation();
        hidePasswordInputs();
        loadProfilePicture(PreferenceData.getUserProfileImage(getApplicationContext()));
        setInformation();
        //editInformation();

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changingPassword = true;

                firstName.setVisibility(View.INVISIBLE);
                lastName.setVisibility(View.INVISIBLE);
                phone.setVisibility(View.INVISIBLE);
                address.setVisibility(View.INVISIBLE);
                manageprofiletxt.setVisibility(View.INVISIBLE);
                phonetxt.setVisibility(View.INVISIBLE);
                addresstxt.setVisibility(View.INVISIBLE);
                nametxt.setVisibility(View.INVISIBLE);
                name2txt.setVisibility(View.INVISIBLE);

                currentPassword.setVisibility(View.VISIBLE);
                newPassword.setVisibility(View.VISIBLE);
                changepasswordtxt.setVisibility(View.VISIBLE);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceData.setUserName(getApplication(), null);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(), "Your profile is now updated", Toast.LENGTH_SHORT).show();
                            } else if(jsonObject.names().get(0).equals("failed")){
                                Toast.makeText(getApplicationContext(), "Your current password does not match", Toast.LENGTH_SHORT).show();
                            }else {
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
                    protected Map<String, String> getParams() throws AuthFailureError{
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        if(!changingPassword)
                        {

                            hashMap.put("emailforprofileedit", getIntent().getStringExtra("email"));
                            hashMap.put("firstname", firstName.getText().toString());
                            hashMap.put("lastname", lastName.getText().toString());
                            hashMap.put("phone", phone.getText().toString());
                            hashMap.put("address", address.getText().toString());
                        }
                        else {
                            hashMap.put("emailforchangepassword", getIntent().getStringExtra("email"));
                            hashMap.put("currentpassword", currentPassword.getText().toString());
                            hashMap.put("newpassword", newPassword.getText().toString());
                        }

                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.constraintLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2 );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        finish();
                        startActivity(new Intent(getApplicationContext(), EditProfile.class));
                    }
                }, 1500);
            }
        });

    }


    public void loadProfilePicture(String fileName){
        ImageRequest imageRequest = new ImageRequest(URL_IMG+fileName, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                profilePicture.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Something went wrong.",Toast.LENGTH_SHORT).show();
            }
        });
        Singleton.getInstance(EditProfile.this).addToRequestQueue(imageRequest);
    }

    private void hidePasswordInputs()
    {
        currentPassword.setVisibility(View.INVISIBLE);
        newPassword.setVisibility(View.INVISIBLE);
        changepasswordtxt.setVisibility(View.INVISIBLE);
    }

    private void setInformation()
    {
        firstName.setHint(PreferenceData.getUserFirstName(getApplicationContext()));
        lastName.setHint(PreferenceData.getUserLastName(getApplicationContext()));
        phone.setHint(PreferenceData.getUserPhone(getApplicationContext()));
        address.setHint(PreferenceData.getUserAddress(getApplicationContext()));
    }

    public void onProfilePicturePressed(View view){
        Intent intent = new Intent(getApplicationContext(), UploadProfilePicture.class);
        intent.putExtra("email" , getIntent().getStringExtra("email"));
        startActivity(intent);
    }
}
