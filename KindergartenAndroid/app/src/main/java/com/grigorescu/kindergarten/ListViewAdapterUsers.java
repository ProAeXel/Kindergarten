package com.grigorescu.kindergarten;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapterUsers extends ArrayAdapter<DemoUser> {
    private List<DemoUser> demoList;
    private Context mCtx;
    ImageView bgapp;
    Animation bganimclose;



    public ListViewAdapterUsers(List<DemoUser> demoList, Context mCtx){
        super(mCtx, R.layout.list_items3, demoList);
        this.demoList = demoList;
        this.mCtx = mCtx;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        final View listViewItem = inflater.inflate(R.layout.list_items2, null, true);

        final TextView txtfname = (TextView)listViewItem.findViewById(R.id.txtfname);
        final TextView txtlname = (TextView)listViewItem.findViewById(R.id.txtlname);
        //bgapp = (ImageView) ViewUserProfile.<View>findViewById(R.id.bgapp);
        bganimclose = AnimationUtils.loadAnimation(getContext(), R.anim.bganimclose);

        final DemoUser demo = demoList.get(position);

        final String fname = demo.getFirstname();
        final String lname = demo.getLastname();
        final String phone = demo.getPhone();
        final String email = demo.getEmail();
        //final String id = Integer.toString(demo.getId());
        txtfname.setText(demo.getFirstname());
        txtlname.setText(demo.getLastname());



        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewUserProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("fname", fname);
                intent.putExtra("lname", lname);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                mCtx.startActivity(intent);
            }
        });
        return listViewItem;
    }

}
