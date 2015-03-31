package com.example.zahan.tsc;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Login extends Activity implements View.OnClickListener {
    EditText user,pass;
    Button signin,forgot;
    String status;
    String savedid;
    String savedpass;
    Intent i;
    private ProgressDialog pDialog;
    public static String filename="MySharedString";
    SharedPreferences somedata,somedata2;
    CheckBox checkBox;
    JSONParser jsonParser = new JSONParser();
    //php login script location:

            //localhost :

            //testing on your device

            //put your local ip instead,  on windows, run CMD > ipconfig

            //or in mac's terminal type ifconfig and look for the ip under en0 or en1

            // private static final String LOGIN_URL = "http://xxx.xxx.x.x:1234/webservice/login.php";

            //testing on Emulator:

    private static final String LOGIN_URL = "http://192.168.3.1:1234/TSC/login.php";

            //testing from a real server:

            //private static final String LOGIN_URL = "http://www.yourdomain.com/webservice/login.php";

            //JSON element ids from repsonse of php script:

    private static final String TAG_SUCCESS = "success";

    private static final String TAG_MESSAGE = "message";

    private static final String TAG_STATUS = "status";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //initialisation
        user=(EditText) findViewById(R.id.userid);
        pass=(EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.signin);
        checkBox=(CheckBox) findViewById(R.id.checkBox);

        //set listener to button click

        signin.setOnClickListener(this);

        somedata2=this.getSharedPreferences(filename, 0);
        String gotid=somedata2.getString("savedid","nothing");
        String gotpass=somedata2.getString("savedpass","nothing");
        if(!gotid.equals("nothing"))
        {
            user.setText(gotid);

        }
        if(!gotpass.equals("nothing"))
        {
            pass.setText(gotpass);

        }

    }


    @Override
    public void onClick(View buttons) {
        switch (buttons.getId())
        {
            case R.id.signin:

                if (checkBox.isChecked()) {
                    savedid = user.getText().toString();
                    savedpass = pass.getText().toString();

                    somedata2 = getSharedPreferences(filename,0);

                    SharedPreferences.Editor edit2 = somedata2.edit();

                    edit2.putString("savedid",savedid);

                    edit2.putString("savedpass",savedpass);

                    edit2.commit();


                }



                new AttemptLogin().execute();

                break;

        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }

    //AsyncTask is a seperate thread than the thread that runs the GUI

            //Any type of networking should be done with asynctask.

    class AttemptLogin extends AsyncTask<String, String, String> {
                //three methods get called, first preExecture, then do in background, and once do

                //in back ground is completed, the onPost execture method will be called.

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override

        protected String doInBackground(String... args) {
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();

            try {

                // Building Parameters

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                    params.add(new BasicNameValuePair("username", username));

                    params.add(new BasicNameValuePair("password", password));




                Log.d("request!", "starting");

                // getting product details by making HTTP request

                JSONObject json = jsonParser.makeHttpRequest(

                        LOGIN_URL, "POST", params);



                // check your log for json response

                Log.d("Login attempt", json.toString());



                // json success tag

                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    Log.d("Login Successful!", json.toString());

                    status=json.getString(TAG_STATUS);

                    somedata = getSharedPreferences(filename,0);

                    SharedPreferences.Editor edit = somedata.edit();

                    edit.putString("username", username);

                    edit.putString("status", status);

                    edit.commit();


                    if(status.equals("teacher"))
                    {
                        i = new Intent("com.example.zahan.tsc.MainActivity");
                    }
                    if(status.equals("student"))
                    {
                        i = new Intent("com.example.zahan.tsc.MainActivity_student");
                    }




                    startActivity(i);

                    return json.getString(TAG_MESSAGE);

                }else{

                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));

                    return json.getString(TAG_MESSAGE);

                }

            } catch (JSONException e) {

                e.printStackTrace();
            }


            return null;
        }
        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            if (file_url != null){

                Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }


}
