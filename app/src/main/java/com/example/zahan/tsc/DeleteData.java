package com.example.zahan.tsc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zahan on 3/14/2015.
 */
public class DeleteData extends Activity implements View.OnClickListener {

    Button yes,cancel;
    String table,id;
    String teacherid;
    SharedPreferences somedata;
    public static String filename="MySharedString";
    private ProgressDialog pDialog;
    JSONParser jParser;
    private String READ_COMMENTS_URL;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletesure);
        jParser=new JSONParser();
        yes=(Button) findViewById(R.id.yes);
        cancel=(Button) findViewById(R.id.cancel);
        yes.setOnClickListener(this);
        cancel.setOnClickListener(this);
        table=getIntent().getStringExtra("table");
        id=getIntent().getStringExtra("id");
        if(table.equals("ct"))
        {
            READ_COMMENTS_URL = "http://192.168.3.1:1234/TSC/deletect.php";

        }
        else if(table.equals("extraclass"))
        {
            READ_COMMENTS_URL = "http://192.168.3.1:1234/TSC/deleteextra.php";

        }
        else if(table.equals("classchange"))
        {
            READ_COMMENTS_URL = "http://192.168.3.1:1234/TSC/deleteclasschange.php";

        }
        else if(table.equals("classcancel"))
        {
            READ_COMMENTS_URL = "http://192.168.3.1:1234/TSC/deleteclasscancel.php";

        }
        else if(table.equals("notices"))
        {
            READ_COMMENTS_URL = "http://192.168.3.1:1234/TSC/deletenotices.php";

        }
        somedata=this.getSharedPreferences(filename, 0);
        teacherid=somedata.getString("status","could not load data");




    }
    class PostComment extends AsyncTask<String, String, String> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

            pDialog = new ProgressDialog(DeleteData.this);

            pDialog.setMessage("Deleting...");

            pDialog.setIndeterminate(false);

            pDialog.setCancelable(true);

            pDialog.show();

        }



        @Override

        protected String doInBackground(String... args) {

            // TODO Auto-generated method stub

            // Check for success tag

            int success;
            try {

                // Building Parameters

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("id",id));

                Log.d("request!", "starting");

                //Posting user data to script

                JSONObject json = jParser.makeHttpRequest(

                        READ_COMMENTS_URL, "POST", params);



                // full json response

                Log.d("Post Comment attempt", json.toString());



                // json success element

                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    Log.d("Comment Added!", json.toString());

                    return json.getString(TAG_MESSAGE);

                }else{

                    Log.d("Comment Failure!", json.getString(TAG_MESSAGE));

                    return json.getString(TAG_MESSAGE);

                }

            } catch (JSONException e) {

                e.printStackTrace();

            }

            return null;

        }

        protected void onPostExecute(String file_url) {

            // dismiss the dialog once product deleted

            pDialog.dismiss();
            Intent i;
            if(teacherid.equals("teacher"))
            {
                i = new Intent("com.example.zahan.tsc.MainActivity");
            }
            else
            {
                i = new Intent("com.example.zahan.tsc.MainActivity_student");
            }
            startActivity(i);



        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.yes:
                new PostComment().execute();
                break;
            case R.id.cancel:
                Intent i;

                if(teacherid.equals("teacher"))
                {
                    i = new Intent("com.example.zahan.tsc.MainActivity");
                }
                else
                {
                    i = new Intent("com.example.zahan.tsc.MainActivity_student");
                }
                startActivity(i);
                break;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
