package com.example.DELL.new_blood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sikandar on 3/9/2018.
 */


public class notif_adapter extends ArrayAdapter<donor_model> {

    Context context;
    int layoutResourceId;
    ArrayList<donor_model> data=new ArrayList<donor_model>();
    String ID;
    String all_selected_subs;

    public notif_adapter(Context context, int layoutResourceId, ArrayList<donor_model> data) {
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
        weatherHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new weatherHolder();
             holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
          //  holder.button =(Button) row.findViewById(R.id.choose);
            // holder.checkBox = (CheckBox) row.findViewById(R.id.checkBox);


            row.setTag(holder);
        }
        else
        {
            holder = (weatherHolder)row.getTag();
        }

        // weather weather = data[position];
        holder.txtTitle.setText(data.get(position).getName());

//        holder.checkBox.setOnCheckedChangeListener(myCheckChangList);
        //      holder.checkBox.setTag(position);
        // holder.checkBox.setChecked(p.box);



        Uri myUri = Uri.parse(data.get(position).getImage().toString());


        //String picture = "https://sikandariqbal.net/Rahnuma/images/pic1.jpg";
        String picture = "http://ennovayt.com/blood/"+data.get(position).getImage().toString();
        new DownLoadImageTask2(holder.imgIcon).execute(picture);

        final Integer pos = position;


//        holder.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String shit = data.get(pos).getName();
////                String guideid1 = data.get(pos).getID();
//                Intent myintent=new Intent(context, donor_profile.class).putExtra("name", shit);
//                myintent.putExtra("phone",data.get(pos).getPhone());
//                myintent.putExtra("blood",data.get(pos).getBlood());
//                myintent.putExtra("image",data.get(pos).getImage());
//                context.startActivity(myintent);
//            }
//        });
        //  holder.imgIcon.setImageBitmap(mIcon_val);

        // holder.imgIcon.setImageResource();

        return row;
    }

    static class weatherHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        Button button;
    }
    donor_model getProduct(int position) {
        return ((donor_model) getItem(position));
    }

//    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
//        public void onCheckedChanged(CompoundButton buttonView,
//                                     boolean isChecked) {
//            getProduct((Integer) buttonView.getTag()).box = isChecked;
//        }
//    };





}

class DownLoadImageTask3 extends AsyncTask<String,Void,Bitmap> {
    ImageView imageView;

    public DownLoadImageTask3(ImageView imageView){
        this.imageView = imageView;
    }

    /*
        doInBackground(Params... params)
            Override this method to perform a computation on a background thread.
     */
    protected Bitmap doInBackground(String...urls){
        String urlOfImage = urls[0];
        Bitmap logo = null;
        try{
            InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
            logo = BitmapFactory.decodeStream(is);
        }catch(Exception e){ // Catch the download exception
            e.printStackTrace();
        }
        return logo;
    }

    /*
        onPostExecute(Result result)
            Runs on the UI thread after doInBackground(Params...).
     */
    protected void onPostExecute(Bitmap result){
        imageView.setImageBitmap(result);
    }
}
