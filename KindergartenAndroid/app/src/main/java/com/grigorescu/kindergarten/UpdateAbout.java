package com.grigorescu.kindergarten;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class UpdateAbout extends AppCompatActivity {

    private static final String URL = new ReadIP().getIP()+"/about_control.php";
    EditText description, address, phone, fax;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_about);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));


        description = (EditText)findViewById(R.id.descriptiontxt);
        address = (EditText)findViewById(R.id.addresstxt);
        phone = (EditText)findViewById(R.id.phonetxt);
        fax = (EditText)findViewById(R.id.faxtxt);
        update = (Button)findViewById(R.id.updatebtn);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(), "SUCCESS:" + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                finish();
                                new About().finishAbout();
                                startActivity(new Intent(getApplicationContext(), About.class));
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
                    protected Map<String, String> getParams()
                    {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("description", description.getText().toString());
                        hashMap.put("address", address.getText().toString());
                        hashMap.put("phone", phone.getText().toString());
                        hashMap.put("fax", fax.getText().toString());
                        return hashMap;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });

    }
}
