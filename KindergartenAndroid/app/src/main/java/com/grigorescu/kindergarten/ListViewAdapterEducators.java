package com.grigorescu.kindergarten;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class ListViewAdapterEducators extends ArrayAdapter<DemoEducator> {

    private List<DemoEducator> demoList;
    private Context mCtx;
    // String dynamicActivity = "com.grigorescu.kindergarten.AnnouncementsPage";


    public ListViewAdapterEducators(List<DemoEducator> demoList, Context mCtx){
        super(mCtx, R.layout.list_items2, demoList);
        this.demoList = demoList;
        this.mCtx = mCtx;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        final View listViewItem = inflater.inflate(R.layout.list_items2, null, true);

        final TextView txtfname = (TextView)listViewItem.findViewById(R.id.txtfname);
        final TextView txtlname = (TextView)listViewItem.findViewById(R.id.txtlname);



        final DemoEducator demo = demoList.get(position);

        final String fname = demo.getFirstname();
        final String lname = demo.getLastname();
        final String description = demo.getDescription();
        final String group = demo.getGroup();
        final String email = demo.getEmail();
        //final String id = Integer.toString(demo.getId());
        txtfname.setText(demo.getFirstname());
        txtlname.setText(demo.getLastname());



        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getContext(), annotitle, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), ViewEducatorProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("fname", fname);
                intent.putExtra("lname", lname);
                intent.putExtra("description", description);
                intent.putExtra("group", group);
                intent.putExtra("email", email);

                mCtx.startActivity(intent);
            }
        });

        return listViewItem;
    }

}
