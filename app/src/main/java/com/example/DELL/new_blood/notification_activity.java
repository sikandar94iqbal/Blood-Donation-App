package com.example.DELL.new_blood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class notification_activity extends AppCompatActivity {

    TextView t;

    TextView name;
    TextView address;
    TextView blood;
    TextView message;
    Button confirm;


    float lat;
    float lng;
int count;
    String u_lat;
    String u_lng;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_activity);

       // t = (TextView) findViewById(R.id.notif);

        toolbar =(Toolbar) findViewById(R.id.toolbars);

        toolbar.setTitle("Emergency");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Emergency");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        String s = getIntent().getStringExtra("notif");
        String name1 = getIntent().getStringExtra("name");

        final String latt = getIntent().getStringExtra("lat");
        final String lngg = getIntent().getStringExtra("lng");

        final String req_id = getIntent().getStringExtra("request_id");
        String blood1 = getIntent().getStringExtra("blood");
        String message1 = getIntent().getStringExtra("message");
        final String token = getIntent().getStringExtra("token");


        name = (TextView) findViewById(R.id.name_textview);
      //  address = (TextView) findViewById(R.id.address_textview);
        blood = (TextView) findViewById(R.id.blood_textview);
        message = (TextView) findViewById(R.id.message_textview);


        name.setText(name1+" is in need of blood");
        //address.setText("LAT/LNG : "+latt+": SHIT :"+lngg);
        blood.setText("Required blood group is "+blood1);
        message.setText("Message : "+message1 +"\n\ntheir location is around you, Click below to help!");
      //  final String[] LatLng = address1.split(":");


        confirm = (Button) findViewById(R.id.confirm);




        SingleShotLocationProvider.requestSingleUpdate(this,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        ///Log.d("Location", "my location is " + location.toString());
                        lat = location.latitude;
                        lng = location.longitude;



u_lat = String.valueOf(lat);
u_lng = String.valueOf(lng);



                    }


                });
count = 0;

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(count == 0){
                        SharedPreferences sharedPreferences = getSharedPreferences("PrefName", MODE_PRIVATE);
                        String USER_ID = sharedPreferences.getString("USER_ID", "");

                        String[] animalsArray = USER_ID.split(":");
//
                        String name1 = animalsArray[1].toString();
                        final String id1 = animalsArray[0].toString();


                        Downloader7 dd = new Downloader7(getApplicationContext(), id1, req_id, u_lat, u_lng, token);
                        dd.execute();
//
//                Toast.makeText(notification_activity.this,USER_ID,Toast.LENGTH_LONG).show();
//              //  Toast.makeText(notification_activity.this, token,Toast.LENGTH_LONG).show();
                        String uri = "http://maps.google.com/maps?daddr=" + latt + "," + lngg + " (" + "Blood donation location" + ")";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setPackage("com.google.android.apps.maps");
                        startActivity(intent);
                        count++;
                    }
                    else{
                        Toast.makeText(notification_activity.this,"Request already recieved",Toast.LENGTH_LONG).show();
                    }

                }
            });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



class Downloader7 extends AsyncTask<Void,Integer,String> {

    Context c;
    String address;

    ///ArrayList<String> check_box_data = new ArrayList<String>();
    ProgressDialog pd;

    String USER_ID;
    String req_id;

    String lat;
    String lng;

    String name;
    String email;
    String password;
    String phone;
    String city;
    String blood_type;
    String token;

    String msg;

    // publlistAdapter boxAdapter;

    public Downloader7(Context c,String USER_ID, String req_id, String lat, String lng, String token) {
        this.c = c;
        this.token=token;
        this.lat=lat;
        this.lng = lng;
        this.USER_ID = USER_ID;
        this.req_id = req_id;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        pd=new ProgressDialog(c);
//        pd.setTitle("Fetch Data");
//        pd.setMessage("Fething data.....please wait");
//        pd.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        String data= null;
        try {
            data = DownloadData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

//        pd.dismiss();
        if (s!=null)
        {
//            Parser3 p=new Parser3(c,s,firstname,username,email,mobile,city,image);
//            p.execute();
        //    Toast.makeText(c,s,Toast.LENGTH_SHORT).show();
//            new AlertDialog.Builder(c)
//                    .setTitle("Your Alert")
//                    .setMessage("Your Message")
//                    .setCancelable(false)
//                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // Whatever...
//                        }
//                    }).show();




        }else
        {
            Toast.makeText(c,"Unable to download data",Toast.LENGTH_SHORT).show();

        }
    }

    private String DownloadData() throws IOException {
        InputStream is = null;
        String line = null;
        String url1 = "http://ennovayt.com/blood/Add_active_donor.php";
        try {
            URL url = new URL(url1);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //add city post method
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream OS = con.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String data = URLEncoder.encode("USER_ID", "UTF-8")+"="+URLEncoder.encode(USER_ID, "UTF-8")+"&"+ URLEncoder.encode("req_id", "UTF-8")+"="+URLEncoder.encode(req_id, "UTF-8")+"&"+ URLEncoder.encode("lat", "UTF-8")+"="+URLEncoder.encode(lat, "UTF-8")+"&"+ URLEncoder.encode("lng", "UTF-8")+"="+URLEncoder.encode(lng, "UTF-8")+"&"+ URLEncoder.encode("token", "UTF-8")+"="+URLEncoder.encode(token, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();


            //getting list of sub citites
            is = new BufferedInputStream(con.getInputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();

            if (br != null) {
                while ((line = br.readLine()) != null) {
                    sb.append((line+"\n"));
                }

            }
            else {
                return null;
            }
            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null)
            {
                is.close();
            }
        }
        return null;
    }
}


