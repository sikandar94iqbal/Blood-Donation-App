package com.example.DELL.new_blood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;

public class event_user extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_user);
        listView = (ListView) findViewById(R.id.listt);

       // String user_id = getIntent().getStringExtra("USER_ID");

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Event History");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //  Toast.makeText(event_history.this, user_id,Toast.LENGTH_LONG).show();

        DownloaderXz d = new DownloaderXz(event_user.this, listView,"http://ennovayt.com/blood/event_user.php");
        d.execute();
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


class DownloaderXz extends AsyncTask<Void,Integer,String> {

    Context c;
    String address;
    ListView lv;
    String CITY = null;
    String selected_city;
    ///ArrayList<String> check_box_data = new ArrayList<String>();
    ProgressDialog pd;
    String all_selected_subs;
    // publlistAdapter boxAdapter;
    String ID;
    String url1;
    String req_id;
    String user_id;
    public DownloaderXz(Context c,ListView lv, String url) {
        this.c = c;

        this.lv = lv;
        this.url1 = url;
        // this.check_box_data = check_box_data;

    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(c);
        pd.setTitle("Fetch Data");
        pd.setMessage("Fething data.....please wait");
        pd.show();
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

        pd.dismiss();
        if (s!=null)
        {
            ParserXz p=new ParserXz(c,s,lv);
            p.execute();
        }else
        {
            Toast.makeText(c,"Unable to download data",Toast.LENGTH_SHORT).show();

        }
    }

    private String DownloadData() throws IOException {
        InputStream is = null;
        String line = null;
        try {
            URL url = new URL(url1);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //add city post method
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream OS = con.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String data = URLEncoder.encode("post", "UTF-8")+"="+URLEncoder.encode("true", "UTF-8");
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


class ParserXz extends AsyncTask <Void,Integer,Integer>{

//    public listAdapter boxAdapter;

    Context c;
    String data;
    ListView lv;
    ProgressDialog pd;
    //    ArrayList<String> check_box_data = new ArrayList<String>();
    ArrayList<String> players=new ArrayList<>();
    String ID;
    String all_selected_subs;
    ArrayList<weather> data_list = new ArrayList<weather>();

    //  public listAdapter get_adapter(){
//        return this.boxAdapter;
//    }

    public ParserXz(Context c, String data, ListView lv) {
        this.c = c;
        this.data = data;
        this.lv = lv;
        this.ID=ID;
        this.all_selected_subs=all_selected_subs;
        // this.check_box_data = check_box_data;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Parsing Data");
        pd.setMessage("Parsing.....please wait");
        pd.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parser();


    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer==1)
        {
//            ArrayAdapter<String> adapter=new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,players);
//            lv.setAdapter(adapter);

//            request_history_adapter Wadapter = new request_history_adapter(c, R.layout.row1,data_list);
//            lv.setAdapter(Wadapter);

            weatherAdapter2 Wadapter = new weatherAdapter2(c, R.layout.row2,data_list);
            lv.setAdapter(Wadapter);

//
//           boxAdapter = new listAdapter(c, data_list);
//
//            lv.setAdapter(boxAdapter);




//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position,
//                                        long id) {
//
//                    final long[] checkedIds = lv.getCheckItemIds();
//                    for (int i=0;i<checkedIds.length;i++)
//                        Toast.makeText(c,checkedIds.toString(),Toast.LENGTH_LONG);
//
//                }
//            });






            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                    String shit = data_list.get(pos).getName();
                    //  String guideid1 = data.get(pos).getID();
                    Intent myintent=new Intent(c, event_activity.class).putExtra("name", shit);
                    myintent.putExtra("address",data_list.get(pos).getAddress());
                    myintent.putExtra("msg",data_list.get(pos).getMsg());
                    myintent.putExtra("img",data_list.get(pos).getImg());
                    c.startActivity(myintent);

                }
            });
        }
        else
        {
            Toast.makeText(c,"Unable to parse data",Toast.LENGTH_SHORT).show();


        }
        pd.dismiss();
    }
    private int parser()
    {
        try
        {
            JSONArray ja=new JSONArray(data);
            JSONObject jo=null;
            //    players.clear();
            for (int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                String name=jo.getString("name");
                String address = jo.getString("address");
                String msg = jo.getString("msg");
                String image = jo.getString("image");
                String ID = jo.getString("id");
                String date_time = jo.getString("date_time");
                // players.add(name);
                data_list.add(new weather(name,msg,image,address,ID,date_time));
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}


