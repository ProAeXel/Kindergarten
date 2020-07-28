package com.grigorescu.kindergarten;

import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewUserProfile extends AppCompatActivity {
    private static String URL_IMG_HANDLER = new ReadIP().getIP() + "/upload.php";
    private static String URL_IMG = new ReadIP().getIP()+ "/pictures/";
    ImageView profileimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_profile);

        TextView firstname = findViewById(R.id.txtfname);
        TextView lastname = findViewById(R.id.txtlname);
        TextView phone = findViewById(R.id.txtphone);
        profileimage = findViewById(R.id.userprofileimage);
        TextView email = findViewById(R.id.txtemail);


        String userfirst = getIntent().getStringExtra("fname");
        String userlast = getIntent().getStringExtra("lname");
        String userphone = getIntent().getStringExtra("phone");
        String useremail = getIntent().getStringExtra("email");
        firstname.setText(userfirst);
        lastname.setText(userlast);
        phone.setText(userphone);
        email.setText(useremail);

        getProfileImageCode();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));

    }
    private void getProfileImageCode() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_IMG_HANDLER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("profilepicture") != null) {
                        loadProfilePicture(jsonObject.getString("profilepicture"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("emailP", getIntent().getStringExtra("email"));

                return params;
            }
        };
        Singleton.getInstance(ViewUserProfile.this).addToRequestQueue(stringRequest);
        //RequestQueue requestQueue = Volley.newRequestQueue(ViewUserProfile.this);
        //requestQueue.add(stringRequest);
    }

    private void loadProfilePicture(String fileName){
        ImageRequest imageRequest = new ImageRequest(URL_IMG+fileName, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                profileimage.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Something went wrong.",Toast.LENGTH_SHORT).show();
            }
        });
        Singleton.getInstance(ViewUserProfile.this).addToRequestQueue(imageRequest);
    }
}
