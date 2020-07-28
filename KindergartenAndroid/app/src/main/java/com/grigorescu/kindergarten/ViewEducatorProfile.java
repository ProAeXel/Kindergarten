package com.grigorescu.kindergarten;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ViewEducatorProfile extends AppCompatActivity {

    private static String URL_IMG_HANDLER = new ReadIP().getIP() + "/upload.php";
    private static String URL_IMG = new ReadIP().getIP()+ "/pictures/";
    ImageView profileimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_educator_profile);

        TextView firstname = findViewById(R.id.txtfname);
        TextView lastname = findViewById(R.id.txtlname);
        TextView description = findViewById(R.id.txtdescription);
        TextView group = findViewById(R.id.txtgroup);
        profileimage = findViewById(R.id.educatorprofileimage);
        TextView email = findViewById(R.id.txtemail);

        String educatorfirst = getIntent().getStringExtra("fname");
        String educatorlast = getIntent().getStringExtra("lname");
        String educatordescription = getIntent().getStringExtra("description");
        String educatorgroup = getIntent().getStringExtra("group");
        String educatoremail = getIntent().getStringExtra("email");
        firstname.setText(educatorfirst);
        lastname.setText(educatorlast);
        description.setText(educatordescription);
        group.setText(educatorgroup);
        email.setText(educatoremail);

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
        Singleton.getInstance(ViewEducatorProfile.this).addToRequestQueue(stringRequest);
        //RequestQueue requestQueue = Volley.newRequestQueue(ViewEducatorProfile.this);
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
        Singleton.getInstance(ViewEducatorProfile.this).addToRequestQueue(imageRequest);
    }
}
