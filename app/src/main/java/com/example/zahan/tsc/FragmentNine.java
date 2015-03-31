package com.example.zahan.tsc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;




public class FragmentNine extends Fragment {


    ListView list;
    String title;
    JSONParser jParser;
    JSONObject json;
    String teacherid;
    Intent deletedata;
    SharedPreferences somedata;
    public static String filename="MySharedString";

    // Progress Dialog

    private ProgressDialog pDialog;

    //php read comments script



    //localhost :

    //testing on your device

    //put your local ip instead,  on windows, run CMD > ipconfig

    //or in mac's terminal type ifconfig and look for the ip under en0 or en1

    // private static final String READ_COMMENTS_URL = "http://xxx.xxx.x.x:1234/webservice/comments.php";

    //testing on Emulator:

    private static final String READ_COMMENTS_URL = "http://192.168.3.1:1234/TSC/notices.php";

    //testing from a real server:

    //private static final String READ_COMMENTS_URL = "http://www.mybringback.com/webservice/comments.php";

    //JSON IDS:

    private static final String TAG_SUCCESS = "success";

    private static final String TAG_POSTS = "posts";

    private static final String TAG_COURSENO = "courseno";

    private static final String TAG_DATE = "date";

    private static final String TAG_TIME = "notice";

    private static final String TAG_ID = "id";
    //it's important to note that the message is both in the parent branch of

    //our JSON tree that displays a "Post Available" or a "No Post Available" message,

    //and there is also a message for each individual post, listed under the "posts"

    //category, that displays what the user typed as their message.

    //An array of all of our comments

    private JSONArray mComments = null;

    //manages all of our comments in a list.

    private ArrayList<HashMap<String, String>> mCommentList;



    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    public FragmentNine() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout_nine, container,
                false);
        list = (ListView) view.findViewById(R.id.list);
        return view;
    }
    @Override

    public void onResume() {

        // TODO Auto-generated method stub

        super.onResume();

        //loading the comments via AsyncTask

        new LoadComments().execute();

    }




    /**
     89
     * Retrieves json data of comments
     90
     */

    public void updateJSONdata() {
        int success;
        jParser = new JSONParser();
        somedata=getActivity().getSharedPreferences(filename,0);
        teacherid=somedata.getString("username","could not load data");


        // Instantiate the arraylist to contain all the JSON data.

        // we are going to use a bunch of key-value pairs, referring

        // to the json element name, and the content, for example,

        // message it the tag, and "I'm awesome" as the content..


        mCommentList = new ArrayList<HashMap<String, String>>();

        // Bro, it's time to power up the J parser


        // Feed the beast our comments url, and it spits us

        //back a JSON object.  Boo-yeah Jerome.

        //when parsing JSON stuff, we should probably

        //try to catch any exceptions:

        try {

            //I know I said we would check if "Posts were Avail." (success==1)

            //before we tried to read the individual posts, but I lied...

            //mComments will tell us how many "posts" or comments are

            //available
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("username",teacherid));

            JSONObject json =jParser.makeHttpRequest(

                    READ_COMMENTS_URL, "POST", params);



            Log.d("request!", "starting");

            success = json.getInt(TAG_SUCCESS);

            if(success==1) {

                mComments = json.getJSONArray(TAG_POSTS);

                // looping through all posts according to the json object returned

                for (int i = 0; i < mComments.length(); i++) {

                    JSONObject c = mComments.getJSONObject(i);

                    //gets the content of each tag

                    String title = c.getString(TAG_TIME);

                    String content = c.getString(TAG_DATE);

                    String username = c.getString(TAG_COURSENO);

                    String id = c.getString(TAG_ID);

                    // creating new HashMap

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_TIME, title);

                    map.put(TAG_DATE, content);

                    map.put(TAG_COURSENO, username);

                    map.put(TAG_ID, id);


                    // adding HashList to ArrayList

                    mCommentList.add(map);

                    //annndddd, our JSON data is up to date same with our array list

                }
            }
            else{

                Log.d("Login Failure!", json.getString(TAG_DATE));



            }




        } catch (JSONException e) {

            e.printStackTrace();

        }

    }

    /**
     96
     * Inserts the parsed data into our listview
     97
     */

    private void updateList() {


        // For a ListActivity we need to set the List Adapter, and in order to do

        //that, we need to create a ListAdapter.  This SimpleAdapter,

        //will utilize our updated Hashmapped ArrayList,

        //use our single_post xml template for each item in our list,

        //and place the appropriate info from the list to the

        //correct GUI id.  Order is important here.

        ListAdapter adapter = new SimpleAdapter(getActivity(), mCommentList,

                R.layout.single_post_nine, new String[] { TAG_TIME, TAG_DATE,

                TAG_COURSENO }, new int[] { R.id.title, R.id.message,

                R.id.username });

        // I shouldn't have to comment on this one:

        list.setAdapter(adapter);

        // Optional: when the user clicks a list item we

        //could do something.  However, we will choose

        //to do nothing...



        list.setOnItemClickListener(new OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view,

                                    int position, long id) {

                try {
                    JSONObject item1 = mComments.getJSONObject(position);
                    String value  = item1.getString(TAG_ID);
                    String table="notices";
                    deletedata=new Intent("com.example.zahan.tsc.DeleteData");
                    deletedata.putExtra("id",value);
                    deletedata.putExtra("table",table);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(deletedata);


            }

        });


    }

    public class LoadComments extends AsyncTask<Void, Void, Boolean> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());

            pDialog.setMessage("Loading Comments...");

            pDialog.setIndeterminate(false);

            pDialog.setCancelable(true);

            pDialog.show();

        }

        @Override

        protected Boolean doInBackground(Void... arg0) {

            //we will develop this method in version 2

            updateJSONdata();

            return null;

        }


        @Override

        protected void onPostExecute(Boolean result) {

            super.onPostExecute(result);

            pDialog.dismiss();

            //we will develop this method in version 2

            updateList();

        }

    }

}




