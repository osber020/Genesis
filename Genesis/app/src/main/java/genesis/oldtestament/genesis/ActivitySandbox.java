package genesis.oldtestament.genesis;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by John Osberg on 3/16/2015.
 */

public class ActivitySandbox extends Fragment {

    private ArrayAdapter<String> mDJListAdapter;

    public ActivitySandbox() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.live_djs_fragment,menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchLiveDJsTask DJTask = new FetchLiveDJsTask();
            DJTask.execute("55414");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Dummy data that is filled in to the list view right away
        String[] data = {
                "DJ wiggle",
                "DJ Snoopy snooop doog",
                "DJ thumb",
                "DJ hands in the air",
                "DJ Go F&%k yourself",
                "DJ nope",
                "DIABEATS",
                "New fresh music dude",
                "Words are hard",
                "I like music",
                "This beer tastes good"
        };
        List<String> DJList = new ArrayList<String>(Arrays.asList(data));

        // Now that we have some dummy DJ data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy DJs) and
        // use it to populate the ListView it's attached to.
        mDJListAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_djs, // The name of the layout ID.
                        R.id.list_item_djs_textview, // The ID of the textview to populate.
                        DJList);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_djs);
        listView.setAdapter(mDJListAdapter);

        return rootView;
    }



    public class FetchLiveDJsTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchLiveDJsTask.class.getSimpleName();
        private final String baseURL = "http://192.168.10.147/scrpits/";
        /* The date/time conversion code is going to be moved outside the asynctask later,
* so for convenience we're breaking it out into its own method now.
*/

        @Override
        protected String[] doInBackground(String... params) {
            //Define the URL for this request and create HTTP Client and Post Header
            String scriptURL = "get_dj_for_listener.php"; //Params: xLocation,zLocation,limit,offset
            String _URL = baseURL + scriptURL;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(_URL);
           
            try{
                //Build the list of parameters to pass in the POST request
                List<NameValuePair> parameters = new ArrayList<NameValuePair>(4);
                parameters.add(new BasicNameValuePair("xLocation", "44.979"));
                parameters.add(new BasicNameValuePair("zLocation", "-93.23"));
                parameters.add(new BasicNameValuePair("limit", "0"));
                parameters.add(new BasicNameValuePair("offset", "20"));
                httppost.setEntity(new UrlEncodedFormEntity(parameters));

                //Execute the post request
                HttpResponse response = httpClient.execute(httppost);
                Log.v(LOG_TAG, "Hey, I did a response!");
            }
            catch (ClientProtocolException e){
                //Do something with it, log?
                Log.v(LOG_TAG, "Hey, I fucked up!");
            }
            catch (IOException e){
                Log.v(LOG_TAG, "Hey, I fucked up #2");
            }  
            return null;
        }

        /*
        * This method is run on the UI thread after doInBackground returns* */
        @Override
        protected void onPostExecute(String[] result){
            if (result != null){
                mDJListAdapter.clear();
                for (String dayForecastStr : result) {
                    mDJListAdapter.add(dayForecastStr);
                }
            }

        }

    }
}

