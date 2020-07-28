package com.grigorescu.kindergarten;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class ListViewAdapter extends ArrayAdapter<Demo> {

    private List<Demo> demoList;
    private Context mCtx;

    public ListViewAdapter(List<Demo> demoList, Context mCtx){
        super(mCtx, R.layout.list_items, demoList);
        this.demoList = demoList;
        this.mCtx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(mCtx);



        final View listViewItem = inflater.inflate(R.layout.list_items, null, true);

        final TextView txttitle = (TextView)listViewItem.findViewById(R.id.txtTitle);
        final TextView txtsubtitle = (TextView)listViewItem.findViewById(R.id.txtsubtitle);


        final Demo demo = demoList.get(position);

        final String title = demo.getTitle();
        final String text = demo.getText();
        //final String id = Integer.toString(demo.getId());
        txttitle.setText(demo.getTitle());
        txtsubtitle.setText(demo.getText());


        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getContext(), annotitle, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), ViewAnnouncement.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title", title);
                intent.putExtra("text", text);
                mCtx.startActivity(intent);
            }
        });

        return listViewItem;
    }

}
