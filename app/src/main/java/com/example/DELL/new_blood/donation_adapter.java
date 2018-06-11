package com.example.DELL.new_blood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sikandar on 3/10/2018.
 */

public class donation_adapter extends ArrayAdapter<donation_model> {
    Context context;
    int layoutResourceId;
    ArrayList<donation_model> data=new ArrayList<donation_model>();
    String ID;
    String all_selected_subs;

    public donation_adapter(Context context, int layoutResourceId, ArrayList<donation_model> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.ID=ID;
        this.all_selected_subs=all_selected_subs;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        request_history_adapter.weatherHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new request_history_adapter.weatherHolder();
            // holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
           // holder.button =(Button) row.findViewById(R.id.choose);
            // holder.checkBox = (CheckBox) row.findViewById(R.id.checkBox);


            row.setTag(holder);
        }
        else
        {
            holder = (request_history_adapter.weatherHolder)row.getTag();
        }

        // weather weather = data[position];
        holder.txtTitle.setText("Donated to Mr. " + data.get(position).getName());

//        holder.checkBox.setOnCheckedChangeListener(myCheckChangList);
        //      holder.checkBox.setTag(position);
        // holder.checkBox.setChecked(p.box);



//        Uri myUri = Uri.parse(data.get(position).getImage().toString());
//
//
//        //String picture = "https://sikandariqbal.net/Rahnuma/images/pic1.jpg";
//        String picture = "http://ennovayt.com/blood/"+data.get(position).getImage().toString();
//        new DownLoadImageTask2(holder.imgIcon).execute(picture);

        final Integer pos = position;
//        holder.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String shit = data.get(pos).getR_id();
////                String guideid1 = data.get(pos).getID();
//                Intent myintent=new Intent(context, donation_activity.class).putExtra("req_id", shit);
//                myintent.putExtra("name",  data.get(pos).getName());
//                myintent.putExtra("req_blood_type", data.get(pos).getReq_blood_type());
//                myintent.putExtra("dates", data.get(pos).getDates());
//
//                context.startActivity(myintent);
//            }
//        });
        //  holder.imgIcon.setImageBitmap(mIcon_val);

        // holder.imgIcon.setImageResource();

        return row;
    }

    static class weatherHolder
    {
        // ImageView imgIcon;
        TextView txtTitle;
        Button button;
    }
    donation_model getProduct(int position) {
        return ((donation_model) getItem(position));
    }

}

//    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
//        public void onCheckedChanged(CompoundButton buttonView,
//                                     boolean isChecked) {
//            getProduct((Integer) buttonView.getTag()).box = isChecked;
//        }
//    };
