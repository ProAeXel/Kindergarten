package com.grigorescu.kindergarten;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class GenerateCode extends AppCompatActivity {

    private TextView showcode;
    private Button generatecode;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private static final String URL = new ReadIP().getIP()+"/generate_code.php";
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    String generatedCode;
    ImageView bgapp;
    Animation bganimopen,bganimclose,frombottom;
    ConstraintLayout card;
    EditText email;

    @Override
    public void onBackPressed()
    {
        bgapp.startAnimation(bganimclose);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(GenerateCode.this, CommonMenu.class);
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
        setContentView(R.layout.activity_generate_code);


        showcode = (TextView)findViewById(R.id.txtshowcode);
        generatecode = (Button)findViewById(R.id.btngeneratecode);
        radioGroup = (RadioGroup)findViewById(R.id.rbgroup);
        bgapp = (ImageView)findViewById(R.id.bgapp);
        bganimopen = AnimationUtils.loadAnimation(this, R.anim.bganim);
        bganimclose = AnimationUtils.loadAnimation(this, R.anim.bganimclose);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        card = (ConstraintLayout)findViewById(R.id.card);
        email = (EditText)findViewById(R.id.emailtxt);

        bgapp.startAnimation(bganimopen);
        card.startAnimation(frombottom);

        requestQueue = Volley.newRequestQueue(this);

        generatecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = -1;
                radioId = radioGroup.getCheckedRadioButtonId();
                if(radioId != -1 && email.getText().toString().length() > 0){
                    radioButton = findViewById(radioId);
                    generatedCode = giveCode(radioButton.getText().toString());
                    showcode.setText(generatedCode);

                    stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.names().get(0).equals("success")) {

                                    Toast.makeText(getApplicationContext(), "Code successfully generated", Toast.LENGTH_SHORT).show();
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
                        protected Map<String, String> getParams()
                        {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("code", generatedCode);
                            hashMap.put("used", "unused");

                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                    sendMessage();
                }
            }
        });
    }

    private void sendMessage() {
        /*final ProgressDialog dialog = new ProgressDialog(GenerateCode.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();*/
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GmailSender sender = new GmailSender("fearofdark2014@gmail.com", "alex1995alexcsrock");
                    sender.sendMail("Kindergarten Registration E-Mail",
                            "   Following your request to register with our application, a scholarship code for which you opted was generated." +
                                    "\n   Please use the following code in registration process:\n" + "     " +
                                    generatedCode.toUpperCase(),
                            "fearofdark2014@gmail.com",
                            email.getText().toString());
                    //dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }

    private String giveCode (String tipProgram){
        String code = null;

        switch (tipProgram)
        {
            case "Program scurt":
                code = "A" + randomCode();
                break;
            case "Program mediu":
                code = "B" + randomCode();
                break;
            case "Program prelungit":
                code = "C" + randomCode();
                break;
        }

        return code;
    }

    private String randomCode()
    {
        String generatedCode;

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(5);

        for (int i = 0; i < 5; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        generatedCode = sb.toString();
        return generatedCode;
    }
}
